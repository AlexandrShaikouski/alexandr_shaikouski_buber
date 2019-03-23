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
    <style type="text/css">
        @media only screen and (max-width: 600px) {
            .content-wrapper {
                padding: 0;
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

            <div id="map" class="content-wrapper">
                <c:if test="${sessionScope.tripOrder eq null}">
                    <div class="content-wrapper d-flex align-items-center text-center error-page bg-primary">
                        <div class="row flex-grow">
                            <div class="col-lg-7 mx-auto text-white">
                                <div class="row align-items-center d-flex flex-row">
                                    <div class="col-lg-12 error-page-divider text-lg-left pl-lg-4">
                                        <h2><fmt:message key="all.page.hello" bundle="${lang}"/>, <fmt:message key="all.page.role.driver" bundle="${lang}"/></h2>
                                        <h3 class="font-weight-light"><fmt:message key="driver.page.message" bundle="${lang}"/></h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>

            <form id="formAcceptOrder" action="">
                <input type="hidden" name="command" value="accept_order">
                <input type="hidden" name="trip_order_id" value="${tripOrder.id}">
                <input type="hidden" name="client_id" value="${tripOrder.clientId}">
                <div id="buttons_accept" style="display: none" class="template-demo">

                    <button id="button_accept" onclick="acceptOrder()"
                            class="btn btn-lg btn-gradient-primary btn-fw"
                            type="button"
                    ><fmt:message key="all.page.acceptOrder"
                                  bundle="${lang}"/></button>
                    <button id="button_cancel_order" onclick="cancelOrder()"
                            class="btn btn-lg btn-gradient-light btn-fw"
                            type="button"
                    ><fmt:message key="all.page.cancel"
                                  bundle="${lang}"/></button>
                </div>
            </form>

            <form id="formPendingClient" action="">
                <input type="hidden" name="command" value="pending_client">
                <div id="buttons_pending" style="display: none" class="template-demo">

                    <button id="button_pending" onclick="pendingClient()"
                            class="btn btn-lg btn-gradient-primary btn-fw"
                            type="button"
                    ><fmt:message key="all.page.droveup"
                                  bundle="${lang}"/></button>
                </div>
            </form>

            <form id="formCompleteTrip" action="">
                <input type="hidden" name="command" value="complete_order_driver">
                <div id="buttons_complete" style="display: none" class="template-demo">
                    <div class="row">
                        <div class="col-12">
                            <span><fmt:message key="all.page.username"
                                               bundle="${lang}"/> : </span><span id="clientName"></span>
                        </div>
                        <div class="col-12">
                            <span><fmt:message key="all.page.numphone"
                                               bundle="${lang}"/> : </span><span id="clientPhone"></span>
                        </div>
                    </div>
                    <button id="button_complete" onclick="completeTrip()"
                            class="btn btn-lg btn-gradient-primary btn-fw"
                            type="button"
                    ><fmt:message key="all.page.complete"
                                  bundle="${lang}"/></button>
                    <button id="button_cancel_complete" onclick="cancelCompleteOrder()"
                            class="btn btn-lg btn-gradient-light btn-fw"
                            type="button"
                    ><fmt:message key="all.page.cancel"
                                  bundle="${lang}"/></button>
                </div>
            </form>

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
        setTripOrderStatus("${sessionScope.tripOrder.statusOrder}");
        setLocaleData('${locale}','<fmt:message key="client.map.costtrip" bundle="${lang}"/>');
    });
</script>
<bub:infoMessage/>

</body>
</html>