<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="bub" uri="bubertags" %>

<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:requestEncoding value="utf-8"/>
<fmt:setBundle basename="locale"  var="lang" scope="application"/>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Reset password</title>

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/vendors/iconfonts/mdi/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/vendors/css/vendor.bundle.base.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/css/style.css">
    <link rel="shortcut icon" href="${pageContext.servletContext.contextPath}/static/images/favicon.png">

</head>

<body>
<div class="container-scroller">
    <div class="container-fluid page-body-wrapper full-page-wrapper">
        <div class="content-wrapper d-flex align-items-center auth">
            <div class="row w-100">
                <div class="col-lg-4 mx-auto">
                    <div class="auth-form-light text-left p-5">
                        <div class="brand-logo">
                            <img src="${pageContext.servletContext.contextPath}/static/images/logo.png">
                        </div>
                        <input type="hidden" style="background: none">
                        <c:choose>
                        <c:when test="${reset == null}">
                            <h4><fmt:message key="all.resetpass.message" bundle="${lang}"/></h4>
                            <form method="POST" class="pt-3" action="${pageContext.servletContext.contextPath}/buber">
                                <input type="hidden" name="command" value="reset_password">
                                <input type="hidden" name="reset" value="send_key">
                                <div class="form-group">
                                    <input name="email" type="text" required class="form-control" id="email" placeholder="<fmt:message key="all.page.email" bundle="${lang}"/>">
                                </div>
                                <div class="mt-3">
                                    <button class="btn btn-block btn-gradient-primary btn-lg font-weight-medium auth-form-btn"
                                            type="submit"><fmt:message key="all.resetpass.enter" bundle="${lang}"/>
                                    </button>
                                </div>
                                <div class="text-center mt-4 font-weight-light">
                                    <fmt:message key="all.signin.create.message" bundle="${lang}"/> <a
                                        href="${pageContext.servletContext.contextPath}/buber?command=register_page"
                                        class="text-primary"><fmt:message key="all.signin.create" bundle="${lang}"/></a>
                                </div>
                            </form>
                        </c:when>
                        <c:when test="${reset == 'send_key'}">
                            <h4><fmt:message key="all.resetpass.enterkey" bundle="${lang}"/></h4>
                            <form method="POST" class="pt-3" action="${pageContext.servletContext.contextPath}/buber">
                                <input type="hidden" name="command" value="reset_password">
                                <input type="hidden" name="reset" value="check_key">
                                <input type="hidden" name="role" value="${role}">
                                <input type="hidden" name="email" value="${email}">
                                <div class="form-group">
                                    <input name="key" type="text" required class="form-control" id="key" placeholder="<fmt:message key="all.resetpass.key" bundle="${lang}"/>">
                                </div>
                                <div class="mt-3">
                                    <button class="btn btn-block btn-gradient-primary btn-lg font-weight-medium auth-form-btn"
                                            type="submit"><fmt:message key="all.resetpass.enter" bundle="${lang}"/></button>
                                </div>
                            </form>
                        </c:when>
                        <c:when test="${reset == 'check_key'}">
                            <h4><fmt:message key="all.resetpass.newpass" bundle="${lang}"/></h4>
                            <form method="POST" class="pt-3" action="${pageContext.servletContext.contextPath}/buber" id="reset_password">
                                <input type="hidden" name="command" value="reset_password">
                                <input type="hidden" name="role" value="${role}">
                                <input type="hidden" name="email" value="${email}">
                                <input type="hidden" name="reset" value="enter_new_pass">
                                <div class="form-group">
                                    <input name="passwordUser" type="password" required class="form-control" id="passwordUser" placeholder="<fmt:message key="all.page.password" bundle="${lang}"/>">
                                </div>
                                <div class="form-group">
                                    <input name="repasswordUser" type="password" required class="form-control" id="repasswordUser" placeholder="<fmt:message key="all.page.repeatpas" bundle="${lang}"/>">
                                </div>
                                <div class="mt-3">
                                    <button name="button_reset_password" type="button" class="btn btn-block btn-gradient-primary btn-lg font-weight-medium auth-form-btn" onclick="validrepassword(document.getElementById('reset_password'))"><fmt:message key="all.resetpass.enter" bundle="${lang}"/></button>
                                </div>
                            </form>
                        </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.servletContext.contextPath}/static/vendors/js/vendor.bundle.base.js"></script>
<script src="${pageContext.servletContext.contextPath}/static/vendors/js/vendor.bundle.addons.js"></script>
<script src="${pageContext.servletContext.contextPath}/static/js/off-canvas.js"></script>
<script src="${pageContext.servletContext.contextPath}/static/js/misc.js"></script>
<script src="${pageContext.servletContext.contextPath}/static/js/script.js"></script>

</body>
</html>
<c:if test="${message != null}">
    <bub:infoMessage message="${message}"/>
    <button id="error-gid" style="display: none" type="button" data-toggle="modal" data-target="#modalInfoMessage"/>
    <script>$('#error-gid').trigger('click');</script>
</c:if>