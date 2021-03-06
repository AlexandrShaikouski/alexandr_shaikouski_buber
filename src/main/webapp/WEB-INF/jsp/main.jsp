<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="bub" uri="bubertags" %>

<c:set var="locale" value="${cookie['locale'].value}"/>
<fmt:setLocale value="${locale ne null ? locale : 'en'}"/>
<fmt:requestEncoding value="utf-8"/>
<fmt:setBundle basename="locale" var="lang" scope="application"/>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Buber</title>

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
    <link rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/static/vendors/iconfonts/mdi/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/vendors/css/vendor.bundle.base.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/css/style.css">
    <link rel="shortcut icon" href="${pageContext.servletContext.contextPath}/static/images/favicon.png">
    <style type="text/css">
        @media only screen and (max-width: 600px) {
            .content-wrapper {
                padding: 0;
            }
            .screen-full{
                padding: 0;
            }
        }</style>
</head>

<body>

<div class="container-scroller">
    <div class="container-fluid page-body-wrapper full-page-wrapper">
        <div class="content-wrapper d-flex align-items-center auth">
            <div class="w-100">
                <div class="screen-full col-lg-4 mx-auto">
                    <div class="auth-form-light text-left p-5">
                        <div class="brand-logo">
                            <img src="${pageContext.servletContext.contextPath}/static/images/logo.png">
                        </div>
                        <h4><fmt:message key="all.signin.welcom" bundle="${lang}"/></h4>
                        <h6 class="font-weight-light"><fmt:message key="all.signin.continue" bundle="${lang}"/></h6>
                        <form method="POST" class="pt-3" action="${pageContext.servletContext.contextPath}/buber">
                            <input type="hidden" name="command" value="sign_in">
                            <div class="form-group">
                                <input type="text" name="login" class="form-control form-control-lg" required
                                       id="exampleInputEmail1"
                                       placeholder="<fmt:message key="all.page.login" bundle="${lang}"/>">
                            </div>
                            <div class="form-group">
                                <input type="password" name="passwordUser" required class="form-control form-control-lg"
                                       id="exampleInputPassword1"
                                       placeholder="<fmt:message key="all.page.password" bundle="${lang}"/>">
                            </div>
                            <div class="mt-3">
                                <button class="btn btn-block btn-gradient-primary btn-lg font-weight-medium auth-form-btn"
                                        type="submit"><fmt:message key="all.signin.signin" bundle="${lang}"/></button>
                            </div>
                            <div class="my-2 d-flex justify-content-between align-items-center">
                                <a href="${pageContext.servletContext.contextPath}/buber?command=reset_password"
                                   class="auth-link text-black"><fmt:message key="all.signin.repassword"
                                                                             bundle="${lang}"/></a>

                            </div>
                            <div class="text-center mt-4 font-weight-light">
                                <fmt:message key="all.signin.create.message" bundle="${lang}"/> <a
                                    href="${pageContext.servletContext.contextPath}/buber?command=register_page"
                                    class="text-primary"><fmt:message key="all.signin.create" bundle="${lang}"/></a>
                            </div>
                        </form>
                        <div class="text-center">
                            <form id="localeForm" action="${pageContext.servletContext.contextPath}/buber">
                                <input type="hidden" name="command" value="locale">
                                <div class="btn-group" role="group" aria-label="Basic example">
                                    <input type="hidden" name="localePage" value="en">
                                    <button id="locale_ru" value="ru" type="button"
                                            onclick="localeChange(document.getElementById('localeForm'))"
                                            class="btn btn-sm btn-link">RU
                                    </button>
                                    |
                                    <button id="locale_en" value="en" type="submit" class="btn btn-sm btn-link">EN
                                    </button>
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
<bub:infoMessage message="${message}"/>
<c:if test="${message != null}">
    <button id="error-gid" style="display: none" type="button" data-toggle="modal" data-target="#modalInfoMessage"/>
    <script>$('#error-gid').trigger('click');</script>
</c:if>

</body>
</html>