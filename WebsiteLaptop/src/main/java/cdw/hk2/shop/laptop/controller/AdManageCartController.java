package cdw.hk2.shop.laptop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cdw.hk2.shop.laptop.model.Cart;
import cdw.hk2.shop.laptop.services.CartServices;

@Controller
@RequestMapping("/admin/manage/cart")
public class AdManageCartController {
@Autowired

private CartServices cartServices;
@GetMapping("/list")
public String manageCart(Model model) {
	String title="Quản lý giỏ hàng";
	model.addAttribute("title",title);
	return "/admin/manage_carts";
	
}
@RequestMapping(value = "/get/list" ,method =RequestMethod.GET,produces = MimeTypeUtils.APPLICATION_JSON_VALUE )
public ResponseEntity<List<Cart>> getListCarts(){
	try {
		return new ResponseEntity<List<Cart>>(cartServices.findAllCart(),HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<List<Cart>>(HttpStatus.BAD_REQUEST);
	}
}
}
