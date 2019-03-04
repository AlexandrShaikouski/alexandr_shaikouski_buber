<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<title>Main page</title>
<body>
<c:if test="${sessionScope.user != null}">
    <c:choose>
        <c:when test="${sessionScope.user.role == 'ADMIN'}">
            <jsp:forward page="/WEB-INF/jsp/admin/admin.jsp"/>
        </c:when>
        <c:when test="${sessionScope.user.role == 'CLIENT'}">
            <jsp:forward page="/WEB-INF/jsp/client/client.jsp"/>
        </c:when>
        <c:when test="${sessionScope.user.role == 'DRIVER'}">
            <jsp:forward page="/WEB-INF/jsp/driver/driver.jsp"/>
        </c:when>
    </c:choose>
</c:if>
<jsp:forward page="WEB-INF/jsp/main.jsp"/>
</body>
</html>