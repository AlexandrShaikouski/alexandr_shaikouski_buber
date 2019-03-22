<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:requestEncoding value="utf-8"/>
<fmt:setBundle basename="locale"  var="lang" scope="application"/>
<c:import url="header.jsp"></c:import>
<c:choose>
    <c:when test="${not empty listUsers}">

        <div class="row">
            <div id="divTable" class="col-lg-12">
                <div class="card">
                    <div id="cardTable" class="card-body">
                        <table id="myTable" class="display text-center">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th><fmt:message key="all.register.login" bundle="${lang}"/></th>
                                <th><fmt:message key="all.page.emailadr" bundle="${lang}"/></th>
                                <th><fmt:message key="all.page.numphone" bundle="${lang}"/></th>
                                <th><fmt:message key="admin.lclient.info" bundle="${lang}"/></th>
                                <th><fmt:message key="all.page.delete" bundle="${lang}"/></th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${listUsers}" var="driver" varStatus="status">
                                <tr>
                                    <td>${driver.id}</td>
                                    <td>${driver.login}</td>
                                    <td>${driver.email}</td>
                                    <td>${driver.phone}</td>
                                    <td>
                                        <form method="post" action="${pageContext.servletContext.contextPath}/buber">
                                            <input type="hidden" name="command" value="info_user">
                                            <input name="role" type="hidden" value="driver"/>
                                            <input type="hidden" name="id" value="${driver.id}">
                                            <button class="btn btn-link" type="submit"><fmt:message key="admin.lclient.info" bundle="${lang}"/></button>
                                        </form>
                                    </td>
                                    <td>
                                        <form id="delete-user${driver.id}" method="post"
                                              action="${pageContext.servletContext.contextPath}/buber">
                                            <input name="command" type="hidden" value="delete_user"/>
                                            <input name="role" type="hidden" value="driver"/>
                                            <input name="id" type="hidden" value="${driver.id}"/>
                                            <button class="btn btn-link" type="button" onclick="confirmDelete(document.getElementById('delete-user${driver.id}'),'delete_user')"><fmt:message key="all.page.delete" bundle="${lang}"/></button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>

                            </tbody>

                        </table>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <h1 class="display1"><fmt:message key="admin.page.noresults" bundle="${lang}"/></h1>
    </c:otherwise>
</c:choose>
<c:import url="footer.jsp"></c:import>