package cdw.hk2.shop.laptop.controller;

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

import cdw.hk2.shop.laptop.model.Bill;
import cdw.hk2.shop.laptop.model.Notify;
import cdw.hk2.shop.laptop.model.Order;
import cdw.hk2.shop.laptop.model.Transaction;
import cdw.hk2.shop.laptop.model.User;
import cdw.hk2.shop.laptop.security.AuthenticationFacade;
import cdw.hk2.shop.laptop.services.BillServices;
import cdw.hk2.shop.laptop.services.NotifyServices;
import cdw.hk2.shop.laptop.services.TransactionServices;
import cdw.hk2.shop.laptop.utils.StringUtils;
import cdw.hk2.shop.laptop.utils.TimeUtlis;

@Controller
@RequestMapping("/admin/manage/transaction")
public class AdManageTransactionController {
@Autowired
private TransactionServices transactionServices;
@Autowired
private BillServices billServices;
@Autowired
private AuthenticationFacade authenticatin;
@Autowired 
private TimeUtlis time;
@Autowired 
private StringUtils string;
@Autowired
private NotifyServices notifyServices;
@GetMapping("/list")
public  String manageTransaction(Model model) {
	String title="Quản lý giao dịch & vận chuyển";
	model.addAttribute("title",title);
	return "/admin/manage_transactions";
	
}
@RequestMapping(value = "/get/list",method =RequestMethod.GET,produces = MimeTypeUtils.APPLICATION_JSON_VALUE )
public ResponseEntity<List<Transaction>> getListTransaction(){
	try {
		return new ResponseEntity<List<Transaction>>(transactionServices.findAllTransations(),HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<List<Transaction>>(HttpStatus.BAD_REQUEST);
	}
}
@GetMapping("/payment")
public ResponseEntity<Boolean> paymentBill(Model model,@RequestParam("id") String idR){
double total= 0;
	try {
		long id= Long.parseLong(idR);		
		Transaction transaction= transactionServices.findTtransById(id);
		transaction.setChecks(true);
		User user=new User();
		transactionServices.saveTransaction(transaction);
		for(Order order:transaction.getOrders()) {
			total=total+order.getTotal();
			 user=order.getUser();
		}
		Bill  bill = new Bill();
		bill.setNamePay(authenticatin.getNameUser());bill.setPayDate(time.convertToDateViaSqlTimestamp());bill.setCodeBill(string.getSaltString("HD_"));
		bill.setTotalPrice(total);
		billServices.save(bill);
		Notify senduser= new Notify();senduser.setUser(user);
		senduser.setContent("Đơn hàng đã được thành toán");senduser.setKeyword("Thanh toán");senduser.setTime(time.convertToDateViaSqlTimestamp());
		notifyServices.Save(senduser);
		
		return new ResponseEntity<Boolean>(true,HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
	}
	
}

}
