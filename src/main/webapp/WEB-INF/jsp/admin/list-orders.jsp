<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="header.jsp"></c:import>
<c:choose>
    <c:when test="${not empty listOrders}">

        <div class="row">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <table class="table table-dark text-center">
                            <thead>
                            <tr>
                                <th>From</th>
                                <th>To</th>
                                <th>Price</th>
                                <th>Status</th>
                                <th>Client ID</th>
                                <th>Driver ID</th>
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
        <h1 class="display1">No results</h1>
    </c:otherwise>
</c:choose>
<c:import url="footer.jsp"></c:import>