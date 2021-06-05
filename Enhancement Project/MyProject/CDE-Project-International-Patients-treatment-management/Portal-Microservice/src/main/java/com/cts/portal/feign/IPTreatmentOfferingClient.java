package com.cts.portal.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.portal.exception.AuthorizationException;
import com.cts.portal.exception.IPTreatmentPackageNotFoundException;
import com.cts.portal.exception.PackageDetailNotFoundException;
import com.cts.portal.exception.SpecialistNotFoundException;
import com.cts.portal.model.AilmentCategory;
import com.cts.portal.model.IPTreatmentPackage;
import com.cts.portal.model.SpecialistDetail;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@FeignClient(name = "IPTreatmentOffering-service", url = "${ipoffering.URL}")
public interface IPTreatmentOfferingClient {

	@GetMapping("/ipTreatmentPackages")
	public List<IPTreatmentPackage> getAllIPTreatmentPackage(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException;

	@GetMapping("/ipTreatmentPackageByName/{ailment}/{packageName}")
	public IPTreatmentPackage getIPTreatmentPackageByName(
			@ApiParam(name = "ailment", value = "ailment of the package") @PathVariable AilmentCategory ailment,
			@ApiParam(name = "packageName", value = "name of the package") @PathVariable String packageName,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException, IPTreatmentPackageNotFoundException;

	@GetMapping("/specialistsByExpertise/{areaOfExpertise}")
	public List<SpecialistDetail> getAllSpecialistsByExpertise(
			@ApiParam(name = "areaOfExpertise", value = "area of expertise") @PathVariable AilmentCategory areaOfExpertise,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException, SpecialistNotFoundException;

	@GetMapping("/specialists")
	public List<SpecialistDetail> getAllSpecialist(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException;

	@PutMapping("/updatePackage/{package_id}")
	@ApiOperation(notes = "Updates a IPTTreatment package by using package_id", value = "Update IPTTreatment package by package_id")
	public void updateTreatmentByPackageId(
			@ApiParam(name = "packageId", value = "package Id") @PathVariable("package_id") Integer packageId,
			@ApiParam(name = "iPTreatmentPackage", value = "iP Treatment Package") @RequestBody IPTreatmentPackage iPTreatmentPackage,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException, PackageDetailNotFoundException;

	@PostMapping("/addSpecialist")
	@ApiOperation(notes = "Creates a specialist detail", value = "Create a specialist detail")
	public SpecialistDetail createSpecialistDetail(
			@ApiParam(name = "specialistDetail", value = "specialist detail") @RequestBody SpecialistDetail specialistDetail,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException;

	@DeleteMapping("/deleteSpecialist/{specialist_id}")
	public void deleteBySpecialistId(
			@ApiParam(name = "specialistId", value = "specialist ID") @PathVariable("specialist_id") Integer specialistId,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException, SpecialistNotFoundException;
}