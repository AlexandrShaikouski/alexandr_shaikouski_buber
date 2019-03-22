<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="locale" value="${cookie['locale'].value}"/>
<fmt:setLocale value="${locale ne null ? locale : 'en'}"/>
<fmt:requestEncoding value="utf-8"/>
<fmt:setBundle basename="locale"  var="lang" scope="application"/>
<c:import url="header.jsp"/>
<div class="row flex-grow">
    <div class="col-lg-7 mx-auto text-white">
        <div class="row align-items-center d-flex flex-row">
            <div class="col-lg-6 text-lg-right pr-lg-4">
                <h1 class="display-1 mb-0"><fmt:message key="all.page.success" bundle="${lang}"/></h1>
            </div>
        </div>
    </div>
</div>
<c:import url="footer.jsp"></c:import>

