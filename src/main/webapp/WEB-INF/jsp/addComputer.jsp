<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Computer</h1>
                    <form:form action="addComputer" method="POST" modelAttribute="computerDto">
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
                                <form:input path="name" value="${ computerDto.name }" type="text" class="form-control" placeholder="Computer name" required="true" />
                            </div>
                            <div class="form-group">
                                <form:label path="introductionDate">Introduced date</form:label>
                                <form:input path="introductionDate" value="${ computerDto.introductionDate }" type="date" class="form-control" id="introductionDate" placeholder="Introduced date" />
                            </div>
                            <div class="form-group">
                                <form:label path="discontinueDate">Discontinued date</form:label>
                                <form:input path="discontinueDate" value="${ computerDto.discontinueDate }" type="date" class="form-control" id="discontinueDate" placeholder="Discontinued date" />
                            </div>
                            <div class="form-group">
                                <form:label path="companyId">Company</form:label>
                                <form:select path="companyId" value="${ computerDto.companyId }" class="form-control" id="companyId">
                                	<%-- Companies list --%>
                                	<option value="" selected>--</option>
									<c:forEach items="${ companiesDtoList }" var="company">
                                    <option value="${ company.id }">${ company.name }</option>
                                    </c:forEach>
                                </form:select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>