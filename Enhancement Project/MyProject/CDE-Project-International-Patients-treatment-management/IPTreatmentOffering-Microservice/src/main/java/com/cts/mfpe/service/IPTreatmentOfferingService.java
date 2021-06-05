package com.cts.mfpe.service;

import java.util.List;

import com.cts.mfpe.exception.IPTreatmentPackageNotFoundException;
import com.cts.mfpe.exception.PackageDetailNotFoundException;
import com.cts.mfpe.exception.SpecialistNotFoundException;
import com.cts.mfpe.model.AilmentCategory;
import com.cts.mfpe.model.IPTreatmentPackage;
import com.cts.mfpe.model.SpecialistDetail;

public interface IPTreatmentOfferingService {

	List<IPTreatmentPackage> findAllIPTreatmentPackages();

	IPTreatmentPackage findIPTreatmentPackageByName(AilmentCategory ailment, String packageName)
			throws IPTreatmentPackageNotFoundException;

	List<SpecialistDetail> findAllSpecialists();

	List<SpecialistDetail> findAllSpecialistsByExpertise(AilmentCategory areaOfExpertise)
			throws SpecialistNotFoundException;

	public void deleteBySpecialistId(Integer specialistId) throws SpecialistNotFoundException;

	public void updateTreatmentByPackageId(Integer packageId, IPTreatmentPackage iPTreatmentPackage)
			throws PackageDetailNotFoundException;
	
	public SpecialistDetail createSpecialist(SpecialistDetail specialistDetail);
}
