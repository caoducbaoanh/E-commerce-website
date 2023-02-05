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

import cdw.hk2.shop.laptop.model.Brand;
import cdw.hk2.shop.laptop.model.Review;
import cdw.hk2.shop.laptop.services.ReviewServices;

@Controller
@RequestMapping(value = "/admin/manage/reviews")
public class AdManageReviewController {
@Autowired
private ReviewServices reviewServices;

@GetMapping("/list")
public String manageReviews(Model model) {
	String title="Quản lý bình luận";
	model.addAttribute("title",title);
	return "/admin/manage_comment";
	
}
@RequestMapping(value = "/get/list" ,method = RequestMethod.GET,produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public ResponseEntity<List<Review>> getListBrands(Model model){
	try {
		return new ResponseEntity<List<Review>>(reviewServices.findAllReviews(),HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<List<Review>>(HttpStatus.BAD_REQUEST);
	}
	
}
@GetMapping("/delete")
public ResponseEntity<Boolean> deleteReviewById(Model model,@RequestParam("id") String idR){
	try {
		long id= Long.parseLong(idR);		
		System.out.println(id);
		reviewServices.deteleReviewsById(id);
		return new ResponseEntity<Boolean>(true,HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
	}
	
}

}
