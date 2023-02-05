package cdw.hk2.shop.laptop.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import cdw.hk2.shop.laptop.model.Brand;
import cdw.hk2.shop.laptop.model.Category;
import cdw.hk2.shop.laptop.model.Product;
import cdw.hk2.shop.laptop.model.ProductDetails;
import cdw.hk2.shop.laptop.model.ProductImage;
import cdw.hk2.shop.laptop.model.Review;
import cdw.hk2.shop.laptop.model.User;
import cdw.hk2.shop.laptop.security.AuthenticationFacade;
import cdw.hk2.shop.laptop.services.BrandServices;
import cdw.hk2.shop.laptop.services.CategoryServices;
import cdw.hk2.shop.laptop.services.ProductServices;
import cdw.hk2.shop.laptop.services.UserServiceImpl;
import cdw.hk2.shop.laptop.utils.UrlImageUtils;

@Controller
@RequestMapping("/admin/manage/product")
public class AdManageProductController {
	@Autowired
	private ProductServices productServices;
	@Autowired
	private CategoryServices categoryServies;
	@Autowired
	private BrandServices brandService;
	@Autowired
	private UrlImageUtils urlImageUtils;
	@Autowired
	private AuthenticationFacade authenticateFace;
	@Autowired
	private UserServiceImpl userServer;

	@GetMapping("/list")
	public String manageProduct(Model model) {
		String title="Quản lý sản phẩm";
		model.addAttribute("title",title);
		Product product = new Product();
		product.setCategory( new Category());
		model.addAttribute("products", product);
		List<Category> category = categoryServies.findAllCategory();
		List<Brand> listBrands = brandService.findAllBrand();
		model.addAttribute("categorys", category);
		ProductDetails productDetails = new ProductDetails();
		model.addAttribute("product_details", productDetails);
		model.addAttribute("brands", listBrands);
		return "/admin/manage_products";
	}

	@RequestMapping(value = "/get/list", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	private ResponseEntity<Iterable<Product>> listP() {

		try {
			return new ResponseEntity<Iterable<Product>>(productServices.findALL(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Iterable<Product>>(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/delete")
	public ResponseEntity<Boolean> deteleProductById(@RequestParam("idP") String value) {
		try {
			long id = Long.parseLong(value);
			productServices.deleteByIdProduct(id);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public String addProduct(Model model, @ModelAttribute(name = "products") Product product,
			@ModelAttribute(name = "product_details") ProductDetails productDetails,
			@RequestParam("upload_file") MultipartFile[] multipartFiles,@RequestParam("categorys") String idc,
			@RequestParam("trademark_product") String idB) throws IOException {
	long idCa = Long.parseLong(idc);
		User user = userServer.findUserById(authenticateFace.getIdUser());
		Category category = categoryServies.findCategoryById(idCa);
		long IdBa = Long.parseLong(idB);
		Brand brand = brandService.findBrandById(IdBa);
		product.setDetails(productDetails);
		product.setBrand(brand);
		product.setUser(user);
		List<ProductImage> listP = new ArrayList<ProductImage>();
		Product productSave = productServices.saveProduct(product);
		product.setImages(listP);
		product.setCategory(category);
		urlImageUtils.saveImage(multipartFiles, true, brand.getName(),0);
		ArrayList<ProductImage> productImages = new ArrayList<ProductImage>();
		for (MultipartFile muFile : multipartFiles) {
			ProductImage productImage = new ProductImage();
			String replace = muFile.getOriginalFilename().replace(' ', '_');
			String path = "product/laptop/" + brand.getName() + "/" + replace;

			productImage.setName(path);
			productImage.setProduct(productSave);
			productImages.add(productImage);
		}
		productSave.setImages(productImages);

		productServices.saveProduct(productSave);

		return "/admin/manage_products";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public RedirectView  getViewProduct(Model model, @ModelAttribute(name = "products") Product product,
			@ModelAttribute(name = "product_details") ProductDetails productDetails,
			@RequestParam("uploadfileup") MultipartFile[] multipartFiles, @RequestParam("categories") String idc,
			@RequestParam("trademark_product") String idB) throws IOException {
		long idCa = Long.parseLong(idc);
		User user = userServer.findUserById(authenticateFace.getIdUser());
		Category category = categoryServies.findCategoryById(idCa);
		List<ProductImage> list = product.getImages();
		long IdBa = Long.parseLong(idB);
		Brand brand = brandService.findBrandById(IdBa);
		product.setBrand(brand);
		product.setUser(user);
		productServices.deleteProductImages(product.getId());
		List<ProductImage> listP = new ArrayList<ProductImage>();
		product.setImages(listP);
		product.setCategory(category);
		urlImageUtils.saveImage(multipartFiles, true, brand.getName(),product.getId());
		product.setDetails(productDetails);
		Product productSave = productServices.updateProduct(product);
		for (MultipartFile muFile : multipartFiles) {
			ProductImage productImage = new ProductImage();
			String replace = muFile.getOriginalFilename().replace(' ', '_');
			String path = "product/laptop/" + brand.getName() + "/" + replace;
			productImage.setProduct(productSave);
			productImage.setName(path);
			productServices.saveImage(productImage);
		}
	//	productDetails.setProduct(productSave);
//		productServices.saveDetails(productDetails);
		return new RedirectView("/admin/manage/product/list");
	}

	@GetMapping("/get")
	public String getProduct(Model model, @RequestParam("idP") String value) {
		long id = Long.parseLong(value);
		Product product = productServices.findByIdProduct(id);
		model.addAttribute("products", product);
		ProductDetails productDetails = product.getDetails();
		model.addAttribute("product_details", productDetails);
		List<Category> category = categoryServies.findAllCategory();
		List<Brand> listBrands = brandService.findAllBrand();
		model.addAttribute("brands", listBrands);
		model.addAttribute("categorys", category);
		return "/admin/ajax/edit_product.html";
	}

	@GetMapping("/account/product/reviews")
	public String productReviews(Model model, @ModelAttribute(name = "reviews") Review review) {

		return null;

	}

}
