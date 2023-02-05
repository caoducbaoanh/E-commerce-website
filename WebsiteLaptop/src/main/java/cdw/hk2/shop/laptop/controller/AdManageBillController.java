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
import cdw.hk2.shop.laptop.model.Review;
import cdw.hk2.shop.laptop.services.BillServices;

@Controller
@RequestMapping("/admin/manage/bill")
public class AdManageBillController {
	@Autowired
	private BillServices billserver;
	@GetMapping("/list")
	public String manageReviews(Model model) {
		String title="Quản lý hóa đơn";
		model.addAttribute("title",title);
		return "/admin/manage_bill";
		
	}
	@RequestMapping(value = "/get/list" ,method = RequestMethod.GET,produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Bill>> getListBrands(Model model){
		try {
			return new ResponseEntity<List<Bill>>(billserver.findAllBill(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Bill>>(HttpStatus.BAD_REQUEST);
		}
		
	}
	@GetMapping("/delete")
	public ResponseEntity<Boolean> deleteBillById(Model model,@RequestParam("id") String idR){
		try {
			long id= Long.parseLong(idR);		
			System.out.println(id);
			billserver.deteleBillsById(id);
			return new ResponseEntity<Boolean>(true,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
		}
		
	}

	

}
