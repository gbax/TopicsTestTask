<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<hr>
<sec:authorize access="isAuthenticated()">
    <a href="<c:url value="j_spring_security_logout" />" >Выйти</a>
</sec:authorize>
<sec:authorize access="!isAuthenticated()">
    <c:if test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}">
        <div class="alert alert-danger">
                Попытка входа  была неудачна. Попробуйте еще раз.<br/>
                Причина: <label id="error">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</label>.<br>
        </div>
    </c:if>


    <form name='f' action="<c:url value='j_spring_security_check' />" method='POST'>

        <div class="authBlock">
            <div class="authTitle">Введите логин и пароль</div>
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
            </div>
        </div>
    </form>
</sec:authorize>
<hr>