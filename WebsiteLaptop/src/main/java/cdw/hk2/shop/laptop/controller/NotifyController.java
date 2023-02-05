package cdw.hk2.shop.laptop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cdw.hk2.shop.laptop.model.Notify;
import cdw.hk2.shop.laptop.model.Order;
import cdw.hk2.shop.laptop.security.AuthenticationFacade;
import cdw.hk2.shop.laptop.services.NotifyServices;
import cdw.hk2.shop.laptop.services.OrderServices;

@Controller
	@RequestMapping("/tai-khoan/thong-bao")
	public class NotifyController {
		@Autowired
		private NotifyServices notifyServices;
		@Autowired
		private AuthenticationFacade authentication;
		@GetMapping("/danh-sach-thong-bao")
		public String orderView(Model model) {
			List<Notify> list= notifyServices.findAllNotifyrByIdUser(authentication.getIdUser());
			model.addAttribute("listNotify", list);
		
			return "/account/notify";
		}
		@GetMapping("/xoa")
		public ResponseEntity<Boolean> deleteReviewById(Model model,@RequestParam("id") String idR){
			try {
				long id= Long.parseLong(idR);		
				notifyServices.deleteNotifyById(id);
				
				return new ResponseEntity<Boolean>(true,HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
			}
			
		}
}
