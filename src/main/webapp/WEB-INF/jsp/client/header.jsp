<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

                    <div class="nav-profile-text">
                        <p class="mb-1 text-black">Hello, ${sessionScope.user.firstName}</p>
                    </div>


                </li>
                <li>
                    <form action="${pageContext.servletContext.contextPath}/buber">
                        <input type="hidden" name="command" value="sign_out">
                        <button type="submit" class="btn dropdown-item">
                            <i class="mdi mdi-logout mr-2 text-primary"></i>
                            Signout
                        </button>
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

        <div class="main-panel">
            <div class="content-wrapper">