<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Список сообщений</title>
    <input type="hidden" id="topicId" value="${topic.id}"/>
    <c:import url="../include/templates/messageTemplate.jsp"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/frameworks/underscore.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/frameworks/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/frameworks/backbone.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/frameworks/backbone.paginator.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/frameworks/handlebars-v1.3.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/frameworks/backgrid.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/extensions/paginator/backgrid-paginator.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/extensions/text-cell/backgrid-text-cell.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/application/application.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/application/models.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/application/views.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/application/collections.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/application/router.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/topic.js"></script>
</head>
<body>
<c:import url="../include/login.jsp"/>
<h3>Список сообщений форума ${topic.description}</h3>
<table id="messagesTable">
    <thead>
    <tr>
        <td>
            Номер
        </td>
        <td>
            Сообщение
        </td>
        <td>
            Действие
        </td>
    </tr>
    </thead>
</table>
<hr>
<sec:authorize access="isAuthenticated()">
    <form action="post" id="messageForm">
        <div>
            <label for="message"></label>
            <textarea id="message" name="message"></textarea>
        </div>
        <div>
            <input type="submit" value="Добавить сообщение">
        </div>
    </form>
</sec:authorize>
<hr>
<div id="grid"></div>
<div id="paginator"></div>
</body>
</html>