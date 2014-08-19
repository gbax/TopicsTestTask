<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script id="messageTemplate" type="text/template">

    <td>{{id}}</td>
    <td>{{message}}</td>
    <sec:authorize access="isAuthenticated()">
        {{#if canDelete}}
            <td><a href="#messages/{{id}}" class="delete">Удалить</a></td>
        {{/if}}
    </sec:authorize>
</script>