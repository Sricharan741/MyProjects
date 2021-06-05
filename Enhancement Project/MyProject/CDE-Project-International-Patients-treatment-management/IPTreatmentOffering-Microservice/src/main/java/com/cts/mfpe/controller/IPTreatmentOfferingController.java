package com.cts.mfpe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cts.mfpe.exception.AuthorizationException;
import com.cts.mfpe.exception.IPTreatmentPackageNotFoundException;
import com.cts.mfpe.exception.PackageDetailNotFoundException;
import com.cts.mfpe.exception.SpecialistNotFoundException;
import com.cts.mfpe.feign.AuthorisingClient;
import com.cts.mfpe.model.AilmentCategory;
import com.cts.mfpe.model.IPTreatmentPackage;
import com.cts.mfpe.model.SpecialistDetail;
import com.cts.mfpe.service.IPTreatmentOfferingService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class IPTreatmentOfferingController {

	@Autowired
	private IPTreatmentOfferingService ipOfferingService;

	@Autowired
	private AuthorisingClient authorisingClient;

	/**
	 * @param requestTokenHeader
	 * @return
	 * @throws AuthorizationException
	 * @throws Exception
	 */
	@GetMapping("/ipTreatmentPackages")
	@ApiOperation(notes = "Returns the list of IP Treatment Packages", value = "Find IP Treatment Package")
	public List<IPTreatmentPackage> getAllIPTreatmentPackage(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException {
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return ipOfferingService.findAllIPTreatmentPackages();
		} else {
			throw new AuthorizationException("Not allowed");
		}

	}

	/**
	 * @param ailment
	 * @param packageName
	 * @param requestTokenHeader
	 * @return
	 * @throws AuthorizationException
	 * @throws IPTreatmentPackageNotFoundException
	 * @throws Exception
	 */
	@GetMapping("/ipTreatmentPackageByName/{ailment}/{packageName}")
	@ApiOperation(notes = "Returns the IP Treatment Package based on package name", value = "Find IP Treatment Package by name")
	public IPTreatmentPackage getIPTreatmentPackageByName(
			@ApiParam(name = "ailment", value = "ailment of the package") @PathVariable AilmentCategory ailment,
			@ApiParam(name = "packageName", value = "name of the package") @PathVariable String packageName,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException, IPTreatmentPackageNotFoundException {

		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return ipOfferingService.findIPTreatmentPackageByName(ailment, packageName);
		} else {
			throw new AuthorizationException("Not allowed");
		}
	}

	@GetMapping("/specialistsByExpertise/{areaOfExpertise}")
	@ApiOperation(notes = "Returns the list of specialists based on area of expertise", value = "Find specialists by expertise")
	public List<SpecialistDetail> getAllSpecialistsByExpertise(
			@ApiParam(name = "areaOfExpertise", value = "area of expertise") @PathVariable AilmentCategory areaOfExpertise,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException, SpecialistNotFoundException {
		System.out.println("Inside ================" + requestTokenHeader);
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return ipOfferingService.findAllSpecialistsByExpertise(areaOfExpertise);
		} else {
			throw new AuthorizationException("Not allowed");
		}
	}

	@DeleteMapping("/deleteSpecialist/{specialist_id}")
	@ApiOperation(notes = "Deletes a specialist based on specialist_id", value = "Delete specialist by specialist_id")
	public void deleteBySpecialistId(
			@ApiParam(name = "specialistId", value = "specialist ID") @PathVariable("specialist_id") Integer specialistId,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException, SpecialistNotFoundException {
		System.out.println("Inside ================" + requestTokenHeader);
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			ipOfferingService.deleteBySpecialistId(specialistId);
		} else {
			throw new AuthorizationException("Not allowed");
		}
	}

	@PutMapping("/updatePackage/{package_id}")
	@ApiOperation(notes = "Updates a IPTTreatment package by using package_id", value = "Update IPTTreatment package by package_id")
	public void updateTreatmentByPackageId(
			@ApiParam(name = "packageId", value = "package Id") @PathVariable("package_id") Integer packageId,
			@ApiParam(name = "iPTreatmentPackage", value = "iP Treatment Package") @RequestBody IPTreatmentPackage iPTreatmentPackage,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException, PackageDetailNotFoundException {
		System.out.println("Inside ================" + requestTokenHeader);
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			ipOfferingService.updateTreatmentByPackageId(packageId, iPTreatmentPackage);
		} else {
			throw new AuthorizationException("Not allowed");
		}
	}
	
	@PostMapping("/addSpecialist")
	@ApiOperation(notes = "Creates a specialist detail", value = "Create a specialist detail")
	public SpecialistDetail createSpecialistDetail(
			@ApiParam(name = "specialistDetail", value = "specialist detail") @RequestBody SpecialistDetail specialistDetail,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException {
		System.out.println("Inside ================" + requestTokenHeader);
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return ipOfferingService.createSpecialist(specialistDetail);
		} else {
			throw new AuthorizationException("Not allowed");
		}
	}

	/**
	 * @param requestTokenHeader
	 * @return
	 * @throws AuthorizationException
	 * @throws Exception
	 */
	@GetMapping("/specialists")
	@ApiOperation(notes = "Returns the list of specialists along with their experience and contact details", value = "Find specialists")
	public List<SpecialistDetail> getAllSpecialist(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException {
		System.out.println("Inside ================" + requestTokenHeader);
		if (authorisingClient.authorizeTheRequest(requestTokenHeader)) {
			return ipOfferingService.findAllSpecialists();
		} else {
			throw new AuthorizationException("Not allowed");
		}
	}

	@GetMapping("/health-check")
	public ResponseEntity<String> healthCheck() {
		return new ResponseEntity<>("Ok", HttpStatus.OK);
	}
}
