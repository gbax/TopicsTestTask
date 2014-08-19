<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script id="topicTemplate" type="text/template">

    <td>{{id}}</td>
    <td><a href="${pageContext.request.contextPath}/topic/{{id}}">{{description}}</a></td>
    <sec:authorize access="isAuthenticated()">
        {{#if canDelete}}
            <td><a href="#topic/{{id}}" class="delete">Удалить</a></td>
        {{/if}}
    </sec:authorize>
</script>