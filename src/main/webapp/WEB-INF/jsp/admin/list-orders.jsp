<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:requestEncoding value="utf-8"/>
<fmt:setBundle basename="locale"  var="lang" scope="application"/>
<c:import url="header.jsp"></c:import>
<c:choose>
    <c:when test="${not empty listOrders}">

        <div class="row">
            <div id="divTable" class="col-lg-12">
                <div class="card">
                    <div id="cardTable" class="card-body">
                        <table id="myTable" class="display text-center">
                            <thead>
                            <tr>
                                <th><fmt:message key="admin.infouser.order.from" bundle="${lang}"/></th>
                                <th><fmt:message key="admin.infouser.order.to" bundle="${lang}"/></th>
                                <th><fmt:message key="admin.infouser.order.price" bundle="${lang}"/></th>
                                <th><fmt:message key="admin.infouser.order.status" bundle="${lang}"/></th>
                                <th><fmt:message key="admin.infouser.order.clientid" bundle="${lang}"/></th>
                                <th><fmt:message key="admin.infouser.order.driverid" bundle="${lang}"/></th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${listOrders}" var="order" varStatus="status">
                                <tr>
                                    <td>${order.from}</td>
                                    <td>${order.to}</td>
                                    <td>${order.price}</td>
                                    <td>${order.statusOrder.value()}</td>
                                    <td>${order.clientId}</td>
                                    <td>${order.driverId}</td>
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