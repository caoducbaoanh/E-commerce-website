package cdw.hk2.shop.laptop.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cdw.hk2.shop.laptop.model.AddressOrder;
import cdw.hk2.shop.laptop.model.Cart;
import cdw.hk2.shop.laptop.model.Notify;
import cdw.hk2.shop.laptop.model.Transaction;
import cdw.hk2.shop.laptop.model.Order;
import cdw.hk2.shop.laptop.model.Payment;
import cdw.hk2.shop.laptop.model.Product;
import cdw.hk2.shop.laptop.model.User;
import cdw.hk2.shop.laptop.security.AuthenticationFacade;
import cdw.hk2.shop.laptop.services.CartServices;
import cdw.hk2.shop.laptop.services.DistrictServices;
import cdw.hk2.shop.laptop.services.NotifyServices;
import cdw.hk2.shop.laptop.services.OrderServices;
import cdw.hk2.shop.laptop.services.ProductServices;
import cdw.hk2.shop.laptop.services.ProvinceServices;
import cdw.hk2.shop.laptop.services.TransactionServices;
import cdw.hk2.shop.laptop.services.UserServiceImpl;
import cdw.hk2.shop.laptop.services.VillageServices;
import cdw.hk2.shop.laptop.services.WardDtoServices;
import cdw.hk2.shop.laptop.utils.EmailServiceImpl;
import cdw.hk2.shop.laptop.utils.StringUtils;
import cdw.hk2.shop.laptop.utils.TimeUtlis;

@Controller
public class PaymentController {
	@Autowired
	private CartServices cartServices;
	@Autowired
	private AuthenticationFacade authentication;
	@Autowired
	private ProvinceServices prServices;
	@Autowired
	private DistrictServices dServices;
	@Autowired
	private WardDtoServices wardServies;
	@Autowired
	private VillageServices villageS;
	@Autowired
	private ProductServices pServer;
	@Autowired
	private UserServiceImpl userSer;
	@Autowired
	private OrderServices orderServices;
	@Autowired
	private TransactionServices transService;
	@Autowired
	private StringUtils stricut;
	@Autowired
	private TimeUtlis time;
	@Autowired
	private NotifyServices notifyServer;
	@Autowired
	private EmailServiceImpl sendMail;

	@PostMapping("/thanhtoan")
	public String viewPayment(Model model, @ModelAttribute(value = "address_order") AddressOrder addressOrder,
			HttpSession session) {
		long id = authentication.getIdUser();
		addressOrder.setProvince(prServices.FindByIdProvince(addressOrder.getProvince()).getName());
		addressOrder.setDistrict(dServices.findDistrictById(addressOrder.getDistrict()).getName());
		addressOrder.setWard(wardServies.findWardByID(addressOrder.getWard()).getName());
		addressOrder.setVillage(villageS.findVillageDtoByStringId(addressOrder.getVillage()).getName());
		Cart cart = cartServices.findCartById(authentication.getIdUser());
		model.addAttribute("listProduct", cart.getProduct());
		session.setAttribute("address_order", addressOrder);
		String s = (String) model.getAttribute("total");
		double totalPrice = pServer.GetPriceTotal(cart.getProduct(), 5.0);
		model.addAttribute("grandtotal", totalPrice);
		return "checkout";

	}

	@PostMapping("/thanh-toan-don-hang")
	public ResponseEntity<Boolean> orderPayment(Model model, HttpServletRequest request,
			@RequestParam(name = "transaction") String transaction,
			@RequestParam(name = "paymentOrder") String raPayment, @RequestParam(name = "address") String address) {
		try {
		long id = authentication.getIdUser();
		User user = userSer.findUserById(id);
		Cart cart = cartServices.findCartById(authentication.getIdUser());
		
		HttpSession session = request.getSession();
		AddressOrder addressOrder = (AddressOrder) session.getAttribute("address_order");
		Payment payment = new Payment();
		payment.setName(raPayment);
		payment.setTransaction(transaction);
		String SKU = stricut.getSaltString("MDH_");
		Order order = new Order();
		order.setUser(user);
		
		order.setPayment(payment);
		order.setAddress(addressOrder);
		order.setSku_order(SKU);
		order.setTotal(cart.getTotal_price());
		order.setQuantity(cart.getQuantity());
		order.setCreatedDate(time.convertToDateViaSqlTimestamp());
		Order orderS = orderServices.saveOrder(order);
		orderServices.updateProduct(cart.getProduct(), orderS);
		cartServices.deleteCartByProduct(cart.getId());
		session.removeAttribute("cartuser");
		session.setAttribute("cartuser", new Cart());
		Notify orders= new Notify();
		orders.setChecks(false);orders.setKeyword("Order");orders.setContent( user.getName()+"Đã đặt hàng thành công:"+order.getSku_order());orders.setNameId(orders.getId());
		orders.setTime(time.convertToDateViaSqlTimestamp());orders.setUser(user);
	//	sendMail.sendMail(orders.getContent(),orders.getKeyword(), user.getAccountDto().getEmail());
		notifyServer.Save(orders);
		cart.setQuantity(0);cart.setTotal_price(0);
		cartServices.saveCart(cart);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
		}

	}
}
