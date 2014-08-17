<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<hr>
<sec:authorize access="isAuthenticated()">
    <a href="<c:url value="j_spring_security_logout" />" >Выйти</a>
</sec:authorize>
<sec:authorize access="!isAuthenticated()">
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