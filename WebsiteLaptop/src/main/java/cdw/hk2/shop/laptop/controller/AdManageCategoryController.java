package cdw.hk2.shop.laptop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cdw.hk2.shop.laptop.model.Category;
import cdw.hk2.shop.laptop.services.CategoryServices;

@Controller
@RequestMapping(value = "/admin/manage/category")
public class AdManageCategoryController {
@Autowired
private CategoryServices categoryServices;


@GetMapping("/list")
public  String manageCategory(Model model) {
	Category category = new Category();
	String title="Quản lý thể loại";
	model.addAttribute("title",title);
	model.addAttribute("categories",category);
	return "/admin/manage_categorys";
	
}
@RequestMapping(value = "/get/list" ,method = RequestMethod.GET,produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public ResponseEntity<List<Category>> getListCategory(){
	
	return new ResponseEntity<List<Category>>(categoryServices.findAllCategory(),HttpStatus.OK);
	
}
@GetMapping("/delete")
public  ResponseEntity<Boolean> deleteCategory(Model model,@RequestParam(name = "id") String id){
	try {
		long idC=Long.parseLong(id);
		categoryServices.deleteCategoryById(idC);
		return new ResponseEntity<Boolean>(true,HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
	}
	
}
@RequestMapping(value = "/add",method = RequestMethod.POST)
public  ResponseEntity<Boolean> addCategory(Model model,@ModelAttribute(value ="categories") Category category ){
	try {
		categoryServices.saveCategory(category);
		return new ResponseEntity<Boolean>(true,HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<Boolean>(false ,HttpStatus.BAD_REQUEST);
		
	}
	
}
}
