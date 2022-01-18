<%@ page import="com.basejava.webapp.model.TextSection" %>
<%@ page import="com.basejava.webapp.model.ListSection" %>
<%@ page import="com.basejava.webapp.model.OrganizationSection" %>
<%@ page import="com.basejava.webapp.model.Organization" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.ContactType, java.lang.String>"/>
                    <% if (!contactEntry.getValue().equals("")) {%>
                    <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
                    <% } %>
        </c:forEach>
        <br/>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                type="java.util.Map.Entry<com.basejava.webapp.model.SectionType, com.basejava.webapp.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <jsp:useBean id="type" type="com.basejava.webapp.model.SectionType"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.basejava.webapp.model.AbstractSection"/>
            <c:choose>
                <c:when test="${type == 'PERSONAL' || type == 'OBJECTIVE'}"><ul>
                    <% if (((TextSection) section).getContent().length()!=0) {%>
                        <h3><%=type.getTitle()%></h3>
                        <%=((TextSection) section).getContent()%></ul><br/>
                        <% } %>
                </c:when>
                <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}"><ul>
                    <% if (((ListSection)section).size()!=0 && !((ListSection)section).toString().equals("\n")) {%>
                        <h3><%=type.getTitle()%></h3>
                        <c:forEach var="content" items="<%=((ListSection) section).getContent()%>">
                                <li>${content}</li>
                        </c:forEach><br/>
                    <% } %></ul>
                </c:when>
                <c:when test="${type == 'EDUCATION' || type == 'EXPERIENCE'}">
                    <% if (((OrganizationSection) section).size()!=0) {%>
                        <h3><%=type.getTitle()%></h3>
                        <c:forEach var="organization" items="<%=((OrganizationSection) section).getContent()%>">
                            <c:choose>
                                <c:when test="${empty organization.fullName.url}">
                                    <h4><ul>${organization.name}</ul></h4>
                                </c:when>
                                <c:otherwise>
                                    <h4><ul><a href="${organization.fullName.url}">${organization.name}</a></ul></h4>
                                </c:otherwise>
                            </c:choose>
                            <c:forEach var="content" items="${organization.content}">
                                <jsp:useBean id="content" type="com.basejava.webapp.model.Organization.Experience"/>
                                <ul>${content.startDate} - ${content.endDate == null ? "по настоящее время" : content.endDate}<br>
                                <b>${content.title}</b><br>${content.description}</ul>
                            </c:forEach>
                        </c:forEach><br/>
                    <% } %>
                </c:when>
            </c:choose>
</
></c:forEach>
    <p/>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>