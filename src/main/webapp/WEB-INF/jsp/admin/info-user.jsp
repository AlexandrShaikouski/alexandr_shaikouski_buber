<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="header.jsp"></c:import>

<div class="row">
    <form id="form" method="post" action="${pageContext.servletContext.contextPath}/buber">
        <input type="hidden" name="command" value="update_user">
        <input type="hidden" name="id" value="${user.id}">
        <input type="hidden" name="role" value="${role}">
        <div class="row">
            <div class="col-md-4"><h2 class="text-black">Id : #${user.id}</h2></div>
            <div class="col-md-4"></div>
            <div class="col-md-4"><h4 class="text-secondary">Registration date : ${user.registrationTime}</h4></div>
        </div>
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <table class="table table-dark">
                        <thead>
                        <tr>
                            <th>Login</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Name</th>
                        </tr>
                        </thead>

                        <tbody>
                        <tr>
                            <td><input name="login" type="text" value="${user.login}"></td>
                            <td><input name="email" type="text" value="${user.email}"></td>
                            <td><input name="phone" type="text" value="${user.phone}"></td>
                            <td><input name="name" type="text" value="${user.firstName}"></td>
                        </tr>
                        </tbody>

                    </table>
                </div>
            </div>
        </div>
        <br>
        <div class="col-lg-12">
            <c:choose>
                <c:when test="${user.statusBan eq null}">
                    <h3 class="text-black">Ban : none</h3>
                </c:when>
                <c:otherwise>

                    <div class="modal-header">
                        <h3 class="text-black">Ban until ${user.statusBan}</h3>
                        <button class="close" type="button" onclick="confirmDelete(document.getElementById('form'),'delete_ban')">Delete</button>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-md-12"><h4 class="text-primary">Disable user at a certain time</h4></div>
        <div class="col-md-12">
            <div class="form-group">
                <label for="ban-time">Add ban</label>
                <select id="ban-time" class="form-control" name="ban_time">
                    <option value="none">none</option>
                    <option value="hour">Hour</option>
                    <option value="day">Day</option>
                    <option value="week">Week</option>
                    <option value="month">Month</option>
                    <option value="year">Year</option>
                </select>
            </div>
            <label for="count-time-ban">Count</label>
            <input id="count-time-ban" type="number" name="count_time_ban">
        </div>
        <br>
        <c:if test="${role == 'client'}">
            <div class="col-md-12">
                <c:choose>
                    <c:when test="${empty user.bonuses}">
                        <h3 class="text-black">Bonus : none</h3>
                    </c:when>
                    <c:otherwise>

                        <h3 class="text-black">This user has</h3>
                        <c:forEach items="${user.bonuses}" var="bonus" varStatus="status">
                            <div class="modal-header">
                                <input type="hidden" name="bonus_id_delete" value="${bonus.id}">
                                <h4 class="text-black">${bonus.name} bonus. With sale %${bonus.factor}</h4>
                                <button class="close" type="button" onclick="confirmDelete(document.getElementById('form'),'delete_bonus')">Delete</button>
                            </div>

                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                <div class="form-group">
                    <label for="create-bonus">Add bonus</label>
                    <select id="create-bonus" class="form-control" name="bonus_id">
                        <option value="-1">none</option>
                        <c:forEach items="${listBonuses}" var="bonus" varStatus="status">
                            <option value="${bonus.id}">${bonus.name}(${bonus.factor})</option>
                        </c:forEach>
                    </select>
                </div>

            </div>
        </c:if>

        <div class="col-md-12">
            <button class="btn btn-gradient-primary" type="submit" >Save</button>
            <button class="btn btn-light" type="reset">Cancel</button>
        </div>
    </form>
    <c:choose>
        <c:when test="${not empty listOrders}">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">All orders of user</h4>
                        <table class="table table-dark text-center">
                            <thead>
                            <tr>
                                <th>From</th>
                                <th>To</th>
                                <th>Price</th>
                                <c:choose>
                                    <c:when test="${role eq 'user'}">
                                        <th>Driver ID</th>
                                    </c:when>
                                    <c:otherwise>
                                        <th>Client ID</th>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                            </thead>

                            <tbody>

                            <c:forEach items="${listOrders}" var="order" varStatus="status">
                                <tr>
                                    <td>${order.from}</td>
                                    <td>${order.to}</td>
                                    <td>${order.price}</td>
                                    <c:choose>
                                        <c:when test="${role eq 'user'}">
                                            <td>${order.driverId}</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>${order.clientId}</td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>

                            </c:forEach>

                            </tbody>

                        </table>
                    </div>
                </div>
            </div>

        </c:when>
        <c:otherwise>
            <h1 class="display1">No orders</h1>
        </c:otherwise>
    </c:choose>
</div>


<c:import url="footer.jsp"></c:import>

