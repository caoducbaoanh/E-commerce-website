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
import org.springframework.web.multipart.MultipartFile;

import cdw.hk2.shop.laptop.model.Banner;
import cdw.hk2.shop.laptop.model.Brand;
import cdw.hk2.shop.laptop.model.Category;
import cdw.hk2.shop.laptop.services.BannerServices;
import cdw.hk2.shop.laptop.services.BrandServices;
import cdw.hk2.shop.laptop.services.CategoryServices;
import cdw.hk2.shop.laptop.utils.UrlImageUtils;

@Controller
@RequestMapping("/admin/manage/banner")
public class AdManageBannerController {
	@Autowired
	private BannerServices bannerserver;
	@Autowired
	private CategoryServices categoryS;
	@Autowired
	private UrlImageUtils urlimage;

	@GetMapping("/list")
	public String manageBrand(Model model) {
		Banner banner = new Banner();
		model.addAttribute("banner", banner);
		String title="Quản lý banner";
		model.addAttribute("title",title);
		List<Category> listCategories = categoryS.findAllCategory();
		model.addAttribute("categories", listCategories);
		return "/admin/manage_banner";

	}

	@RequestMapping(value = "/get/list", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Banner>> getListBrand() {
		try {
			return new ResponseEntity<List<Banner>>(bannerserver.findAllBanner(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Banner>>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public ResponseEntity<Boolean> addBrand(Model model, @ModelAttribute(name = "banner") Banner banner,
			@RequestParam("files") MultipartFile[] multipartFiles) {
		try {
			String path = "/images/banner"+"/" + multipartFiles[0].getOriginalFilename();
			banner.setLink_images(path);
			urlimage.saveImage(multipartFiles, false,"",0);
			bannerserver.saveBanner(banner);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/delete")
	public ResponseEntity<Boolean> deleteBrand(Model model, @RequestParam(name = "id") String id) {
		try {
			long idB = Long.parseLong(id);
			bannerserver.deleteBannerById(idB);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/get")
	public String editBrand(Model model, @RequestParam(name = "id") String id) {
		long idB = Long.parseLong(id);
		// Brand brand = brandServices.findBrandById(idB);
		// model.addAttribute("brands", brand);
		List<Category> listCategories = categoryS.findAllCategory();
		model.addAttribute("categories", listCategories);
		return "/admin/ajax/edit_brand.html";
	}
}
