<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Список форумов</title>
    <c:import url="../include/resources.jsp"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</head>
<body>
<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}"/>
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2 center">
            <c:import url="../include/login.jsp"/>
            <aside class="note">
                <h3>Список форумов</h3>
            </aside>
            <sec:authorize access="isAuthenticated()">
            <form action="post" id="topicForm" style="margin-bottom: 10px;">
                <div style="margin-bottom: 10px;">
                    <label for="description"></label>
                    <textarea id="description" name="description" style="width: 100%; resize: none"></textarea>
                </div>
                <div style="text-align: center">
                    <input type="submit" class="btn btn-group-sm" value="Добавить форум">
                </div>
            </form>
            </sec:authorize>
            <div id="grid"></div>
            <div id="paginator"></div>

</body>
</html>
