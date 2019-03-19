<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="bub" uri="bubertags" %>

<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
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
                padding: 0;
            }

            footer {
                display: none;
            }
        }</style>
</head>
<body>

<div class="container-scroller">
    <nav class="navbar default-layout-navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
        <div class="text-center navbar-brand-wrapper d-none d-sm-block align-items-center justify-content-center">
            <a class="navbar-brand brand-logo" href="${pageContext.servletContext.contextPath}"><img
                    src="${pageContext.servletContext.contextPath}/static/images/logo.png" alt="logo"/></a>
            <a class="navbar-brand brand-logo-mini" href="${pageContext.servletContext.contextPath}"><img
                    src="${pageContext.servletContext.contextPath}/static/images/logo-mini.svg" alt="logo"/></a>
        </div>

        <div class="navbar-menu-wrapper d-flex align-items-stretch">
            <ul class="navbar-nav navbar-nav-right">
                <li class="nav-item nav-profile dropdown">

                    <div class="nav-profile-text">
                        <p class="mb-1 text-black"><fmt:message key="all.page.hello"
                                                                bundle="${lang}"/>, ${sessionScope.user.firstName}</p>
                    </div>


                </li>
                <li>
                    <form action="${pageContext.servletContext.contextPath}/buber">
                        <input type="hidden" name="command" value="sign_out">
                        <button type="submit" class="btn dropdown-item">
                            <i class="mdi mdi-logout mr-2 text-primary"></i>
                            <fmt:message key="all.page.signout" bundle="${lang}"/>
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
                <c:choose>
                    <c:when test="${not empty listOrders}">
                        <div class="col-lg-12">
                            <div class="card">
                                <div class="card-body">
                                    <h4 class="card-title"><fmt:message key="admin.infouser.order.message" bundle="${lang}"/></h4>
                                    <table class="table table-bordered table-dark text-center">
                                        <thead>
                                        <tr>
                                            <th><fmt:message key="admin.infouser.order.from" bundle="${lang}"/></th>
                                            <th><fmt:message key="admin.infouser.order.to" bundle="${lang}"/></th>
                                            <th><fmt:message key="admin.infouser.order.price" bundle="${lang}"/></th>
                                            <th><fmt:message key="admin.infouser.order.register" bundle="${lang}"/></th>
                                        </tr>
                                        </thead>

                                        <tbody>

                                        <c:forEach items="${listOrders}" var="order" varStatus="status">
                                            <tr>
                                                <td>${order.from}</td>
                                                <td>${order.to}</td>
                                                <td>${order.price}</td>
                                                <td><fmt:formatDate value="${order.dateCreate}" pattern="HH:mm dd.MM.yyyy" /></td>

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
<script src="${pageContext.servletContext.contextPath}/static/js/driver.js"></script>
<script src="${pageContext.servletContext.contextPath}/static/js/script.js"></script>
<script>
    $(document).ready(function () {
        setTripOrder("${sessionScope.tripOrder}");
    });
</script>
<bub:infoMessage/>

</body>
</html>