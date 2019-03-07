<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:requestEncoding value="utf-8"/>
<fmt:setBundle basename="locale"  var="lang" scope="application"/>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Registration page</title>

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
    <link rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/static/vendors/iconfonts/mdi/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/vendors/css/vendor.bundle.base.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/css/style.css">
    <link rel="shortcut icon" href="${pageContext.servletContext.contextPath}/static/images/favicon.png"/>

    <style type="text/css">
        @-webkit-keyframes chartjs-render-animation {
            from {
                opacity: 0.99
            }
            to {
                opacity: 1
            }
        }

        @keyframes chartjs-render-animation {
            from {
                opacity: 0.99
            }
            to {
                opacity: 1
            }
        }

        .chartjs-render-monitor {
            -webkit-animation: chartjs-render-animation 0.001s;
            animation: chartjs-render-animation 0.001s;
        }</style>
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
                        <h4><fmt:message key="all.register.newhear" bundle="${lang}"/></h4>
                        <h6 class="font-weight-light"><h4><fmt:message key="all.register.welcom" bundle="${lang}"/></h4></h6>
                        <form method="POST" class="forms-sample" action="${pageContext.servletContext.contextPath}/buber"
                              id="form_register">

                            <input type="hidden" name="command" value="create_user">
                            <input type="hidden" name="role" value="client">
                            <input type="hidden" name="flag" value="client">
                            <div class="form-group">
                                <label for="login"><h4><fmt:message key="all.page.login" bundle="${lang}"/></h4></label>
                                <input name="login"  title="<fmt:message key="all.register.usernamerule" bundle="${lang}"/>" type="text" class="form-control" id="login" placeholder="<fmt:message key="all.page.username" bundle="${lang}"/>">
                            </div>
                            <div class="form-group">
                                <label for="passwordUser"><fmt:message key="all.page.password" bundle="${lang}"/></label>
                                <input name="passwordUser" type="password" class="form-control" id="passwordUser"
                                       placeholder="<fmt:message key="all.page.password" bundle="${lang}"/>">
                            </div>
                            <div class="form-group">
                                <label for="repasswordUser"><fmt:message key="all.page.repeatpas" bundle="${lang}"/></label>
                                <input name="repasswordUser" type="password" class="form-control" id="repasswordUser"
                                       placeholder="<fmt:message key="all.page.repeatpas" bundle="${lang}"/>">
                            </div>
                            <div class="form-group">
                                <label for="email"><fmt:message key="all.page.emailadr" bundle="${lang}"/></label>
                                <input name="email" type="text" class="form-control" id="email" placeholder="<fmt:message key="all.page.email" bundle="${lang}"/>">
                            </div>
                            <div class="form-group">
                                <label for="phone"><fmt:message key="all.page.numphone" bundle="${lang}"/></label>
                                <input name="phone" type="text" class="form-control" id="phone" value="+375"
                                       placeholder="+375XXXXXXXXX">
                            </div>
                            <div class="form-group">
                                <label for="first_name"><fmt:message key="all.page.fname" bundle="${lang}"/></label>
                                <input name="first_name" type="text" class="form-control" id="first_name"
                                       placeholder="<fmt:message key="all.page.fname" bundle="${lang}"/>">
                            </div>
                            <div class="mt-3">
                                <button name="button_register" type="button"
                                        onclick="valid(document.getElementById('form_register'))"
                                        class="btn btn-block btn-gradient-primary btn-lg font-weight-medium auth-form-btn">
                                    <fmt:message key="all.register.signup" bundle="${lang}"/>
                                </button>
                            </div>
                            <div class="text-center mt-4 font-weight-light">
                                <fmt:message key="all.register.footermessage" bundle="${lang}"/> <a href="${pageContext.servletContext.contextPath}"
                                                            class="text-primary"><fmt:message key="all.register.login" bundle="${lang}"/></a>
                            </div>
                        </form>
                    </div>

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
<c:if test="${message != null}">
    <div id="myModal"  class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header"><button class="close" type="button" data-dismiss="modal">x</button>
                </div>
                <h3 class="modal-title text-center">Message</h3>
                <div class="modal-body text-center">${message}</div>
                <div class="modal-footer"><button class="btn btn-default" type="button" data-dismiss="modal">Close</button></div>
            </div>
        </div>
    </div>
    <button id="error-gid" style="display: none" type="button" data-toggle="modal" data-target="#myModal"/>
    <script>$('#error-gid').trigger('click');</script>
</c:if>
</body>
</html>