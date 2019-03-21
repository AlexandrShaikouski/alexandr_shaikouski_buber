<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="header.jsp"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:requestEncoding value="utf-8"/>
<fmt:setBundle basename="locale" var="lang" scope="application"/>
<div class="row">
    <form id="form" class="col-md-12" method="post" action="${pageContext.servletContext.contextPath}/buber">
        <input type="hidden" name="command" value="update_user">
        <input type="hidden" name="id" value="${user.id}">
        <input type="hidden" name="role" value="${role}">
        <div class="row">
            <div class="col-md-4"><h2 class="text-black">Id : #${user.id}</h2></div>
            <div class="col-md-4"></div>
            <div class="col-md-4"><h4 class="text-secondary"><fmt:message key="admin.infouser.regdate" bundle="${lang}"/> : ${user.registrationTime}</h4></div>
        </div>
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <table class="table table-dark text-center">
                        <thead>
                        <tr>
                            <th><fmt:message key="all.page.username" bundle="${lang}"/></th>
                            <th><fmt:message key="all.page.emailadr" bundle="${lang}"/></th>
                            <th><fmt:message key="all.page.numphone" bundle="${lang}"/></th>
                            <th><fmt:message key="all.page.fname" bundle="${lang}"/></th>
                        </tr>
                        </thead>

                        <tbody>
                        <tr>
                            <td><input name="login" type="hidden" value="${user.login}">${user.login}</td>
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
                    <h3 class="text-black"><fmt:message key="admin.infouser.ban.bannone" bundle="${lang}"/></h3>
                </c:when>
                <c:otherwise>

                    <div class="modal-header">
                        <h3 class="text-black"><fmt:message key="admin.infouser.ban.until"
                                                            bundle="${lang}"/> <fmt:formatDate value="${user.statusBan}" pattern="HH:mm dd-MM-yyyy" /></h3>
                        <button class="close" type="button"
                                onclick="confirmDelete(document.getElementById('form'),'delete_ban')"><fmt:message
                                key="all.page.delete" bundle="${lang}"/></button>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-md-12"><h4 class="text-primary"><fmt:message key="admin.infouser.ban.disable"
                                                                     bundle="${lang}"/></h4></div>
        <div class="col-md-12">
            <div class="form-group">
                <label for="ban-time"><fmt:message key="admin.infouser.ban.addban" bundle="${lang}"/></label>
                <select id="ban-time" class="form-control" name="ban_time">
                    <option value="none"><fmt:message key="admin.infouser.ban.none" bundle="${lang}"/></option>
                    <option value="hour"><fmt:message key="admin.infouser.ban.hour" bundle="${lang}"/></option>
                    <option value="day"><fmt:message key="admin.infouser.ban.day" bundle="${lang}"/></option>
                    <option value="week"><fmt:message key="admin.infouser.ban.week" bundle="${lang}"/></option>
                    <option value="month"><fmt:message key="admin.infouser.ban.month" bundle="${lang}"/></option>
                    <option value="year"><fmt:message key="admin.infouser.ban.year" bundle="${lang}"/></option>
                </select>
            </div>
            <label for="count-time-ban"><fmt:message key="admin.infouser.ban.count" bundle="${lang}"/></label>
            <input id="count-time-ban" type="number" max="999" name="count_time_ban">
        </div>
        <br>
        <c:if test="${role == 'client'}">
            <div class="col-md-12">
                <c:choose>
                    <c:when test="${empty user.bonuses}">
                        <h3 class="text-black"><fmt:message key="admin.infouser.bonus.nobonus" bundle="${lang}"/></h3>
                    </c:when>
                    <c:otherwise>

                        <h3 class="text-black"><fmt:message key="admin.infouser.bonus.hasbonus" bundle="${lang}"/></h3>
                        <c:forEach items="${user.bonuses}" var="bonus" varStatus="status">
                            <div class="modal-header">
                                <input type="hidden" name="bonus_id_delete" value="${bonus.id}">
                                <h4 class="text-black">${bonus.name} <fmt:message key="admin.infouser.bonus.withsale"
                                                                                  bundle="${lang}"/>
                                    %${bonus.factor}</h4>
                                <button class="close" type="button"
                                        onclick="confirmDelete(document.getElementById('form'),'delete_bonus')">
                                    <fmt:message key="all.page.delete" bundle="${lang}"/></button>
                            </div>

                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                <div class="form-group">
                    <label for="create-bonus"><fmt:message key="admin.infouser.bonus.addbonus"
                                                           bundle="${lang}"/></label>
                    <select id="create-bonus" class="form-control" name="bonus_id">
                        <option value="-1"><fmt:message key="admin.infouser.ban.none" bundle="${lang}"/></option>
                        <c:forEach items="${listBonuses}" var="bonus" varStatus="status">
                            <option value="${bonus.id}">${bonus.name}(${bonus.factor})</option>
                        </c:forEach>
                    </select>
                </div>

            </div>
        </c:if>

        <div class="template-demo">
            <button class="btn btn-gradient-primary btn-fw" type="submit"><fmt:message key="all.page.save"
                                                                                bundle="${lang}"/></button>
            <button class="btn btn-light btn-fw" type="reset"><fmt:message key="all.page.cancel" bundle="${lang}"/></button>
        </div>
    </form>
    <c:choose>
        <c:when test="${not empty listOrders}">
            <div id="divTable" class="col-lg-12">
                <div class="card">
                    <div id="cardTable" class="card-body">
                        <h4 class="card-title"><fmt:message key="admin.infouser.order.message" bundle="${lang}"/></h4>
                        <table class="table table-bordered table-dark text-center">
                            <thead>
                            <tr>
                                <th><fmt:message key="admin.infouser.order.from" bundle="${lang}"/></th>
                                <th><fmt:message key="admin.infouser.order.to" bundle="${lang}"/></th>
                                <th><fmt:message key="admin.infouser.order.price" bundle="${lang}"/></th>
                                <c:choose>
                                    <c:when test="${role eq 'client'}">
                                        <th><fmt:message key="admin.infouser.order.driverid" bundle="${lang}"/></th>
                                    </c:when>
                                    <c:otherwise>
                                        <th><fmt:message key="admin.infouser.order.clientid" bundle="${lang}"/></th>
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
                                        <c:when test="${role eq 'client'}">
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
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <h1 class="display1"><fmt:message key="admin.page.noresultsorder" bundle="${lang}"/></h1>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>


<c:import url="footer.jsp"></c:import>

