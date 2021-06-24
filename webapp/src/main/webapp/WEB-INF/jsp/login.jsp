<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
	<jsp:include page="component/head.jsp" />
</head>
<body>
    
	<%-- Nav bar --%>
	<jsp:include page="component/navbar.jsp" />

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="label.login.title" /></h1>
                    <form action="login" method="POST" name="f">
                        <fieldset>
                        	<%-- Error message --%>
                        	<c:if test="${ error }">
                        	<div class="form-group alert alert-danger">
                        		<p><spring:message code="label.login.error" /></p>
                                ${ errorMessage }
                            </div>
                        	</c:if>
                            <div class="form-group">
                                <label for="username">
                                	<spring:message code="label.login.field.username" />
                                </label>
                                <input name="username" value="${ username }" type="text" class="form-control" required />
                            </div>
                            <div class="form-group">
                                <label for="password">
                                	<spring:message code="label.login.field.password" />
								</label>
                                <input name="password" value="" type="password" class="form-control" required />
                            </div>               
                        </fieldset>
                        <div class="actions pull-right">
                        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <input type="submit" value='<spring:message code="label.login.button" />' class="btn btn-primary">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    
    <%-- Script --%>
    <jsp:include page="component/script.jsp" />
    <script src="static/js/addEditComputerValidator.js"></script>
</body>
</html>