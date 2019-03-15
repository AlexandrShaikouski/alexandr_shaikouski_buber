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
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <table class="table table-bordered table-dark text-center">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th><fmt:message key="all.register.login" bundle="${lang}"/></th>
                                <th><fmt:message key="all.page.email" bundle="${lang}"/></th>
                                <th><fmt:message key="all.page.numphone" bundle="${lang}"/></th>
                                <th><fmt:message key="admin.lclient.info" bundle="${lang}"/></th>
                                <th><fmt:message key="all.page.delete" bundle="${lang}"/></th>
                            </tr>
                            </thead>

                            <tbody>

                            <c:forEach items="${listUsers}" var="client" varStatus="status">
                                <tr>
                                    <td>${client.id}</td>
                                    <td>${client.login}</td>
                                    <td>${client.email}</td>
                                    <td>${client.phone}</td>

                                    <td>
                                        <form method="post" action="${pageContext.servletContext.contextPath}/buber">
                                            <input name="role" type="hidden" value="client"/>
                                            <input type="hidden" name="command" value="info_user"/>
                                            <input type="hidden" name="id" value="${client.id}">
                                            <button class="btn btn-link" type="submit"><fmt:message key="admin.lclient.info" bundle="${lang}"/></button>
                                        </form>
                                    </td>
                                    <td>
                                        <form id="delete-user${client.id}" method="post"
                                              action="${pageContext.servletContext.contextPath}/buber">
                                            <input name="command" type="hidden" value="delete_user"/>
                                            <input name="role" type="hidden" value="client"/>
                                            <input name="id" type="hidden" value="${client.id}"/>
                                            <button class="btn btn-link" type="button" onclick="confirmDelete(document.getElementById('delete-user${client.id}'),'delete_user')"><fmt:message key="all.page.delete" bundle="${lang}"/></button>
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