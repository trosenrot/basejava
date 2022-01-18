package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;
import com.google.gson.JsonArray;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;
    private JsonArray HtmlUtil;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "add":
                r = new Resume();
                r.addSection(SectionType.PERSONAL, new TextSection(""));
                r.addSection(SectionType.OBJECTIVE, new TextSection(""));
                r.addSection(SectionType.ACHIEVEMENT, new ListSection());
                r.addSection(SectionType.QUALIFICATIONS, new ListSection());
                r.addSection(SectionType.EDUCATION, new OrganizationSection());
                r.addSection(SectionType.EXPERIENCE, new OrganizationSection());
                break;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        boolean add = (uuid.equals(""));
        String fullName = request.getParameter("fullName").trim();
        if (fullName.length() == 0) {
            String url;
            if (!add) {
                url = "resume?uuid=" + uuid + "&action=edit";
            } else {
                url = "resume?action=add";
            }
            response.sendRedirect(url);
            return;
        }
        Resume r;
        if (add) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.setContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    String value = request.getParameter(type.name());
                    if (value != null && value.trim().length() != 0) {
                        r.setSections(type, new TextSection(value.trim()));
                    } else {
                        r.setSections(type, new TextSection(""));
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String[] values = request.getParameterValues(type.name());
                    if (values != null && values.length != 0) {
                        List<String> listValues = new ArrayList<>();
                        for (int i = 0; i < values.length; i++) {
                            String[] arrValues = values[i].split("\n");
                            for (int j = 0; j < arrValues.length; j++) {
                                arrValues[j] = arrValues[j].trim();
                                if (!arrValues[j].equals("") && !arrValues[j].equals("\r")) {
                                    listValues.add(arrValues[j]);
                                }
                            }
                        }
                        r.setSections(type, new ListSection(listValues));
                    } else {
                        r.addSection(type, new ListSection(new ArrayList<>()));
                    }
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    List<Organization> orgs = new ArrayList<>();
                    String[] urls = request.getParameterValues(type.name() + "url");
                    String[] valuesOrg = request.getParameterValues(type.name());
                    if (valuesOrg != null) {
                        for (int i = 0; i < valuesOrg.length; i++) {
                            String name = valuesOrg[i];
                            if (name != null && !name.equals("")) {
                                Organization org = new Organization(name, urls[i].equals("") ? null : urls[i]);
                                String counter = type.name() + i;
                                String[] startDates = request.getParameterValues(counter + "startDate");
                                String[] endDates = request.getParameterValues(counter + "endDate");
                                String[] titles = request.getParameterValues(counter + "title");
                                String[] descriptions = request.getParameterValues(counter + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (titles != null && !titles[j].equals("")) {
                                        org.addContent(new Organization.Experience(YearMonth.parse(startDates[j]), endDates[j].equals("") ? null : YearMonth.parse(endDates[j]), titles[j], descriptions[j] == "" ? "null" : descriptions[j]));
                                    }
                                }
                                String newTitle = request.getParameter(type.name() + "newTitle");
                                if (newTitle != null && !newTitle.equals("")) {
                                    String newDescription = request.getParameter(type.name() + "newDescription");
                                    String newStartDate = request.getParameter(type.name() + "newStartDate");
                                    String newEndDate = request.getParameter(type.name() + "newEndDate");
                                    org.addContent(new Organization.Experience(YearMonth.parse(newStartDate), newEndDate.equals("") ? null : YearMonth.parse(newEndDate), newTitle, newDescription.equals("") ? "null" : newDescription));
                                }
                                orgs.add(org);
                            }
                        }
                    }
                    String newOrgName = request.getParameter(type.name() + "newOrgName");
                    if (newOrgName != null && !newOrgName.equals("")) {
                        String newOrgUrl = request.getParameter(type.name() + "newOrgUrl");
                        Organization newOrg = new Organization(newOrgName, newOrgUrl.equals("") ? null : newOrgUrl);
                        String newOrgTitle = request.getParameter(type.name() + "newOrgTitle");
                        if (newOrgTitle != null && !newOrgTitle.equals("")) {
                            String newOrgDescription = request.getParameter(type.name() + "newOrgDescription");
                            String newOrgEndDate = request.getParameter(type.name() + "newOrgEndDate");
                            newOrg.addContent(new Organization.Experience(YearMonth.parse(request.getParameter(type.name() + "newOrgStartDate")),
                                    newOrgEndDate.equals("") ? null : YearMonth.parse(newOrgEndDate),
                                    newOrgTitle, newOrgDescription.equals("") ? "null" : newOrgDescription));
                        }
                        orgs.add(newOrg);
                    }
                    r.setSections(type, new OrganizationSection(orgs));
                    break;
            }
        }
        if (add) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }
}
