<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
					<div class="label label-default pull-right">id: ${ computerDto.id }</div>
					<h1><spring:message code="label.editComputer.title" /></h1>

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
                                <form:label path="name">
                                	<spring:message code="label.common.computer.name" />
                                </form:label>
                                <form:input path="name" type="text" class="form-control" id="computerName" 
                                placeholder="PC1" value="${ computerDto.name }" required="" />
                            </div>
                            <div class="form-group">
                                <form:label path="introductionDate" >
                                	<spring:message code="label.common.computer.introductionDate" />
                                </form:label>
                                <form:input path="introductionDate" type="date" class="form-control" id="introductionDate" value="${ computerDto.introductionDate }" />
                            </div>
                            <div class="form-group">
                                <form:label path="discontinueDate">
                                	<spring:message code="label.common.computer.discontinueDate" />
                                </form:label>
                                <form:input path="discontinueDate" type="date" class="form-control" id="discontinueDate" value="${ computerDto.discontinueDate }" />
                            </div>
                            <div class="form-group">
                                <form:label path="companyId">
                                	<spring:message code="label.common.computer.company" />
                               	</form:label>
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
							<input type="submit" value='<spring:message code="label.editComputer.button.edit" />' class="btn btn-primary">
							or
							<a href="dashboard" class="btn btn-default">
								<spring:message code="label.common.navigation.cancel" />
							</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	
	<%-- Script --%>
	<jsp:include page="component/script.jsp" />
	
</body>
</html>