<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

					<form action="editComputer?id=${ computerDto.id }" method="POST">
						<input type="hidden" value="${ computerDto.id }" id="id" name="computerId" />
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
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name" value="${ computerDto.name }">
                            </div>
                            <div class="form-group">
                                <label for="introductionDate">Introduced date</label>
                                <input type="date" class="form-control" id="introductionDate" name="introductionDate" placeholder="Introduced date" value="${ computerDto.introductionDate }">
                            </div>
                            <div class="form-group">
                                <label for="discontinueDate">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinueDate" name="discontinueDate" placeholder="Discontinued date"  value="${ computerDto.discontinueDate }">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId">
                                	<%-- Companies list --%>
                                	<option value="">--</option>
									<c:forEach items="${ companiesDtoList }" var="company">
                                    <option ${ company.id == computerDto.companyId ? "selected" : "" } value="${ company.id }">${ company.name }</option>
                                    </c:forEach>
                                </select>
                            </div>         
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>