package cdw.hk2.shop.laptop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import  cdw.hk2.shop.laptop.dto.*;

import cdw.hk2.shop.laptop.model.Information;
import cdw.hk2.shop.laptop.model.Notify;
import cdw.hk2.shop.laptop.model.User;
import cdw.hk2.shop.laptop.security.AuthenticationFacade;
import cdw.hk2.shop.laptop.services.DistrictServices;
import cdw.hk2.shop.laptop.services.InformationServices;
import cdw.hk2.shop.laptop.services.NotifyServices;
import cdw.hk2.shop.laptop.services.ProvinceServices;
import cdw.hk2.shop.laptop.services.ReviewServices;
import cdw.hk2.shop.laptop.services.UserServiceImpl;
import cdw.hk2.shop.laptop.services.VillageServices;
import cdw.hk2.shop.laptop.services.WardDtoServices;
import cdw.hk2.shop.laptop.utils.TimeUtlis;
import cdw.hk2.shop.laptop.utils.UrlImageUtils;

@Controller
@RequestMapping("/tai-khoan")
public class ProfileController {
	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private ProvinceServices providedS;
	@Autowired
	private VillageServices villageDto;
	@Autowired
	private WardDtoServices vWardDtoServices;
	@Autowired
	private DistrictServices districtS;
	@Autowired
	private BCryptPasswordEncoder pass;
	@Autowired
	private AuthenticationFacade authentication;
	@Autowired
	private InformationServices inforS;
	@Autowired
	private ReviewServices reviewServices;
	@Autowired
	private NotifyServices notifyServices;
	@Autowired
	private TimeUtlis time;

	@RequestMapping(value = "/thong-tin-tai-khoan", method = RequestMethod.GET)
	public String profileView(Model model) {

		User user = userServiceImpl.findUserById(authentication.getIdUser());
		Information information = user.getInformation();
		model.addAttribute("informations",information);
		List<ProvinceDto> listProvince = providedS.findAllProvince();
		if(information.getDistrict()!=null&& information.getProvince()!=null) {
			List<DistrictDto> district= districtS.findAllDistrictByIDP(information.getProvince());
			model.addAttribute("listdistrict", district);
			List<WardDto> wardDtos =vWardDtoServices.findAllWardByIDD(information.getDistrict());
			List<VillageDto> liVillageDtos = villageDto.findAllVillageByID(information.getWard());

			model.addAttribute("listvillage", liVillageDtos);
			model.addAttribute("listward", wardDtos);
		}
		model.addAttribute("listProvince", listProvince);
	

		model.addAttribute("users", user);

		return "/account/index";

	}

	@RequestMapping(value  = "/thong-tin-tai-khoan/cap-nhat", method = RequestMethod.POST)
	public ResponseEntity<Boolean> updateProfile(Model model, @ModelAttribute(name = "informations") Information infor,@RequestParam( name = "firstname") String name) {

		try {
			User user = userServiceImpl.findUserById(authentication.getIdUser());
			user.setName(name);
			userServiceImpl.saveUser(user);
			inforS.updateInfor(infor);
			return new ResponseEntity<Boolean>(true,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
		}
		
	}
	@PostMapping("/thong-tin-tai-khoan/thay-doi/mat-khau")
	public ResponseEntity<Boolean> changePass(Model model, @RequestParam(value = "pass_old") String password_old,
			@RequestParam(value = "pass_new") String password_new) {
		try {
			String passUser = userServiceImpl.findUserById(authentication.getIdUser()).getAccountDto().getPassword();
			boolean response;
			User user = userServiceImpl.findUserById(authentication.getIdUser());
			model.addAttribute("informations", user.getInformation());
			List<ProvinceDto> listProvince = providedS.findAllProvince();
			model.addAttribute("listProvince", listProvince);
			model.addAttribute("users", user);
			if (BCrypt.checkpw(password_old, passUser)) {
				String passNew = pass.encode(password_new);
				userServiceImpl.updatePass(passNew, authentication.getIdUser());
				return new ResponseEntity<Boolean>(true,HttpStatus.OK);
			}else {
				return new ResponseEntity<Boolean>(false,HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
		}
		

	}
	@GetMapping("/danh-gia")
	public String managaReview(Model model) {
		User user =userServiceImpl.findUserById(authentication.getIdUser());
		model.addAttribute("user", user);
		return "account/review";
	}
	@GetMapping("/danh-gia/xoa")
	public ResponseEntity<Boolean> deleteReviewById(Model model,@RequestParam("id") String idR){
		try {
			long id= Long.parseLong(idR);		
			System.out.println(id);
			User user = userServiceImpl.findUserById(authentication.getIdUser());
			reviewServices.deteleReviewsById(id);
			Notify senduser= new Notify();senduser.setUser(user);
			senduser.setContent("??n h�ng ?� h?y b?i kh�ch h�ng");senduser.setKeyword(user.getAccountDto().getEmail()+"h?y ??n h�ng");senduser.setTime(time.convertToDateViaSqlTimestamp());
			notifyServices.Save(senduser);
			return new ResponseEntity<Boolean>(true,HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
		}
		
	}

}
