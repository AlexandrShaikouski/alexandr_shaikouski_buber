<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="bub" uri="bubertags" %>

<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
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
    <link rel="shortcut icon" href="${pageContext.servletContext.contextPath}/static/images/favicon.png"/>
    <script src="https://api-maps.yandex.ru/2.1/?apikey=34223f99-cf9b-42e5-99f3-79fa5603abbb&lang=ru_RU"
            type="text/javascript">
    </script>
    <style type="text/css">
        @media only screen and (max-width: 600px) {
            .content-wrapper {
                padding: 0;
            }
            footer{
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
                    <a class="nav-link" data-toggle="collapse" href="#ui-basic" aria-expanded="false"
                       aria-controls="ui-basic">
                        <span class="menu-title"><fmt:message key="all.page.menu" bundle="${lang}"/></span>
                        <i class="menu-arrow"></i>
                        <i class="mdi mdi-crosshairs-gps menu-icon"></i>
                    </a>
                    <div class="collapse" id="ui-basic">
                        <ul class="nav flex-column sub-menu">
                            <li class="nav-item">
                                <form style="margin: 0" action="${pageContext.servletContext.contextPath}/buber">
                                    <input type="hidden" name="command" value="list_clients">
                                    <button class="btn btn-link btn-sm" type="submit"><fmt:message
                                            key="admin.header.lclient" bundle="${lang}"/></button>
                                </form>
                            </li>
                            <li class="nav-item">
                                <form style="margin: 0" action="${pageContext.servletContext.contextPath}/buber">
                                    <input type="hidden" name="command" value="list_drivers">
                                    <button class="btn btn-link btn-sm" type="submit"><fmt:message
                                            key="admin.header.ldriver" bundle="${lang}"/></button>
                                </form>
                            </li>
                            <li class="nav-item">
                                <form style="margin: 0" action="${pageContext.servletContext.contextPath}/buber">
                                    <input type="hidden" name="command" value="list_admin">
                                    <button class="btn btn-link btn-sm" type="submit"><fmt:message
                                            key="admin.header.ladmin" bundle="${lang}"/></button>
                                </form>
                            </li>
                            <li class="nav-item">
                                <form style="margin: 0" action="${pageContext.servletContext.contextPath}/buber">
                                    <input type="hidden" name="command" value="list_orders">
                                    <button class="btn btn-link btn-sm" type="submit"><fmt:message
                                            key="admin.header.lorders" bundle="${lang}"/></button>
                                </form>
                            </li>
                            <li class="nav-item">
                                <form style="margin: 0" action="${pageContext.servletContext.contextPath}/buber">
                                    <input type="hidden" name="command" value="create_page">
                                    <button class="btn btn-link btn-sm" type="submit"><fmt:message
                                            key="admin.header.createuser" bundle="${lang}"/></button>
                                </form>
                            </li>
                        </ul>
                    </div>
                </li>
            </ul>
        </nav>
        <div class="main-panel">

            <div id="<c:if test="${sessionScope.tripOrder == null}">map</c:if>" class="content-wrapper">

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
<script src="${pageContext.servletContext.contextPath}/static/js/client.js"></script>
<script src="${pageContext.servletContext.contextPath}/static/js/script.js"></script>

<bub:infoMessage/>


<div id="modalOrder" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <h3 class="modal-title text-center"><fmt:message key="client.footer.confirmorder" bundle="${lang}"/></h3>
            <div class="modal-body"><fmt:message key="admin.infouser.order.from" bundle="${lang}"/>:<p id="from"></p>
            </div>
            <div class="modal-body"><fmt:message key="admin.infouser.order.to" bundle="${lang}"/>:<p id="to"></p></div>
            <div class="modal-body"><fmt:message key="admin.infouser.order.price" bundle="${lang}"/>:<p id="price"></p>
            </div>
            <form id="formOrder" action="">
                <c:if test="${not empty sessionScope.user.bonuses}">
                    <div class="modal-body">
                        <label for="use-bonus"><fmt:message key="client.footer.usebonus" bundle="${lang}"/></label>
                        <select id="use-bonus" class="form-control" name="bonus_id">
                            <option id="factor0" value="0"><fmt:message key="admin.infouser.ban.none" bundle="${lang}"/></option>
                            <c:forEach items="${sessionScope.user.bonuses}" var="bonus" varStatus="status">
                                <option id="factor${bonus.id}" value="${bonus.id}">${bonus.name}(${bonus.factor})</option>
                            </c:forEach>
                        </select>
                    </div>
                </c:if>
                <div class="modal-footer">
                    <input type="hidden" name="command" value="order">
                    <input type="hidden" name="from" value="">
                    <input type="hidden" name="to" value="">
                    <input type="hidden" name="price" value="">
                    <button class="btn btn-default" type="button" onclick="ajaxOrder()" data-dismiss="modal">
                        <fmt:message
                                key="all.page.confirm"
                                bundle="${lang}"/></button>
                    <button class="btn btn-default" type="button" data-dismiss="modal"><fmt:message
                            key="all.page.cancel"
                            bundle="${lang}"/></button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
