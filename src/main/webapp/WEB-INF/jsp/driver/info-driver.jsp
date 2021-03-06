<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="bub" uri="bubertags" %>

<c:set var="locale" value="${cookie['locale'].value}"/>
<fmt:setLocale value="${locale ne null ? locale : 'en'}"/>
<fmt:requestEncoding value="utf-8"/>
<fmt:setBundle basename="locale" var="lang" scope="application"/>
<c:set var="tripOrder" value="${sessionScope.tripOrder}"/>

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
    <link rel="shortcut icon" href="${pageContext.servletContext.contextPath}/static/images/favicon.png"/>
    <script src="https://api-maps.yandex.ru/2.1/?apikey=34223f99-cf9b-42e5-99f3-79fa5603abbb&lang=ru_RU"
            type="text/javascript">
    </script>
    <style type="text/css">
        @media only screen and (max-width: 600px) {
            .content-wrapper {
                padding: 10px;
            }

            footer,#signOut,#welcom {
                display: none;
            }
        }</style>
</head>
<body>

<div class="container-scroller">
    <nav class="navbar default-layout-navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
        <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
            <a class="navbar-brand brand-logo" href="${pageContext.servletContext.contextPath}"><img
                    src="${pageContext.servletContext.contextPath}/static/images/logo.png" alt="logo"/></a>
            <a class="navbar-brand brand-logo-mini" href="${pageContext.servletContext.contextPath}"><img
                    src="${pageContext.servletContext.contextPath}/static/images/logo-mini.svg" alt="logo"/></a>
        </div>

        <div class="navbar-menu-wrapper d-flex align-items-stretch">
            <ul class="navbar-nav navbar-nav-right">
                <li class="nav-item nav-profile dropdown">

                    <div id="welcom" class="nav-profile-text">
                        <p class="mb-1 text-black"><fmt:message key="all.page.hello"
                                                                bundle="${lang}"/>, ${sessionScope.user.firstName}</p>
                    </div>


                </li>
                <li>
                    <form action="${pageContext.servletContext.contextPath}/buber">
                        <input type="hidden" name="command" value="sign_out">
                        <button type="submit" class="btn dropdown-item">
                            <i class="mdi mdi-logout mr-2 text-primary"></i>
                            <span id="signOut"><fmt:message key="all.page.signout" bundle="${lang}"/></span>
                        </button>
                    </form>
                </li>
                <li>
                    <form id="localeForm" action="${pageContext.servletContext.contextPath}/buber">
                        <input type="hidden" name="command" value="locale">
                        <div class="btn-group" role="group" aria-label="Basic example">
                            <input type="hidden" name="localePage" value="en">
                            <button id="locale_ru" value="ru" type="button"
                                    onclick="localeChange(document.getElementById('localeForm'))"
                                    class="btn btn-sm btn-link">RU
                            </button>
                            |
                            <button id="locale_en" value="en" type="submit" class="btn btn-sm btn-link">EN</button>
                        </div>
                    </form>
                </li>
            </ul>
            <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button"
                    data-toggle="offcanvas">
                <span class="mdi mdi-menu"></span>
            </button>
        </div>
    </nav>

    <div class="container-fluid page-body-wrapper">

        <nav class="sidebar sidebar-offcanvas" id="sidebar">
            <ul class="nav">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.servletContext.contextPath}">
                        <span class="menu-title">Buber</span>
                        <i class="mdi mdi-home menu-icon"></i>
                    </a>
                </li>
                <li class="nav-item">
                    <ul class="nav flex-column sub-menu">
                        <li class="nav-item">
                            <a class="btn btn-link btn-sm"
                               href="${pageContext.servletContext.contextPath}/index.jsp"><fmt:message
                                    key="all.page.mainpage" bundle="${lang}"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-link btn-sm"
                               href="${pageContext.servletContext.contextPath}/buber?command=personal_data"><fmt:message
                                    key="all.page.personaldata" bundle="${lang}"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-link btn-sm"
                               href="${pageContext.servletContext.contextPath}/buber?command=list_orders"><fmt:message
                                    key="admin.header.lorders" bundle="${lang}"/></a>
                        </li>
                    </ul>
            </ul>
        </nav>
        </nav>
        <div class="main-panel">

            <div class="content-wrapper">

                <c:if test="${user ne null}">
                    <form method="POST" class="forms-sample" action="${pageContext.servletContext.contextPath}/buber"
                          id="form_update">

                        <input type="hidden" name="command" value="update_user">
                        <input type="hidden" name="id" value="${user.id}">
                        <div class="form-group">
                            <label for="login"><h4><fmt:message key="all.page.login" bundle="${lang}"/></h4></label>
                            <input name="login" readonly type="text" class="form-control" id="login"
                                   placeholder="<fmt:message key="all.page.username" bundle="${lang}"/>"
                                   value="${user.login}">
                        </div>
                        <div class="form-group">
                            <label for="email"><fmt:message key="all.page.emailadr" bundle="${lang}"/></label>
                            <input name="email"  data-toggle="tooltip" data-placement="bottom"  title="<fmt:message key="all.register.emailrule" bundle="${lang}"/>" type="text" class="form-control" id="email"
                                   placeholder="<fmt:message key="all.page.email" bundle="${lang}"/>"
                                   value="${user.email}">
                        </div>
                        <div class="form-group">
                            <label for="phone"><fmt:message key="all.page.numphone" bundle="${lang}"/></label>
                            <input name="phone" data-toggle="tooltip" data-placement="bottom"  title="<fmt:message key="all.register.phonerule" bundle="${lang}"/>" type="text" class="form-control" id="phone"
                                   placeholder="<fmt:message key="all.page.numphone" bundle="${lang}"/>"
                                   value="${user.phone}">
                        </div>
                        <div class="form-group">
                            <label for="first_name"><fmt:message key="all.page.fname" bundle="${lang}"/></label>
                            <input name="name" data-toggle="tooltip" data-placement="bottom"  title="<fmt:message key="all.register.namerule" bundle="${lang}"/>" type="text" class="form-control" id="first_name"
                                   placeholder="<fmt:message key="all.page.fname" bundle="${lang}"/>"
                                   value="${user.firstName}">
                        </div>
                        <div class="template-demo">
                            <button name="button_register" type="button"
                                    onclick="valid(document.getElementById('form_update'))"
                                    class="btn btn-gradient-primary btn-lg btn-fw">
                                <fmt:message key="all.page.save" bundle="${lang}"/>
                            </button>
                            <button class="btn btn-light btn-fw" type="reset"><fmt:message key="all.page.cancel"
                                                                                           bundle="${lang}"/></button>
                        </div>


                    </form>
                </c:if>
            </div>
            <footer class="footer">
                <div class="d-sm-flex justify-content-center justify-content-sm-between">
        <span class="text-muted text-center text-sm-left d-block d-sm-inline-block"><fmt:message
                key="all.footer.copyright" bundle="${lang}"/></span>
                </div>
            </footer>

        </div>
    </div>
</div>
<script src="${pageContext.servletContext.contextPath}/static/vendors/js/vendor.bundle.base.js"></script>
<script src="${pageContext.servletContext.contextPath}/static/vendors/js/vendor.bundle.addons.js"></script>
<script src="${pageContext.servletContext.contextPath}/static/js/off-canvas.js"></script>
<script src="${pageContext.servletContext.contextPath}/static/js/dashboard.js"></script>
<script src="${pageContext.servletContext.contextPath}/static/js/script.js"></script>
<bub:infoMessage/>
<c:if test="${message != null}">
    <script>
        $('#infoMessage').html('${message}');
        $('#modalInfoMessage').modal('show');
    </script>
</c:if>
</body>
</html>