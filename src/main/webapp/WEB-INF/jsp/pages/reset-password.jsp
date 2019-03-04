<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                            <h4>Enter your email</h4>
                            <form method="POST" class="pt-3" action="${pageContext.servletContext.contextPath}/buber">
                                <input type="hidden" name="command" value="reset_password">
                                <input type="hidden" name="reset" value="1">
                                <div class="form-group">
                                    <input name="email" type="text" class="form-control" id="email" placeholder="Email">
                                </div>
                                <div class="mt-3">
                                    <button class="btn btn-block btn-gradient-primary btn-lg font-weight-medium auth-form-btn"
                                            type="submit">Enter
                                    </button>
                                </div>
                                <div class="text-center mt-4 font-weight-light">
                                    Don't have an account? <a
                                        href="${pageContext.servletContext.contextPath}/buber?command=register_page"
                                        class="text-primary">Create</a>
                                </div>
                            </form>
                        </c:when>
                        <c:when test="${reset == '1'}">
                            <h4>Enter key</h4>
                            <form method="POST" class="pt-3" action="${pageContext.servletContext.contextPath}/buber">
                                <input type="hidden" name="command" value="reset_password">
                                <input type="hidden" name="reset" value="2">
                                <input type="hidden" name="role" value="${role}">
                                <input type="hidden" name="email" value="${email}">
                                <div class="form-group">
                                    <input name="key" type="text" class="form-control" id="key" placeholder="Key">
                                </div>
                                <div class="mt-3">
                                    <button class="btn btn-block btn-gradient-primary btn-lg font-weight-medium auth-form-btn"
                                            type="submit">Enter</button>
                                </div>
                            </form>
                        </c:when>
                        <c:when test="${reset == '2'}">
                            <h4>New password</h4>
                            <form method="POST" class="pt-3" action="${pageContext.servletContext.contextPath}/buber" id="reset_password">
                                <input type="hidden" name="command" value="reset_password">
                                <input type="hidden" name="role" value="${role}">
                                <input type="hidden" name="email" value="${email}">
                                <input type="hidden" name="reset" value="3">
                                <div class="form-group">
                                    <input name="passwordUser" type="password" class="form-control" id="passwordUser" placeholder="Password">
                                </div>
                                <div class="form-group">
                                    <input name="repasswordUser" type="password" class="form-control" id="repasswordUser" placeholder="Repeat password">
                                </div>
                                <div class="mt-3">
                                    <button name="button_reset_password" type="button" class="btn btn-block btn-gradient-primary btn-lg font-weight-medium auth-form-btn" onclick="validrepassword(document.getElementById('reset_password'))">Enter</button>
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