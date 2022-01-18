<%@ page import="com.basejava.webapp.model.*" %>
<%@ page import="com.basejava.webapp.util.DateUtil" %>
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
                <dt><b>${type.title}</b></dt>
                        <c:set var="section" value="${resume.getSection(type)}"/>
                        <c:choose>
                            <c:when test="${section != null}">
                                <jsp:useBean id="section" type="com.basejava.webapp.model.AbstractSection"/>
                                <c:choose>
                                    <c:when test="${type == 'PERSONAL' || type == 'OBJECTIVE'}">
                                        <input type='text' name='${type.name()}' size=106 value='<%=section%>'>
                                    </c:when>
                                    <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                                        <textarea cols=80 rows=10 name='${type.name()}' ><%=section + "\n"%></textarea>
                                    </c:when>
                                    <c:when test="${type == 'EDUCATION' || type == 'EXPERIENCE'}">
                                        <c:forEach var="org" items="<%=((OrganizationSection) section).getContent()%>" varStatus="counter">
                                            <dl>
                                                <dt><u>Название:</u></dt>
                                                <dd><input type="text" name='${type.name()}' size=106 value="${org.name} " placeholder="Введите название"></dd>
                                            </dl>
                                            <dl>
                                                <dt>Сайт:</dt>
                                                <dd><input type="text" name='${type.name()}url' size=106 value="${org.fullName.url}" placeholder="Введите сайт"></dd>
                                            </dl><br>
                                            <c:forEach var="exp" items="${org.content}">
                                                <jsp:useBean id="exp" type="com.basejava.webapp.model.Organization.Experience"/>
                                                <dl>
                                                    <dt>Дата начала:</dt>
                                                    <dd><input type="text" name='${type.name()}${counter.index}startDate' size=6 value="<%=exp.getStartDate()%>" placeholder="yyyy-MM" pattern="[0-9]{4}-[0-9]{2}"></dd>
                                                </dl>
                                                <dl>
                                                    <dt>Дата окончания:</dt>
                                                    <dd><input type="text" name='${type.name()}${counter.index}endDate' size=6 value="<%=exp.getEndDate()%>" placeholder="yyyy-MM" pattern="[0-9]{4}-[0-9]{2}"></dd>
                                                </dl>
                                                <dl>
                                                    <dt>Должность:</dt>
                                                    <dd><input type="text" name='${type.name()}${counter.index}title' size=106 value="${exp.title}" placeholder="Введите должность"></dd>
                                                </dl>
                                                <dl>
                                                    <dt>Описание:</dt>
                                                    <dd><textarea cols=80 rows=10 name='${type.name()}${counter.index}description' placeholder="Введите описание">${exp.description}</textarea></dd>
                                                </dl>
                                                </c:forEach>
                                            <dl>
                                                <dt>Дата начала:</dt>
                                                <dd><input type="text" name='${type.name()}newStartDate' size=6 placeholder="yyyy-MM" pattern="[0-9]{4}-[0-9]{2}"></dd>
                                            </dl>
                                            <dl>
                                                <dt>Дата окончания:</dt>
                                                <dd><input type="text" name='${type.name()}newEndDate' size=6 placeholder="yyyy-MM" pattern="[0-9]{4}-[0-9]{2}"></dd>
                                            </dl>
                                            <dl>
                                                <dt>Должность:</dt>
                                                <dd><input type="text" name='${type.name()}newTitle' size=106 placeholder="Введите должность"></dd>
                                            </dl>
                                            <dl>
                                                <dt>Описание:</dt>
                                                <dd><textarea cols=80 rows=10 name='${type.name()}newDescription' placeholder="Введите описание"></textarea></dd>
                                            </dl>
                                        </c:forEach>
                                        <dl>
                                            <dt><u>Название:</u></dt>
                                            <dd><input type="text" name='${type.name()}newOrgName' size=106 placeholder="Введите название"></dd>
                                        </dl>
                                        <dl>
                                            <dt>Сайт:</dt>
                                            <dd><input type="text" name='${type.name()}newOrgUrl' size=106 placeholder="Введите сайт"></dd>
                                        </dl><br>
                                        <dl>
                                            <dt>Дата начала:</dt>
                                            <dd><input type="text" name='${type.name()}newOrgStartDate' size=6 placeholder="yyyy-MM" pattern="[0-9]{4}-[0-9]{2}"></dd>
                                        </dl>
                                        <dl>
                                            <dt>Дата окончания:</dt>
                                            <dd><input type="text" name='${type.name()}newOrgEndDate' size=6 placeholder="yyyy-MM" pattern="[0-9]{4}-[0-9]{2}"></dd>
                                        </dl>
                                        <dl>
                                            <dt>Должность:</dt>
                                            <dd><input type="text" name='${type.name()}newOrgTitle' size=106 placeholder="Введите должность"></dd>
                                        </dl>
                                        <dl>
                                            <dt>Описание:</dt>
                                            <dd><textarea cols=80 rows=10 name='${type.name()}newOrgDescription' placeholder="Введите описание"></textarea></dd>
                                        </dl>
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
                                        <dl>
                                            <dt>Название:</dt>
                                            <dd><input type="text" name='newOrgName' size=106 placeholder="Введите название"></dd>
                                        </dl>
                                        <dl>
                                            <dt>Сайт:</dt>
                                            <dd><input type="text" name='newOrgUrl' size=106 placeholder="Введите сайт"></dd>
                                        </dl><br>
                                        <dl>
                                            <dt>Дата начала:</dt>
                                            <dd><input type="text" name='newOrgStartDate' size=6 placeholder="yyyy-MM"></dd>
                                        </dl>
                                        <dl>
                                            <dt>Дата окончания:</dt>
                                            <dd><input type="text" name='newOrgEndDate' size=6 placeholder="yyyy-MM"></dd>
                                        </dl>
                                        <dl>
                                            <dt>Должность:</dt>
                                            <dd><input type="text" name='newOrgTitle' size=106 placeholder="Введите должность"></dd>
                                        </dl>
                                        <dl>
                                            <dt>Описание:</dt>
                                            <dd><textarea cols=80 rows=10 name='newOrgDescription' placeholder="Введите описание"></textarea></dd>
                                        </dl>
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
