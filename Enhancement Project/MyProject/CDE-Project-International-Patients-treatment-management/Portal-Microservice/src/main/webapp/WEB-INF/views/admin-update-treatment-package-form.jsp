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
				<h1>Update package details</h1>
				<div class="container result-container border py-5">
					<c:choose>
						<c:when test="${not empty ipTreatmentPackage}">

							<form:form modelAttribute="ipTreatmentPackage"
								action="/portal/updatePackage" method="post">

								<div class="form-group">
									<label>IPTTreatment Package Id :</label>
									<form:input path="treatmentPackageId" class="form-control"
										type="number"
										value="${ipTreatmentPackage.getTreatmentPackageId()}"
										readonly="true" />
								</div>

								<div class="form-group">
									<label>Ailment :</label>
									<form:input path="ailmentCategory" class="form-control"
										type="text" value="${ipTreatmentPackage.getAilmentCategory()}"
										readonly="true" />
								</div>

								<div class="form-group">
									<label>Package Id :</label>
									<form:input path="packageDetail.pid" class="form-control"
										type="text"
										value="${ipTreatmentPackage.getPackageDetail().getPid()}"
										readonly="true" />
								</div>

								<div class="form-group">
									<label>Package Name :</label>
									<form:input path="packageDetail.treatmentPackageName"
										class="form-control"
										value="${ipTreatmentPackage.getPackageDetail().getTreatmentPackageName()}"
										readonly="true" />
								</div>

								<div class="form-group">
									<label>Test Details :</label>
									<c:if
										test="${ipTreatmentPackage.getAilmentCategory().name() == 'ORTHOPAIDICS' }">
										<form:select path="packageDetail.testDetails"
											class="form-control" items="${optTestDetailsList}"
											required="true" />
									</c:if>
									<c:if
										test="${ipTreatmentPackage.getAilmentCategory().name() == 'UROLOGY'}">
										<form:select path="packageDetail.testDetails"
											class="form-control" items="${uptTestDetailsList}"
											required="true" />
									</c:if>
								</div>


								<div class="form-group">
									<label>Cost :</label>
									<form:input path="packageDetail.cost" class="form-control"
										type="number"
										value="${ipTreatmentPackage.getPackageDetail().getCost()}"
										required="true" min="1" />
								</div>


								<div class="form-group">
									<label>Duration :</label>
									<form:input path="packageDetail.treatmentDuration"
										class="form-control" type="number"
										value="${ipTreatmentPackage.getPackageDetail().getTreatmentDuration()}"
										required="true" min="0" />
								</div>

								<div class="form-group">
									<input type="submit" class="btn btn-success" value="Update" />
									<input type="reset" value="Reset" id="Reset" name="Reset"
										class="btn btn-primary">
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