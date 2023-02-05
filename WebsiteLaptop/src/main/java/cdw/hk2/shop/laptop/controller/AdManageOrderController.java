package cdw.hk2.shop.laptop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cdw.hk2.shop.laptop._enum.EOrderStatus;
import cdw.hk2.shop.laptop.model.Notify;
import cdw.hk2.shop.laptop.model.Order;
import cdw.hk2.shop.laptop.model.Transaction;
import cdw.hk2.shop.laptop.model.User;
import cdw.hk2.shop.laptop.services.NotifyServices;
import cdw.hk2.shop.laptop.services.OrderServices;
import cdw.hk2.shop.laptop.services.TransactionServices;
import cdw.hk2.shop.laptop.utils.StringUtils;
import cdw.hk2.shop.laptop.utils.TimeUtlis;

@Controller
@RequestMapping("/admin/manage/order")
public class AdManageOrderController {
	@Autowired
	private OrderServices orderS;
	@Autowired
	private StringUtils sku;
	@Autowired
	private TimeUtlis time;
	@Autowired
	private TransactionServices transS;
	@Autowired
	private NotifyServices notifyServices;
	
@GetMapping("/list")
public String listOrder(Model model) {
	String title="Quản lý đơn hàng";
	model.addAttribute("title",title);
	return "/admin/manage_orders";
}
@RequestMapping(value = "/get/list",method = RequestMethod.GET,produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public ResponseEntity<List<Order>> getListOrder(){
	try {
		return new ResponseEntity<List<Order>>(orderS.findAllOrder(),HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<List<Order>>(HttpStatus.BAD_REQUEST);
	}
}
@RequestMapping(value = "/proccess",method = RequestMethod.GET)
private ResponseEntity<Boolean> proccessOrder(Model model,@RequestParam(name = "id") String id, @RequestParam(name = "proccess") String proccess ){
	System.out.println(id+proccess);
	long idU =Long.parseLong(id);
	Order order =orderS.findOrderById(idU);
	order.getUser();
	orderS.proccessOrder(order,proccess);
	Notify senduser= new Notify();senduser.setUser(order.getUser());
	senduser.setContent("Đơn hàng đã được vận chuyển");senduser.setKeyword("Vận chuyển");senduser.setTime(time.convertToDateViaSqlTimestamp());
	notifyServices.Save(senduser);
	return new ResponseEntity<Boolean>(true,HttpStatus.OK);
	
}
@RequestMapping(value = "/list/detele")
private ResponseEntity<Boolean> clearOrder(Model model,@RequestParam (name = "id") String id){
	long idu= Long.parseLong(id);
	Order order =orderS.findOrderById(idu);
	orderS.deleteOrderById(idu);
	Notify senduser= new Notify();senduser.setUser(order.getUser());
	senduser.setContent("Đơn hàng đã được hủy");senduser.setKeyword("Hủy đơn hàng");senduser.setTime(time.convertToDateViaSqlTimestamp());
	notifyServices.Save(senduser);
	return new ResponseEntity<Boolean>(true,HttpStatus.OK);
	
}
}
