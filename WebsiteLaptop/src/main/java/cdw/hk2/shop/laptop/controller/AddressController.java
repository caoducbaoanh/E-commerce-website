package cdw.hk2.shop.laptop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import  cdw.hk2.shop.laptop.dto.*;

import cdw.hk2.shop.laptop.services.DistrictServices;
import cdw.hk2.shop.laptop.services.ProvinceServices;
import cdw.hk2.shop.laptop.services.WardDtoServices;

@Controller
public class AddressController {
	@Autowired
	private ProvinceServices prServices;
	@Autowired
	private DistrictServices dServices;
	@Autowired
	private WardDtoServices wardServies;

	@GetMapping("/chondiachi")
	public String selectAddress(Model model, @RequestParam(value = "Id") String id,
			@RequestParam(value = "IdAddress") String idAddress) {

		if (idAddress.equals("country")) {
			ProvinceDto getAddress = prServices.FindByIdProvince(id);
			List<DistrictDto> list = getAddress.getDistrict();
			model.addAttribute("address", list);
			model.addAttribute("check", "D");

		}

		if (idAddress.equals("district")) {
			DistrictDto getAddress = dServices.findDistrictById(id);
			List<WardDto> list = getAddress.getWard();
			model.addAttribute("address", list);
			model.addAttribute("check", "W");
		}
		if (idAddress.equals("ward")) {
			System.out.println("Village" + id);
			WardDto getAddress = wardServies.findWardByID(id);
			List<VillageDto> list = getAddress.getVillages();
			model.addAttribute("address", list);
			model.addAttribute("check", "V");
		}
		return "fragments/option";
	}

}
