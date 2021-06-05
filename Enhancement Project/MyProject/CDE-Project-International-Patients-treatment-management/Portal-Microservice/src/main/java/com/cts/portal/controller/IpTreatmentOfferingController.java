package com.cts.portal.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cts.portal.exception.IPTreatmentPackageNotFoundException;
import com.cts.portal.exception.PackageDetailNotFoundException;
import com.cts.portal.exception.SpecialistNotFoundException;
import com.cts.portal.feign.IPTreatmentOfferingClient;
import com.cts.portal.model.AilmentCategory;
import com.cts.portal.model.FormInputsGetByExpertise;
import com.cts.portal.model.FormInputsGetByPackageName;
import com.cts.portal.model.IPTreatmentPackage;
import com.cts.portal.model.SpecialistDetail;

@Controller
@RequestMapping("/portal")
public class IpTreatmentOfferingController {

	@Autowired
	private IPTreatmentOfferingClient client;

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/specialists")
	public ModelAndView showSpecialistPage(HttpServletRequest request, @ModelAttribute("error") String error,
			@ModelAttribute("status") String status) throws Exception {

		if ((String) request.getSession().getAttribute("Authorization") == null) {

			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		/*
		 * get the list of specialists using feign client of IPOfferingMicroservice
		 */
		System.out.println("Inside /specialists");
		List<SpecialistDetail> specialists = client
				.getAllSpecialist((String) request.getSession().getAttribute("Authorization"));
		ModelAndView model = new ModelAndView("user-view-list-of-specialist-page");
		model.addObject("specialists", specialists);
		model.addObject("error", error);
		model.addObject("status", status);
		if(specialists.isEmpty()) {
			ArrayList<String> list = new ArrayList<String>();
			list.add("No data to filter/ reset");
			model.addObject("ailmentList", list);
		}
		else {
			model.addObject("ailmentList", specialists.stream().map(x->x.getAreaOfExpertise()).collect(Collectors.toSet()));
		}
		model.addObject("formInputsGetByExpertise", new FormInputsGetByExpertise());
		return model;
	}

	/**
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/ipTreatmentPackages")
	public ModelAndView showIPTreatmentPackages(Model model, HttpServletRequest request,
			@ModelAttribute("error") String error, @ModelAttribute("status") String status) throws Exception {
		System.out.println("Inside IP Treatment Packages");
		if ((String) request.getSession().getAttribute("Authorization") == null) {

			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		List<IPTreatmentPackage> packageDetails = client
				.getAllIPTreatmentPackage((String) request.getSession().getAttribute("Authorization"));
		ModelAndView modelAndView = new ModelAndView("user-view-package-detail-page");
		modelAndView.addObject("error", error);
		modelAndView.addObject("status", status);
		modelAndView.addObject("ipTreatmentPackagekageName", packageDetails);
		return modelAndView;
	}

	/**
	 * @param formInputsGetByPackageName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/ipTreatmentPackageByName")
	public ModelAndView showIPTreatmentPackageByName2(
			@ModelAttribute("formInputsGetByPackageName") FormInputsGetByPackageName formInputsGetByPackageName,
			HttpServletRequest request) throws Exception {

		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		/*
		 * if token is set, then allow access to view
		 */
		ModelAndView model = new ModelAndView("user-package-detail-by-name-page");
		if (formInputsGetByPackageName != null && formInputsGetByPackageName.getAilment() != null
				&& formInputsGetByPackageName.getPackageName() != null) {
			try {
				/*
				 * get the package details by Name using feign client of IPOfferingMicroservice
				 */
				IPTreatmentPackage ipTreatmentPackagekageName = client.getIPTreatmentPackageByName(
						formInputsGetByPackageName.getAilment(), formInputsGetByPackageName.getPackageName(),
						(String) request.getSession().getAttribute("Authorization"));
				model.addObject("ipTreatmentPackagekageName", ipTreatmentPackagekageName);
			} catch (IPTreatmentPackageNotFoundException e) {
				model.addObject("error", e.getMessage());
			}
		}
		return model;
	}

	@GetMapping("/specialistsByExpertise")
	public ModelAndView showSpecialistsByExpertise(
			@ModelAttribute("formInputsGetByExpertise") FormInputsGetByExpertise formInputsGetByExpertise,
			HttpServletRequest request) throws Exception {
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		/*
		 * if token is set, then allow access to view
		 */
		ModelAndView model = new ModelAndView("user-view-list-of-specialist-page");
		if (formInputsGetByExpertise != null && formInputsGetByExpertise.getAreaOfExpertise() != null) {
			try {
				/*
				 * get the specialist details by AreaOfExpertise using feign client of
				 * IPOfferingMicroservice
				 */
				List<SpecialistDetail> specialists = client.getAllSpecialistsByExpertise(
						formInputsGetByExpertise.getAreaOfExpertise(),
						(String) request.getSession().getAttribute("Authorization"));
				model.addObject("specialists", specialists);
//				model.addObject("ailmentList", specialists.stream().map(x->x.getAreaOfExpertise()).collect(Collectors.toList()));
			} catch (SpecialistNotFoundException e) {
				model.addObject("error", e.getMessage());
			}
		}
		return model;
	}

