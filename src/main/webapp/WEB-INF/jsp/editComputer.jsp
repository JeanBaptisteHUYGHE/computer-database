<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application -
				Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id: ${ computerDto.id }</div>
					<h1>Edit Computer</h1>

					<form:form action="editComputer" method="POST" modelAttribute="computerDto">
						<form:input path="id" type="hidden" id="id" />
						<fieldset>
						
							<%-- Errors messages --%>
                        	<c:if test="${ not empty errorsList }">
                        	<div class="form-group alert alert-danger">
                        		<ul>
                        		<c:forEach items="${ errorsList }" var="errorMessage">
                                	<li>${ errorMessage }</li>
                                </c:forEach>
                        		</ul>
                                ${ errorMessage }
                            </div>
                        	</c:if>
                        	
                        	
							<div class="form-group">
                                <form:label path="name">Computer name</form:label>
                                <form:input path="name" type="text" class="form-control" id="computerName" placeholder="Computer name" value="${ computerDto.name }" required="" />
                            </div>
                            <div class="form-group">
                                <form:label path="introductionDate" >Introduced date</form:label>
                                <form:input path="introductionDate" type="date" class="form-control" id="introductionDate" placeholder="Introduced date" value="${ computerDto.introductionDate }" />
                            </div>
                            <div class="form-group">
                                <form:label path="discontinueDate">Discontinued date</form:label>
                                <form:input path="discontinueDate" type="date" class="form-control" id="discontinueDate" placeholder="Discontinued date"  value="${ computerDto.discontinueDate }" />
                            </div>
                            <div class="form-group">
                                <form:label path="companyId">Company</form:label>
                                <form:select path="companyId" class="form-control" id="companyId">
                                	<%-- Companies list --%>
                                	<option value="">--</option>
									<c:forEach items="${ companiesDtoList }" var="company">
                                    <option ${ company.id == computerDto.companyId ? "selected" : "" } value="${ company.id }">${ company.name }</option>
                                    </c:forEach>
                                </form:select>
                            </div>         
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>