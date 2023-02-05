package cdw.hk2.shop.laptop.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cdw.hk2.shop.laptop.model.Brand;
import cdw.hk2.shop.laptop.model.Product;
import cdw.hk2.shop.laptop.model.Review;
import cdw.hk2.shop.laptop.model.User;
import cdw.hk2.shop.laptop.security.AuthenticationFacade;
import cdw.hk2.shop.laptop.services.BrandServices;
import cdw.hk2.shop.laptop.services.CategoryServices;
import cdw.hk2.shop.laptop.services.ProductServices;
import cdw.hk2.shop.laptop.services.ReviewServices;
import cdw.hk2.shop.laptop.services.UserServiceImpl;
import cdw.hk2.shop.laptop.utils.TimeUtlis;
import jdk.jfr.Category;

@Controller
@RequestMapping("/sanpham")
public class ProductController {
	@Autowired
	private ProductServices pServer;
	@Autowired
	private BrandServices brandS;
	@Autowired
	private AuthenticationFacade authentication;
	@Autowired
	private UserServiceImpl users;
	@Autowired
	private ReviewServices reviewS;
	@Autowired
	private TimeUtlis time;
	@Autowired
	private CategoryServices categoryServices;

	@RequestMapping(value = "/id/{id}/detail/{dtlId}", method = RequestMethod.GET)
	public String getViewProduct(Model model, @PathVariable(value = "id") String value) {
		long id = Long.parseLong(value);
		Product getProduct = pServer.findByIdProduct(id);
		List<Product> listProductRelated = pServer.findALLByProductRelated(getProduct);
		List<Product> listProduct = pServer.findAllProduct();
		model.addAttribute("getProduct", getProduct);
		model.addAttribute("productRelated", listProductRelated);
		model.addAttribute("listProduct", listProduct);
		Review review = new Review();
		model.addAttribute("reviews", review);
		return "/product_detail";
	}

	@GetMapping("/tat-ca-san-pham")
	private String viewAllProduct(Model model) {
		return findPaginated(1, "price", "asc", model, 0, null);
	}

//	@RequestMapping(value = "/{name}",method = RequestMethod.GET)
//	private String productCategory(Model model) {
//		return findPaginated(1, "id", "asc", model, null);
//
//	}

	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model, long id, String keyword) {
		int pageSize = 15;
		List<Brand> lisbBrands = brandS.findAllBrand();
		model.addAttribute("listBrand", lisbBrands);
		Page<Product> page = pServer.findPaginated(pageNo, pageSize, sortField, sortDir, id, keyword);
		List<Product> listProduct = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listProduct", listProduct);
		return "product";
	}

	@RequestMapping(value = "/danh-gia/", method = RequestMethod.GET)
	public ResponseEntity<String> addReviews(Model model, @ModelAttribute(name = "reviews") Review review,
			@RequestParam("valueID") String valueID, @RequestParam("review") String comment) {
		String msg = "";
		User user = users.findUserById(authentication.getIdUser());
		long id = Long.parseLong(valueID);
		Product product = pServer.findByIdProduct(id);
//		try {
		if (reviewS.checkReview(user.getId(), product.getId())) {
			review.setConfirm(false);
			review.setComment(comment);
			ArrayList<Review> listReview = new ArrayList<Review>();
			review.setUser(user);
			review.setTime(time.convertToDateViaSqlTimestamp());
			review.setProduct(product);
			Review save = reviewS.saveReview(review);
//			listReview.add(save);
//			product.setReviews(listReview);
//			pServer.saveProduct(product);
//			user.setReviews(listReview);
//			users.saveUser(user);
			msg = "Bình luận thành công";
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		} else {
			msg = "bạn đã bình luận ở sản phẩm này";
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}

//		} catch (Exception e) {
//			msg = "xảy ra lỗi quá trình bình luận";
//			return new ResponseEntity<String>(msg,HttpStatus.OK);
//		}

	}

	@GetMapping("/the-loai/{category}")
	public String getCategory(Model model, @PathVariable(value = "category") String category) {
		Brand brand = brandS.findBrandByName(category);
		return findPaginated(1, "price", "asc", model, brand.getId(), null);
	}

	@GetMapping("/tim-kiem/")
	public String searchProductKeyword(Model model, @RequestParam(name = "valueID") String category,
			@RequestParam(name = "search") String keyword) {
		long key = 0;
		if (!category.equals("all")) {
			key = Long.parseLong(category);
			return findPaginated(1, "price", "asc", model, key, keyword);
		}
		System.out.println(keyword);
		return findPaginated(1, "price", "asc", model, key, keyword);

	}
}
