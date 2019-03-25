<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="locale" value="${cookie['locale'].value}"/>
<fmt:setLocale value="${locale ne null ? locale : 'en'}"/>
<fmt:requestEncoding value="utf-8"/>
<fmt:setBundle basename="locale"  var="lang" scope="application"/>
<c:import url="header.jsp"/>
<c:choose>
    <c:when test="${not empty listOrders}">
        <div class="row"><div class="col-12">
            <h4 class="card-title"><fmt:message key="admin.infouser.order.message"
                                                bundle="${lang}"/></h4>
        </div></div>
        <table id="myTable" style="display: none" class="display">
            <thead>
            <tr>
                <th><fmt:message key="admin.infouser.order.from" bundle="${lang}"/></th>
                <th><fmt:message key="admin.infouser.order.to" bundle="${lang}"/></th>
                <th><fmt:message key="admin.infouser.order.price" bundle="${lang}"/></th>
                <th><fmt:message key="admin.infouser.order.register" bundle="${lang}"/></th>
                <th><fmt:message key="admin.infouser.order.status" bundle="${lang}"/></th>
                <th><fmt:message key="all.page.role.client" bundle="${lang}"/></th>
                <th><fmt:message key="all.page.role.driver" bundle="${lang}"/></th>
            </tr>
            </thead>

            <tbody>
            <c:set var="count" value='${0}' scope="page"/>
            <c:forEach items="${listOrders}" var="order" varStatus="status">
                <tr>
                    <td id="cordinate${count}" class="cord" id="from${order.id}">${order.from}</td>
                    <c:set var="count" value="${count + 1}" scope="page"/>
                    <td id="cordinate${count}" class="cord" id="to${order.id}">${order.to}</td>
                    <c:set var="count" value="${count + 1}" scope="page"/>
                    <td>${order.price}</td>
                    <td><fmt:formatDate value="${order.dateCreate}"
                                        pattern="HH:mm dd.MM.yyyy"/></td>
                    <td>${order.statusOrder.value()}</td>
                    <td>
                        <form method="post" action="${pageContext.servletContext.contextPath}/buber">
                            <input name="role" type="hidden" value="client"/>
                            <input type="hidden" name="command" value="info_user"/>
                            <input type="hidden" name="id" value="${order.clientId}">
                            <button class="btn btn-link" type="submit"><fmt:message key="admin.lclient.info"
                                                                                    bundle="${lang}"/></button>
                        </form>
                    </td>
                    <td>
                        <form method="post" action="${pageContext.servletContext.contextPath}/buber">
                            <input name="role" type="hidden" value="driver"/>
                            <input type="hidden" name="command" value="info_user"/>
                            <input type="hidden" name="id" value="${order.driverId}">
                            <button class="btn btn-link" type="submit"><fmt:message key="admin.lclient.info"
                                                                                    bundle="${lang}"/></button>
                        </form>
                    </td>

                </tr>

            </c:forEach>

            </tbody>

        </table>
        <script>
            $(document).ready(function () {
                ymaps.ready(function () {
                    changeAddress('${listOrders.size()}','${locale}');
                });
            });
        </script>
    </c:when>
    <c:otherwise>
        <div class="col-lg-12">
            <div class="card">
                <div class="card-body">
                    <h1 class="display1"><fmt:message key="admin.page.noresults"
                                                      bundle="${lang}"/></h1>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>
<c:import url="footer.jsp"></c:import>