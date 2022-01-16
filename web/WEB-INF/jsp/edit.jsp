<%@ page import="com.basejava.webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input required pattern="^[A-Za-zА-Яа-яЁё0-9 _]+$" type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContacts(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <c:choose>
                    <c:when test="${resume.getSection(type)}!=null">
                        <c:set var="section" value="${resume.getSection(type)}"/>
                        <jsp:useBean id="section" type="com.basejava.webapp.model.AbstractSection"/>
                        <c:choose>
                            <c:when test="${type == 'PERSONAL' || type == 'OBJECTIVE'}">
                                <input type='text' name='${type.name()}' size=106 value='<%=section%>'>
                            </c:when>
                            <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                                <textarea cols=80 rows=10 name='${type.name()}' ><%=section + "\n"%></textarea>
                            </c:when>
                            <c:when test="${type == 'EDUCATION' || type == 'EXPERIENCE'}">
                                <c:set var="organization" value="<%=((OrganizationSection) section).getContent()%>"/>
                                <jsp:useBean id="organization" type="java.util.ArrayList"/>
                                <textarea cols=80 rows=10 name='${type.name()}' disabled> <%=organization%> </textarea>
                            </c:when>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${type == 'PERSONAL' || type == 'OBJECTIVE'}">
                                <input type='text' name='${type.name()}' size=106 value=''>
                            </c:when>
                            <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                                <textarea cols=80 rows=10 name='${type.name()}'></textarea>
                            </c:when>
                            <c:when test="${type == 'EDUCATION' || type == 'EXPERIENCE'}">
                                <textarea cols=80 rows=10 name='${type.name()}' disabled></textarea>
                            </c:when>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
