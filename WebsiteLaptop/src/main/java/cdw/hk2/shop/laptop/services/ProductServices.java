package cdw.hk2.shop.laptop.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cdw.hk2.shop.laptop.model.Product;
import cdw.hk2.shop.laptop.model.ProductDetails;
import cdw.hk2.shop.laptop.model.ProductImage;
import cdw.hk2.shop.laptop.repository.IProductDetailsRepository;
import cdw.hk2.shop.laptop.repository.IProductImageRepository;
import cdw.hk2.shop.laptop.repository.IProductRepository;

@Service
public class ProductServices implements IProductServices {
	@Autowired
	private IProductRepository ipRes;
	@Autowired
	private IProductImageRepository imageRepository;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private IProductDetailsRepository ipdetailRes;
	// Select

	public Product findByIdProduct(long id) {
		return ipRes.findById(id).orElse(null);
	}

	public List<Product> findAllProduct() {
		return ipRes.findAll();
	}

	public List<Product> findAllByIdProduct(Iterable<Long> id) {
		return ipRes.findAllById(id);

	}

	// Delete
	public String deleteByIdProduct(long id) {
		ipRes.deleteById(id);
		return "deteleID";
	}

	public String deleteByProduct(Product product) {
		ipRes.delete(product);
		return "deteleProduct";
	}

	public String deteleAllByID(Iterable<Long> id) {
		ipRes.deleteAllById(id);
		return "deteleAllId";
	}

	public String deteleAll() {
		ipRes.deleteAll();
		return "deteleALL";
	}

	// Save
	public Product saveProduct(Product product) {
		return ipRes.save(product);

	}

	public String saveListProduct(List<Product> list) {
		ipRes.saveAll(list);
		return "saveListProduct";
	}

	// Update

	public Product updateProduct(Product product) {
		Product exitsGetP = ipRes.findById(product.getId()).orElse(null);
		exitsGetP.setBrand(product.getBrand());
		exitsGetP.setCategory(product.getCategory());
		exitsGetP.setDescription(product.getDescription());
		exitsGetP.setDetails(product.getDetails());
		exitsGetP.setDiscount(product.getDiscount());
		exitsGetP.setImages(product.getImages());
		exitsGetP.setQuantity(product.getQuantity());
		exitsGetP.setPrice(product.getPrice());
		exitsGetP.setName(product.getName());

		return ipRes.save(exitsGetP);

	}

	public void deleteProductImages(long id) {
		Product product = ipRes.findById(id).orElse(null);
		for (ProductImage productImage : product.getImages()) {
			String path = "C:\\Users\\Luu Pot\\eclipse-workspace\\WebsiteLaptop\\src\\main\\resources\\static\\"
					+ productImage.getName().replace("/", "\\");
			System.out.println(path);
			File file = new File(path);
			file.delete();
		}

		imageRepository.deleteProductImageByIdP(id);
	}

	// List Category
	public List<Product> findAllByCategory(long id) {
		List<Product> listCategory = new ArrayList<Product>();
		List<Product> list = ipRes.findAll();
		for (Product product : list) {
			if (product.getCategory().getId() == id) {
				listCategory.add(product);
			}
		}
		return listCategory;

	}

	// list related product
	public List<Product> findALLByProductRelated(Product product) {
		List<Product> list = ipRes.findAll();
		List<Product> listRelate = new ArrayList<Product>();
		for (Product lProduct : list) {
			if (lProduct.getBrand().getId() == product.getBrand().getId()) {
				listRelate.add(lProduct);
			}
		}

		return listRelate;
	}

	@Override
	public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, long id,
			String keyword) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();
		List<Product> list = new ArrayList<Product>();
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		String like = "%" + keyword + "%";
		if (id > 0 && keyword == null) {
			return this.ipRes.findAllProductByBrand(id, pageable);
		} else if (id == 0 && keyword == "") {
			return this.ipRes.findAll(pageable);
		} else if (id == 0 && keyword != null) {
			return this.ipRes.findProductByName(like, pageable);
		} else if (id > 0 && keyword != null) {
			List<Product> listC = ipRes.findALlBySearchCategory(like, id);
			if (listC.size()>0) {
				return this.ipRes.findALlBySearchCategory(like, id, pageable);
			} else {
				return this.ipRes.findALlBySearchBrand(like, id, pageable);
			}
		} else {
			return this.ipRes.findAll(pageable);

		}

	}

	public double sumPrice(List<Product> list, int discount) {
		double total = 0;
		for (Product product : list) {
			total = total + product.getPrice();
		}
		return total;

	}

	public Iterable<Product> findALL() {
		return ipRes.findAll();
	}

	public double GetPriceTotal(List<Product> list, Double discount) {
		double total = 0;
		double price = 0;
		double discountP = (100 - discount) / 100;
		System.out.println("dic" + discountP);
		for (Product p : list) {
			total = total + p.getPrice();
			System.out.println(total);
		}
		price = (total + 30000) * discountP;
		System.out.println(price + "price");
		return price;

	}

	public Product findProductByName(String name) {
		return ipRes.findProductByName(name);

	}

	@Transactional
	public void deleleProductImagebyIdP(List<ProductImage> list) {
		for (ProductImage inImage : list) {
			imageRepository.deleteById(inImage.getId());
		}
	}

	public ProductImage saveImage(ProductImage image) {
		return imageRepository.save(image);

	}

	public ProductDetails saveDetails(ProductDetails details) {
		return ipdetailRes.save(details);
	}

	public List<String> searchProduct(String key, String search) throws Exception {
		List<String> list = new ArrayList<String>();
		String like = "%" + search + "%";
		List<String> listName = new ArrayList<String>();
		if (key.equals("all")) {
			List<Product> listP = ipRes.findALlBySearch(like);
			listName = searchNameProduct(listP);
		} else {
			long id = Long.parseLong(key);
			List<Product> listP = ipRes.findALlBySearchCategory(like, id);
			if (listP.isEmpty()) {
				List<Product> listBrand = ipRes.findALlBySearchBrand(like, id);
				listName = searchNameProduct(listBrand);
				System.out.println(listBrand.get(0).getName());
			} else {
				listName = searchNameProduct(listP);
			}
		}

		return listName;
	}

	public List<String> searchNameProduct(List<Product> listP) {
		List<String> listName = new ArrayList<String>();
		for (Product product : listP) {
			int index = product.getName().indexOf("(");
			String name = product.getName().substring(0, index);
			listName.add(name);
			Collections.sort(listName);
			listName = getListStringName(listName);
		}
		return listName;

	}

	public List<String> getListStringName(List<String> name) {
		List<String> list = new ArrayList<String>();
		if (name.size() > 8) {
			for (int i = 0; i < 8; i++) {
				list.add(name.get(i));
			}
		} else {
			list = name;
		}
		return list;

	}
}
