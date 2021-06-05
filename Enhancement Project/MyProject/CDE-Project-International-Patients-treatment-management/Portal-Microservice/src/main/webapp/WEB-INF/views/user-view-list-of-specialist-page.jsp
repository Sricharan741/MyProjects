<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>International Patients Management System</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Pinyon+Script&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="/css/style.css">
<link rel="stylesheet" href="/css/style-table.css">
<link rel="stylesheet" href="/css/style-admin.css">
</head>
<body>
	<div class="main-container-register">
		<%@ include file="fragments/header.jsp"%>
		<div class="section grid">
			<%@ include file="admin-fragments/admin-sidebar.jsp"%>
			<div class="content list-container">
				<c:if test="${not empty error}">
					<div class="alert alert-danger alert-dismissible" role="alert">
						<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
    					<strong>Failed!</strong> ${error}
					</div>
				</c:if>
				<c:if test="${status.equals('added')}">
					<div class="alert alert-success alert-dismissible" role="alert">
						<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						<strong>Success!</strong> Specialist added.
					</div>
				</c:if>
				<c:if test="${status.equals('deleted')}">
					<div class="alert alert-success alert-dismissible" role="alert">
						<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						<strong>Success!</strong> Specialist deleted.
					</div>
				</c:if>
				<h1>Our Specialists</h1>
				<div class="container">
					<div class="container mb-2 px-5">
						<div class="text-right p-2">
								<a type="button" class="btn btn-success btn-md px-5 py-2" href="/portal/addSpecialist">Add</a>
						</div>
					</div>
					<table class="table table-striped">
						<thead>
							<tr>
								<th>#</th>
								<th>Name</th>
								<th>Area of Expertise</th>
								<th>experience (year)</th>
								<th>Contact Number</th>
								<th>Delete</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${specialists}" var="specialist"
								varStatus="loop">
								<tr>
									<td>${loop.index+1}</td>
									<td>${specialist.getName()}</td>
									<td>${specialist.getAreaOfExpertise()}</td>
									<td>${specialist.getExperienceInYears()}</td>
									<td>${specialist.getContactNumber()}</td>
									<td><a type="button" class="btn btn-sm btn-danger px-4 py-1" href="/portal/deleteSpecialist?specialist_id=${specialist.getSpecialistId()}">Delete</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="container mt-2 px-5">
						<form:form class="form" method="GET"
							modelAttribute="formInputsGetByExpertise">
							<div class="form-group col-md-4 p-2 mb-0">
								<form:label path="areaOfExpertise">Select Area Of Expertise:</form:label>
								<form:select path="areaOfExpertise" class="form-control form-control-sm" id="areaOfExpertise"
									items="${ailmentList}" required="required" />
								<form:button class="my-2 btn btn-md" formaction="specialistsByExpertise">Filter</form:button>
								<form:button class="my-2 btn btn-md" formaction="specialists">Reset</form:button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
		<%@ include file="fragments/footer.jsp"%>
	</div>
</body>
</html>