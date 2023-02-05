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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cdw.hk2.shop.laptop.model.Brand;
import cdw.hk2.shop.laptop.model.Category;
import cdw.hk2.shop.laptop.services.BrandServices;
import cdw.hk2.shop.laptop.services.CategoryServices;

@Controller
@RequestMapping("/admin/manage/brand")
public class AdManageBrandController {
	@Autowired
	private BrandServices brandServices;
	@Autowired
	private CategoryServices categoryS;

	@GetMapping("/list")
	public String manageBrand(Model model) {
		Brand brand = new Brand();
		String title="Quản lý thương hiệu";
		model.addAttribute("title",title);
		model.addAttribute("brands",brand);
		List<Category> listCategories = categoryS.findAllCategory();
		model.addAttribute("categories", listCategories);
		return "/admin/manage_brand";

	}

	@RequestMapping(value = "/get/list", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Brand>> getListBrand() {
		try {
			return new ResponseEntity<List<Brand>>(brandServices.findAllBrand(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Brand>>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/add",method = RequestMethod.GET)
	public   ResponseEntity<Boolean> addBrand(Model model,@RequestParam(value = "namebrand") String name,@RequestParam(value= "category[]") Integer[] list) {
		try {
			
			Brand brand = new Brand();
			brand.setName(name);
			Brand brandS= brandServices.saveBrand(brand);
			for (int i=0;i<list.length;i++) {
				Category category =categoryS.findCategoryById(list[i]);
				category.setBrand(brandS);
				categoryS.saveCategory(category);
				}
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/delete")
	public ResponseEntity<Boolean> deleteBrand(Model model, @RequestParam(name = "id") String id) {
		try {
			long idB = Long.parseLong(id);
			brandServices.deleteBrandById(idB);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}

	}
	@GetMapping("/get")
	public String editBrand(Model model, @RequestParam(name = "id") String id) {
			long idB = Long.parseLong(id);
			Brand brand = brandServices.findBrandById(idB);
			model.addAttribute("brands", brand);
			List<Category> listCategories = categoryS.findAllCategory();
			model.addAttribute("categories", listCategories);
			return "/admin/ajax/edit_brand.html";
	}
}
