<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="locale" value="${cookie['locale'].value}"/>
<fmt:setLocale value="${locale ne null ? locale : 'en'}"/>
<fmt:requestEncoding value="utf-8"/>
<fmt:setBundle basename="locale"  var="lang" scope="application"/>
<c:import url="header.jsp"></c:import>

<div class="content-wrapper d-flex align-items-center text-center error-page bg-primary">
    <div class="row flex-grow">
        <div class="col-lg-7 mx-auto text-white">
            <div class="row align-items-center d-flex flex-row">
                <div class="col-lg-12 error-page-divider text-lg-left pl-lg-4">
                    <h2><fmt:message key="all.page.hello" bundle="${lang}"/>, <fmt:message key="all.page.role.admin" bundle="${lang}"/></h2>
                    <h3 class="font-weight-light"><fmt:message key="admin.mainpage.message" bundle="${lang}"/></h3>
                </div>
            </div>
        </div>
    </div>
</div>

<c:import url="footer.jsp"></c:import>
