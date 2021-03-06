<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

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
			<h1 id="homeTitle">${ computersCount } <spring:message code="label.dashboard.computersFound"/></h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder='<spring:message code="label.dashboard.searchBarMessage"/>' value="${ search }"/>
						<input
							type="submit" id="searchsubmit" value='<spring:message code="label.dashboard.button.filterByName"/>'
							class="btn btn-primary" />
					</form>
				</div>
				<%-- Administrator buttons --%>
				<security:authorize access="hasAuthority('admin')">
					<div class="pull-right">
						<a class="btn btn-success" id="addComputer" href="addComputer">
							<spring:message code="label.dashboard.button.addComputer"/>
						</a>
						<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">
							<spring:message code="label.dashboard.button.edit"/>
						</a>
					</div>
				</security:authorize>
			</div>
		</div>
		
		<div class="container">
			<%-- Errors messages --%>
           	<c:if test="${ not empty errorsList }">
           	<p></p>
           	<div class="form-group alert alert-danger">
           		<ul>
           		<c:forEach items="${ errorsList }" var="errorMessage">
                   	<li>${ errorMessage }</li>
                   </c:forEach>
           		</ul>
                   ${ errorMessage }
               </div>
           	</c:if>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;">
							<input type="checkbox" id="selectall" />
							<span style="vertical-align: top;"> - 
							<a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
								<i class="fa fa-trash-o fa-lg"></i>
							</a>
							</span>
						</th>
						<th><spring:message code="label.dashboard.table.header.computerName"/></th>
						<th><spring:message code="label.dashboard.table.header.introducedDate"/></th>
						<th><spring:message code="label.dashboard.table.header.discontinuedDate"/></th>
						<th><spring:message code="label.dashboard.table.header.companyName"/></th>
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<%-- Computer table --%>
					<c:forEach items="${ computersList }" var="computer">
					<tr>
						<td class="editMode"><input type="checkbox" name="cb"
							class="cb" value="${ computer.id }"></td>
						<td>
							<c:url value="/editComputer" var="editComputerLink" scope="request">
								<c:param name="id" value="${ computer.id }"/>
							</c:url>
							<a href="${ editComputerLink }" onclick="">${ computer.name }</a>
						</td>
						<td>${ computer.introductionDate }</td>
						<td>${ computer.discontinueDate }</td>
						<td>${ computer.companyName }</td>

					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<!-- Pagination -->
			<ul class="pagination">
				<%-- Display first page link --%>
				<c:if test = "${page.index > 0}">
				<li>
					<c:url value="" var="dashboardLink" scope="request">
						<c:param name="page" value="0"/>
						<c:param name="search" value="${ search }"/>
					</c:url>
					<a href="${ dashboardLink }" aria-label="First">
						<span aria-hidden="true">&laquo;&laquo;</span>
					</a>
				</li>
				<%-- Display previous page link --%>
				<li>
					<c:url value="" var="dashboardLink" scope="request">
						<c:param name="page" value="${ page.index - 1 }"/>
						<c:param name="search" value="${ search }"/>
					</c:url>
					<a href="${ dashboardLink }" aria-label="Previous">
						<span aria-hidden="true">&laquo;</span>
					</a>
				</li>
				</c:if>
				<%-- Pages numbers menu --%>
				<c:forEach var="pageIndex" begin="${ page.index - 3 >= 0 ? page.index - 3: 0 }" end="${ page.index + 3 }">
					<c:if test = "${pageIndex >= 0 and pageIndex <= page.maxIndex }">
					<li class="${ pageIndex == page.index ? 'active' : '' }">
						<c:url value="" var="dashboardLink" scope="request">
							<c:param name="page" value="${ pageIndex }"/>
							<c:param name="search" value="${ search }"/>
						</c:url>
						<a href="${ dashboardLink }">${ pageIndex + 1 }</a>
					</li>
					</c:if>
				</c:forEach>
				<%-- Display next page link --%>
				<c:if test = "${page.index < page.maxIndex}">
				<li>
					<c:url value="" var="dashboardLink" scope="request">
						<c:param name="page" value="${ page.index + 1 }"/>
						<c:param name="search" value="${ search }"/>
					</c:url>
					<a href="${ dashboardLink }" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
				<%-- Display last page link --%>
				<li>
					<c:url value="" var="dashboardLink" scope="request">
						<c:param name="page" value="${ page.maxIndex }"/>
						<c:param name="search" value="${ search }"/>
					</c:url>
					<a href="${ dashboardLink }" aria-label="Last">
						<span aria-hidden="true">&raquo;&raquo;</span>
					</a>
				</li>
				</c:if>
			</ul>

			<!-- Page size -->
			<div class="d-flex pagination pull-right" role="group">
				<c:forEach var="pageSize" items="10,50,100">
				<c:url value="" var="dashboardLink" scope="request">
					<c:param name="pageSize" value="${ pageSize }"/>
					<c:param name="page" value="${ page.index }"/>
					<c:param name="search" value="${ search }"/>
				</c:url>
				<a href="${ dashboardLink }" class="btn btn-default ${ pageSize == page.size ? 'active' : '' }">
					${ pageSize }
				</a>
				</c:forEach>
			</div>
		</div>

	</footer>
	
	<%-- Script --%>
	<jsp:include page="component/script.jsp" />
	
</body>
</html>