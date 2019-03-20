<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:requestEncoding value="utf-8"/>
<fmt:setBundle basename="locale" var="lang" scope="application"/>

<c:import url="header.jsp"></c:import>
<div class="row">
    <div class="col-12 grid-margin stretch-card">
        <div class="card">
            <div class="card-body">
                <h4 class="card-title"><fmt:message key="admin.createuser.createuser" bundle="${lang}"/></h4>
                <form method="POST" class="forms-sample" action="${pageContext.servletContext.contextPath}/buber"
                      id="form">
                    <input type="hidden" name="flag" value="admin">
                    <input type="hidden" name="command" value="create_user">
                    <input type="hidden" name="flag" value="admin">
                    <div class="form-group">
                        <label for="login"><fmt:message key="all.register.login" bundle="${lang}"/></label>
                        <input name="login" required
                               title="<fmt:message key="all.register.usernamerule" bundle="${lang}"/>" type="text"
                               class="form-control" id="login"
                               placeholder="<fmt:message key="all.register.login" bundle="${lang}"/>">
                    </div>
                    <div class="form-group">
                        <label for="passwordUser"><fmt:message key="all.page.password" bundle="${lang}"/></label>
                        <input name="passwordUser" required type="password" class="form-control" id="passwordUser"
                               placeholder="<fmt:message key="all.page.password" bundle="${lang}"/>">
                    </div>
                    <div class="form-group">
                        <label for="repasswordUser"><fmt:message key="all.page.repeatpas" bundle="${lang}"/></label>
                        <input name="repasswordUser" required type="password" class="form-control" id="repasswordUser"
                               placeholder="<fmt:message key="all.page.repeatpas" bundle="${lang}"/>">
                    </div>
                    <div class="form-group">
                        <label for="email"><fmt:message key="all.page.emailadr" bundle="${lang}"/></label>
                        <input name="email" required type="text" class="form-control" id="email"
                               placeholder="<fmt:message key="all.page.email" bundle="${lang}"/>">
                    </div>
                    <div class="form-group">
                        <label for="phone"><fmt:message key="all.page.numphone" bundle="${lang}"/></label>
                        <input name="phone" required type="text" class="form-control" id="phone" value="+375"
                               placeholder="+375XXXXXXXXX">
                    </div>
                    <div class="form-group">
                        <label for="first_name"><fmt:message key="all.page.fname" bundle="${lang}"/></label>
                        <input name="first_name" required type="text" class="form-control" id="first_name"
                               placeholder="<fmt:message key="all.page.fname" bundle="${lang}"/>">
                    </div>
                    <div class="row form-group">
                        <div class="col-md-4 form-check">
                            <label class="form-check-label">
                                <input type="radio" class="form-check-input" name="role" id="optionsRadios1"
                                       value="admin">
                                <fmt:message key="all.page.role.admin" bundle="${lang}"/>
                                <i class="input-helper"></i></label>
                        </div>
                        <div class="col-md-4 form-check">
                            <label class="form-check-label">
                                <input type="radio" class="form-check-input" name="role" id="optionsRadios2"
                                       value="client" checked="">
                                <fmt:message key="all.page.role.client" bundle="${lang}"/>
                                <i class="input-helper"></i></label>
                        </div>
                        <div class="col-md-4 form-check">
                            <label class="form-check-label">
                                <input type="radio" class="form-check-input" name="role" id="optionsRadios3"
                                       value="driver" checked="">
                                <fmt:message key="all.page.role.driver" bundle="${lang}"/>
                                <i class="input-helper"></i></label>
                        </div>

                    </div>
                    <div class="template-demo">
                        <button name="button_register" type="button" onclick="valid(document.getElementById('form'),'flag')"
                                class="btn btn-gradient-primary mr-2 btn-fw"><fmt:message key="all.signin.create"
                                                                                          bundle="${lang}"/></button>
                        <button class="btn btn-light btn-fw" type="reset"><fmt:message key="all.page.cancel"
                                                                                       bundle="${lang}"/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<c:import url="footer.jsp"></c:import>