	@GetMapping("/deleteSpecialist")
	public ModelAndView deleteSpecialistById(@RequestParam("specialist_id") Integer specialistId,
			HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		/*
		 * if token is set, then allow access to view
		 */
		System.out.println(specialistId);
		ModelAndView model = new ModelAndView("redirect:/portal/specialists");
		if (specialistId != null) {
			try {
				/*
				 * delete the specialist details by specialistId using feign client of
				 * IPOfferingMicroservice
				 */
				client.deleteBySpecialistId(specialistId,
						((String) request.getSession().getAttribute("Authorization")));
				redirectAttributes.addFlashAttribute("status", "deleted");
			} catch (SpecialistNotFoundException ex) {
				redirectAttributes.addFlashAttribute("status", "Not deleted");
				redirectAttributes.addFlashAttribute("error", ex.getMessage());
			}
		}
		return model;
	}

	@GetMapping("/updatePackage")
	public ModelAndView showIPTreatmentPackageForm(@RequestParam("ailment") AilmentCategory ailmentCategory,
			@RequestParam("package_name") String packageName, HttpServletRequest request) throws Exception {
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		/*
		 * if token is set, then allow access to view
		 */
		ModelAndView model = new ModelAndView("admin-update-treatment-package-form");
		if (ailmentCategory != null && packageName != null) {
			/*
			 * get the ipTreatmentPackage based on ailment and packageName by using
			 * IPOfferingMicroservice and show update form page
			 */
			IPTreatmentPackage ipTreatmentPackage = client.getIPTreatmentPackageByName(ailmentCategory, packageName,
					(String) request.getSession().getAttribute("Authorization"));
			model.addObject("ipTreatmentPackage", ipTreatmentPackage);
		}
		return model;
	}

	@PostMapping("/updatePackage")
	public ModelAndView updateByTreatmentPackageId(
			@ModelAttribute("ipTreatmentPackage") IPTreatmentPackage ipTreatmentPackage, HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws Exception {
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		/*
		 * if token is set, then allow access to view
		 */
		ModelAndView model = new ModelAndView("redirect:/portal/ipTreatmentPackages");
		if (ipTreatmentPackage != null && ipTreatmentPackage.getAilmentCategory() != null
				&& ipTreatmentPackage.getTreatmentPackageId() > 0 && ipTreatmentPackage.getPackageDetail() != null
				&& ipTreatmentPackage.getPackageDetail().getPid() > 0
				&& ipTreatmentPackage.getPackageDetail().getTestDetails() != null
				&& !ipTreatmentPackage.getPackageDetail().getTestDetails().isEmpty()
				&& ipTreatmentPackage.getPackageDetail().getCost() > 0
				&& ipTreatmentPackage.getPackageDetail().getTreatmentDuration() > 0
				&& ipTreatmentPackage.getPackageDetail().getTreatmentPackageName() != null
				&& ipTreatmentPackage.getPackageDetail().getTreatmentPackageName().length() > 0) {
			try {
				/*
				 * update the IPTTreatmentPackage by package Id using feign client of
				 * IPOfferingMicroservice
				 */
				client.updateTreatmentByPackageId(ipTreatmentPackage.getPackageDetail().getPid(), ipTreatmentPackage,
						((String) request.getSession().getAttribute("Authorization")));
				redirectAttributes.addFlashAttribute("status", "updated");
			} catch (PackageDetailNotFoundException ex) {
				redirectAttributes.addFlashAttribute("status", "Not updated");
				redirectAttributes.addFlashAttribute("error", ex.getMessage());
			}
		}
		return model;
	}

	@GetMapping("/addSpecialist")
	public ModelAndView showSpecialistDetailForm(@ModelAttribute("specialistDetail") SpecialistDetail specialistDetail,
			HttpServletRequest request) throws Exception {

		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		/*
		 * if token is set, then allow access to view
		 */
		System.out.println("Inside /specialistsForm");

		ModelAndView model = new ModelAndView("admin-add-specialist-detail-form");

		return model;
	}

	@PostMapping("/addSpecialist")
	public ModelAndView AddNewSpl(@ModelAttribute("specialistDetail") SpecialistDetail specialistDetail,
			HttpServletRequest request, final RedirectAttributes redirectAttributes) throws Exception {
		System.out.println("Inside IP Treatment Packages");
		if ((String) request.getSession().getAttribute("Authorization") == null) {

			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		/*
		 * if token is set, then allow access to view
		 */
		ModelAndView model = new ModelAndView("redirect:/portal/specialists");
		if (specialistDetail != null && specialistDetail.getAreaOfExpertise() != null
				&& specialistDetail.getName() != null && !specialistDetail.getName().isEmpty()
				&& specialistDetail.getContactNumber() > 999999999 && specialistDetail.getExperienceInYears() >= 0) {
			/*
			 * create and add the specialist details using feign client of
			 * IPOfferingMicroservice
			 */
			client.createSpecialistDetail(specialistDetail,
					(String) request.getSession().getAttribute("Authorization"));
			redirectAttributes.addFlashAttribute("status", "added");
		} else {
			redirectAttributes.addFlashAttribute("status", "Not added");
			redirectAttributes.addFlashAttribute("error", "Invalid Input");
		}
		return model;

	}

	@ModelAttribute("ailmentList")
	public Set<String> populateAilmentEnumList() {
		return EnumSet.allOf(com.cts.portal.model.AilmentCategory.class).stream().map(a -> a.name())
				.collect(Collectors.toSet());

	}

	@ModelAttribute("packageList")
	public List<String> populatePackageList() {
		return Arrays.asList("Package 1", "Package 2");

	}

	@ModelAttribute("optTestDetailsList")
	public List<String> populateOPTTestDetailsList() {
		return Arrays.asList("OPT1", "OPT2", "OPT3", "OPT4");
	}

	@ModelAttribute("uptTestDetailsList")
	public List<String> populateUPTTestDetailsList() {
		return Arrays.asList("UPT1", "UPT2", "UPT3", "UPT4");
	}
}
