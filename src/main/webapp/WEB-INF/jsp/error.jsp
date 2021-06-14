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
            <div class="alert alert-danger">
                <h3><spring:message code="label.error" /> ${ errorCode }</h3>
                <hr>
                <p>
                	<spring:message code="label.error.description" />
                </p>
                <p>
                	${ errorMessage }
                </p>
            </div>
            <a href="dashboard" class="btn btn-default">
            	<spring:message code="label.common.navigation.home" />
           	</a>
		 </div>
    </section>

    <%-- Script --%>
	<jsp:include page="component/script.jsp" />

</body>
</html>