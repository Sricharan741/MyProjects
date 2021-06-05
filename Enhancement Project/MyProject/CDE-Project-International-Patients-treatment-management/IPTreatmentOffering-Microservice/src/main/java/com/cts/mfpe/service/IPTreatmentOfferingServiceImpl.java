package com.cts.mfpe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.mfpe.exception.IPTreatmentPackageNotFoundException;
import com.cts.mfpe.exception.PackageDetailNotFoundException;
import com.cts.mfpe.exception.SpecialistNotFoundException;
import com.cts.mfpe.model.AilmentCategory;
import com.cts.mfpe.model.IPTreatmentPackage;
import com.cts.mfpe.model.PackageDetail;
import com.cts.mfpe.model.SpecialistDetail;
import com.cts.mfpe.repository.IPTreatmentPackageRepository;
import com.cts.mfpe.repository.PackageDetailRepository;
import com.cts.mfpe.repository.SpecialistDetailRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IPTreatmentOfferingServiceImpl implements IPTreatmentOfferingService {

	@Autowired
	IPTreatmentPackageRepository treatmentPackRepository;

	@Autowired
	SpecialistDetailRepository specialistRepository;

	@Autowired
	PackageDetailRepository packageDetailRepository;

	@Override
	public List<IPTreatmentPackage> findAllIPTreatmentPackages() {

		List<IPTreatmentPackage> treatmentPackages = treatmentPackRepository.findAll();
		log.info("[IPTreatmentPackage details:] " + treatmentPackages);
		return treatmentPackages;
	}

	@Override
	public IPTreatmentPackage findIPTreatmentPackageByName(AilmentCategory ailment, String packageName)
			throws IPTreatmentPackageNotFoundException {

		IPTreatmentPackage treatmentPackage = treatmentPackRepository.findByName(ailment, packageName)
				.orElseThrow(() -> new IPTreatmentPackageNotFoundException("IP Treatment Package not found"));

		log.info("[IPTreatmentPackage (" + packageName + ") detail:] " + treatmentPackage);
		return treatmentPackage;
	}

	@Override
	public List<SpecialistDetail> findAllSpecialists() {

		List<SpecialistDetail> specialists = specialistRepository.findAll();
		log.info("[Specialist details:] " + specialists);
		return specialists;
	}

	@Override
	public List<SpecialistDetail> findAllSpecialistsByExpertise(AilmentCategory areaOfExpertise)
			throws SpecialistNotFoundException {

		List<SpecialistDetail> specialists = specialistRepository.findByAreaOfExpertise(areaOfExpertise).get();
		if (specialists.isEmpty()) {
			throw new SpecialistNotFoundException("No specialist available with areaOfExpertise : " + areaOfExpertise);
		}
		log.info("[Specialist details:] " + specialists);
		return specialists;
	}

	@Override
	public void deleteBySpecialistId(Integer specialistId) throws SpecialistNotFoundException {
		SpecialistDetail specialist = specialistRepository.findById(specialistId).get();
		if (specialist == null) {
			throw new SpecialistNotFoundException("No specialist available with specialistId : " + specialistId);
		}
		log.info("[Specialist details:] " + specialist);
		specialistRepository.deleteById(specialistId);

	}

	@Override
	public void updateTreatmentByPackageId(Integer packageId, IPTreatmentPackage iPTreatmentPackage)
			throws PackageDetailNotFoundException {
		PackageDetail packageDetail = packageDetailRepository.findById(packageId).get();
		if (packageDetail == null) {
			throw new PackageDetailNotFoundException("IP Treatment Package not found");
		}
		packageDetailRepository.save(iPTreatmentPackage.getPackageDetail());
		log.info("[Package details:] " + iPTreatmentPackage.getPackageDetail());

	}

	@Override
	public SpecialistDetail createSpecialist(SpecialistDetail specialistDetail) {
		specialistRepository.save(specialistDetail);
		log.info("[Specialist details:] " + specialistDetail);
		return specialistDetail;
	}

}
