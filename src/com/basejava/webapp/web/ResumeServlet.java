package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

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
                url = "resume?uuid="+uuid+"&action=edit";
            } else {
                url = "resume?action=add";
            }
            response.sendRedirect(url);
            return;
        }
        Resume r;
        if (add) {
            r = new Resume(fullName);
        }
        else {
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
                                if (!arrValues[j].equals("") && !arrValues[j].equals("\r")){
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
                    r.addSection(type, new OrganizationSection());
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
