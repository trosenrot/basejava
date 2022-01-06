package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    Storage storage;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("name");
        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + '!');
        response.getWriter().write(
                "<table border=\"1\">" +
                        "<tr>" +
                        "<th>UUID</th>" +
                        "<th>fullName</th>" +
                        "</tr>");
        for (Resume resume : storage.getAllSorted()) {
            response.getWriter().write("<tr>" +
                    "<td>" + resume.getUuid() + "</td>" +
                    "<td>" + resume.getFullName() + "</td>" +
                    "</tr>");
        }
        response.getWriter().write("</table>");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
