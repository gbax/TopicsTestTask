<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Application</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/backbone.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/underscore.js"></script>
</head>
<body>
<h2>Hey</h2>
<h4>Message: ${message}!</h4>
<hr>
<sec:authorize access="isAuthenticated()">
    1
</sec:authorize>
<sec:authorize access="!isAuthenticated()">
    0
</sec:authorize>
<hr>
<form name='f' action="<c:url value='j_spring_security_check' />" method='POST'>

    <div class="authBlock">
        <div class="authTitle">Введите логин и пароль для домена Aplana</div>
        <div class="authForm">
            <form action="index.html" method="POST" name="authForm">
                <label for="inputLogin">Логин: </label>
                <br/>
                <input id="inputLogin" type="text" name="j_username" value=""/>
                <br/>
                <label for="inputPass">Пароль: </label>
                <br/>
                <input id="inputPass" type="password" name="j_password" value=""/>
                <br/>
                <br/>
                <input class="btnSend" type="submit" value="Войти"/>
            </form>  <br>


            <%--<c:forEach items="${sessionScope}" var="e">--%>
            <%--<fmt:message key="${e.key}">--%>
            <%--<fmt:param value="${e.value}"/>--%>
            <%--</fmt:message><br>--%>
            <%--</c:forEach>--%>
        </div>
    </div>

</form>
</body>
</html>
