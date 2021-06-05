<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
				<h1>Add Specialist Details</h1>
				<div class="container result-container border py-4">
					<c:choose>
						<c:when test="${not empty specialistDetail}">

							<form:form name="form" action="/portal/addSpecialist"
								method="post" modelAttribute="specialistDetail">


								<%-- <div class="form-group">
											<form:label path="specialistId">Specialist Id:</form:label>
											<form:input path="specialistId" id="specialistId"
												name="specialistId" class="form-control" required="required" />
										</div> --%>

								<div class="form-group">
									<form:label path="name">Specialist Name:</form:label>
									<form:input path="name" id="name" name="name"
										class="form-control" required="required" minlength="1" />
								</div>

								<div class="form-group">
									<form:label path="areaOfExpertise">Add Area Of Expertise:</form:label>
									<form:select path="areaOfExpertise" id="areaOfExpertise"
										name="areaOfExpertise" class="form-control"
										items="${ailmentList}" required="required" />
								</div>

								<div class="form-group">
									<form:label path="experienceInYears">Experience In Years:</form:label>
									<form:input path="experienceInYears" type="number"
										id="experienceInYears" name="experienceInYears"
										class="form-control" required="required" min="0" />
								</div>

								<div class="form-group">
									<form:label path="contactNumber">Contact Number</form:label>
									<form:input path="contactNumber" id="contactNumber"
										name="contactNumber" class="form-control" required="required"
										pattern="[1-9]{1}[0-9]{9}" maxlength="10" />
								</div>


								<div class="form-group">
									<input type="submit" value="Submit" id="submit" name="submit"
										class="btn btn-success"> <input type="reset"
										value="Clear" id="Clear" name="Clear" class="btn btn-primary">
								</div>

							</form:form>
						</c:when>
					</c:choose>
				</div>
			</div>
		</div>
		<%@ include file="fragments/footer.jsp"%>
	</div>
</body>
</html>