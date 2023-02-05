package cdw.hk2.shop.laptop.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cdw.hk2.shop.laptop.model.*;
import cdw.hk2.shop.laptop.model.Brand;
import cdw.hk2.shop.laptop.model.Cart;
import cdw.hk2.shop.laptop.model.Category;
import cdw.hk2.shop.laptop.model.Product;
import cdw.hk2.shop.laptop.model.User;
import cdw.hk2.shop.laptop.security.AuthenticationFacade;
import cdw.hk2.shop.laptop.services.BannerServices;
import cdw.hk2.shop.laptop.services.BrandServices;
import cdw.hk2.shop.laptop.services.CartServices;
import cdw.hk2.shop.laptop.services.CategoryServices;
import cdw.hk2.shop.laptop.services.ProductServices;
import cdw.hk2.shop.laptop.services.UserServiceImpl;

@Controller
public class HomeController {
	@Autowired
	private ProductServices productServices;
	@Autowired
	private BannerServices bannerS;
	@Autowired
	private AuthenticationFacade authentication;
	@Autowired
	private CategoryServices icategory;
	@Autowired
	private BrandServices brandServer;
	@Autowired
	private CartServices cartServices;
	@Autowired
	private UserServiceImpl userS;

	@GetMapping("/")
	public String homeView(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String check = authentication.getNameUser();
		System.out.println(check+"fffffffffffffffff");;
		if (!check.equals("anonymousUser")) {
			User user = userS.findUserById(authentication.getIdUser());
			Cart carts = cartServices.findCartById(user.getId());
			session.setAttribute("cartuser", carts);
		}
		List<Category> listCategory = icategory.findAllCategoryBy();
		session.setAttribute("listCategory", listCategory);
		List<Brand> listBrand = brandServer.findAllBrand();
		session.setAttribute("listBrand", listBrand);
		List<Product> listProducts = productServices.findAllProduct();
		model.addAttribute("listproduct", listProducts);
		List<Product> listTop = productServices.findAllByCategory(5);
		List<Product> listTopSale = productServices.findAllByCategory(5);
		List<Product> listNewProduct = productServices.findAllByCategory(5);
		List<Product> listliht = productServices.findAllByCategory(5);
		List<Product> listFactory = productServices.findAllByCategory(6);
		List<Banner> listBannerns = bannerS.findAllBanner();
		model.addAttribute("listBanner", listBannerns);
		model.addAttribute("top", listTopSale);
		model.addAttribute("newlist", listNewProduct);
		model.addAttribute("highlights", listliht);
		model.addAttribute("listfactory", listFactory);

		return "index";
	}

	@RequestMapping(value = "/tim-kiem/tu-khoa/san-pham", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> searchKeyWord(Model model, @RequestParam("search") String search,
			@RequestParam("key") String key) throws Exception {
		List<String> listKey = new ArrayList<String>();
		
			listKey = productServices.searchProduct(key, search);
		return new ResponseEntity<List<String>>(listKey,HttpStatus.OK);
	}
}
