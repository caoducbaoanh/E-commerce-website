package cdw.hk2.shop.laptop.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import cdw.hk2.shop.laptop.dto.*;
import cdw.hk2.shop.laptop.model.AddressOrder;
import cdw.hk2.shop.laptop.model.Cart;
import cdw.hk2.shop.laptop.model.Notify;
import cdw.hk2.shop.laptop.model.Product;
import cdw.hk2.shop.laptop.model.User;
import cdw.hk2.shop.laptop.security.AuthenticationFacade;
import cdw.hk2.shop.laptop.services.CartServices;
import cdw.hk2.shop.laptop.services.NotifyServices;
import cdw.hk2.shop.laptop.services.ProductServices;
import cdw.hk2.shop.laptop.services.ProvinceServices;
import cdw.hk2.shop.laptop.services.UserServiceImpl;
import cdw.hk2.shop.laptop.utils.TimeUtlis;

@Controller
@RequestMapping("/tai-khoan")
public class ShoppingCartController {
	@Autowired
	private ProductServices pServer;
	@Autowired
	private ProvinceServices prServices;
	@Autowired
	private AuthenticationFacade authentication;
	@Autowired
	private CartServices cartServices;
	@Autowired
	private UserServiceImpl userServices;
	@Autowired
	private TimeUtlis time;
	@Autowired
	private NotifyServices notifyServer;

	@GetMapping("/gio-hang")
	public String viewCart(Model model,HttpServletRequest request) {
			long id = authentication.getIdUser();
			Cart cart = cartServices.findCartById(authentication.getIdUser());
			List<Product> cartP= new ArrayList<Product>();
			AddressOrder ao = new AddressOrder();
			model.addAttribute("cart", cart);
			model.addAttribute("amount", 1);
			HttpSession session = request.getSession();
			session.removeAttribute("cartuser");
			session.setAttribute("cartuser", cart);
			List<ProvinceDto> listProvince = prServices.findAllProvince();
			List<Product> listOther = pServer.findAllProduct();
			model.addAttribute("listProvince", listProvince);
			model.addAttribute("listOther", listOther);
			model.addAttribute("address_order", ao);
			return "shopping_cart";
	}
	@GetMapping("/themgiohang")
	public String addProductCart(Model model, HttpServletRequest request) {
		if (!authentication.getNameUser().equals("")) {
		
			User user = userServices.findUserById(authentication.getIdUser());
			String getID = request.getParameter("ajaxID");
			long id = Long.parseLong(getID);
			Product product = pServer.findByIdProduct(id);
			Cart cart =cartServices.updateCart(user,product);
			HttpSession session = request.getSession();
			session.removeAttribute("cartuser");
			session.setAttribute("cartuser", cart);
			Notify carts= new Notify();
			carts.setChecks(false);carts.setKeyword("cart");carts.setContent( user.getName()+"-"+"?� th�m s?n ph?m:");carts.setNameId(carts.getId());
			carts.setTime(time.convertToDateViaSqlTimestamp());carts.setUser(user);
			notifyServer.Save(carts);
			return "fragments/cart";
			
		} else {
			return "login";
		}

	}

	@GetMapping("/capnhatdonhang")
	public String updatecart(Model model) {
		return null;

	}
	@GetMapping("/gio-hang/xoa/{id}")
	public RedirectView deleteProductCart(HttpServletRequest request,Model model,@PathVariable("id") long id) {
		HttpSession session = request.getSession();
		session.removeAttribute("cartuser");
		cartServices.deleteProductByCart(id);

		return new RedirectView("/tai-khoan/gio-hang");
		
	}

}
