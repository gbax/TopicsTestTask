<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Список форумов</title>
    <c:import url="../include/templates/topicTemplate.jsp"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/frameworks/underscore.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/frameworks/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/frameworks/backbone.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/frameworks/backbone.paginator.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/frameworks/handlebars-v1.3.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/application/application.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/application/models.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/application/views.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/application/collections.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/application/router.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</head>
<body>
<c:import url="../include/login.jsp"/>
<h3>Список форумов</h3>
<table id="topicsTable">
    <thead>
    <tr>
        <td>
            Номер
        </td>
        <td>
            Описание
        </td>
        <td>
            Действие
        </td>
    </tr>
    </thead>
</table>
<hr>
<sec:authorize access="isAuthenticated()">
    <form action="post" id="topicForm">
        <div>
            <label for="description"></label>
            <textarea id="description" name="description"></textarea>
        </div>
        <div>
            <input type="submit" value="Добавить форум">
        </div>
    </form>
</sec:authorize>
</body>
</html>
