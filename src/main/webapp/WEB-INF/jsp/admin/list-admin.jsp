<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="locale" value="${cookie['locale'].value}"/>
<fmt:setLocale value="${locale ne null ? locale : 'en'}"/>
<fmt:requestEncoding value="utf-8"/>
<fmt:setBundle basename="locale"  var="lang" scope="application"/>
<c:import url="header.jsp"/>
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
                            </tr>
                            </thead>

                            <tbody>

                            <c:forEach items="${listUsers}" var="client" varStatus="status">
                                <tr>
                                    <td>${client.id}</td>
                                    <td>${client.login}</td>
                                    <td>${client.email}</td>
                                    <td>${client.phone}</td>
                                </tr>

                            </c:forEach>

                            </tbody>

                        </table>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $(document).ready(function () {
                langTable('${locale}');
            });
        </script>
    </c:when>
    <c:otherwise>
        <h1 class="display1"><fmt:message key="admin.page.noresults" bundle="${lang}"/>s</h1>
    </c:otherwise>
</c:choose>
<c:import url="footer.jsp"></c:import>