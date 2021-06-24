<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


	<!-- Nav bar -->
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
	
				<a class="navbar-brand" href="dashboard">
					<spring:message code="label.title"/>
				</a>
			</div>
	
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	
				<ul class="nav navbar-nav navbar-right">
					<!-- Language -->
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false">
							<spring:message code="label.lang.language" />
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
						<c:forEach var="lang" items="en,fr">
							<li><a href="?lang=${ lang }"><spring:message code="label.lang.${ lang }" /></a></li>
						</c:forEach>
						</ul>
					</li>
					
					<!-- User -->
					<security:authorize access="isAuthenticated()">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false">
							<span class="glyphicon glyphicon-user"></span>
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="#">
									<security:authentication property="principal.username" /> 
								</a>
							</li>
							<li role="separator" class="divider"></li>
							<li>
								<a href="#">
									<form:form action="logout" method="POST">
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
										<span class='glyphicon glyphicon-log-out' aria-hidden='true'></span>
									    <input type="submit" value="<spring:message code='label.user.logout' />" class="btn btn-link">
									</form:form>
								</a>
							</li>
						</ul>
					</li>
					</security:authorize>
				</ul>
			</div>
		</div>
	</nav>