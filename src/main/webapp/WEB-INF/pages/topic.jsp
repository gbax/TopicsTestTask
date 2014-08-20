<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Список сообщений</title>
    <input type="hidden" id="topicId" value="${topic.id}"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/backgrid.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/extensions/paginator/backgrid-paginator.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/extensions/text-cell/backgrid-text-cell.css"/>

    <c:import url="../include/templates/messageTemplate.jsp"/>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/frameworks/underscore.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/frameworks/jquery-1.11.1.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/frameworks/backbone.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/frameworks/backbone.paginator.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/frameworks/handlebars-v1.3.0.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/frameworks/backgrid.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/extensions/paginator/backgrid-paginator.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/extensions/text-cell/backgrid-text-cell.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/application/application.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/application/models.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/application/views.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/application/collections.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/application/router.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/topic.js"></script>
    <style>

        html {
            height: 100%;
            width: 100%;
            overflow: hidden;
            min-width: 100%;
            min-height: 100%;
        }

        body {
            height: 100%;
            width: 100%;
            padding: 0;
            margin: 0;
        }

        .container {
            height: 100%;
            display: table;
            width: 100%;
            padding: 0;
        }

        .row {
            height: 100%;
            display: table-cell;
            vertical-align: middle;
        }

        .centering {
            float: none;
            margin: 0 auto;
        }

    </style>
</head>
<body>

<div class="container">
    <div class="row">
        <c:import url="../include/login.jsp"/>
        <aside class="note">
            <h3>Список сообщений форума ${topic.description}</h3>
        </aside>
        <sec:authorize access="isAuthenticated()">
            <div class="centering text-center span8">
                <form action="post" id="messageForm">
                    <div>
                        <label for="message"></label>
                        <textarea id="message" name="message"></textarea>
                    </div>
                    <div>
                        <input type="submit" class="btn btn-lg btn-default" value="Добавить сообщение">
                    </div>
                </form>

            </div>
        </sec:authorize>

        <div class="centering text-center span8">
            <div id="grid"></div>
        </div>

        <div class="centering text-center span8">

            <div id="paginator"></div>


        </div>
    </div>
</div>


</body>
</html>