package cdw.hk2.shop.laptop.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cdw.hk2.shop.laptop.model.Brand;
import cdw.hk2.shop.laptop.model.Category;
import cdw.hk2.shop.laptop.model.Product;



@Repository
@Transactional
public interface IProductRepository extends JpaRepository<Product, Long> {
	@Query(value = "SELECT * FROM cdwebshoplaptop.products where products.name= ?1",nativeQuery = true)
	Product findProductByName(String name);
	@Query(value = "Select * From cdwebshoplaptop.products where products.brand_id= ?1",nativeQuery = true)
	Page<Product> findAllProductByBrand(long id,Pageable pageable);
	@Query(value = "Select * From cdwebshoplaptop.products where products.name like(?1)",nativeQuery = true)
	List<Product> findALlBySearch(String search);
	@Query(value = "SELECT * FROM cdwebshoplaptop.products where products.name like(?1) and products.category_id=(?2) ",nativeQuery = true)
	List<Product> findALlBySearchCategory(String like, long id);
	@Query(value = "SELECT * FROM cdwebshoplaptop.products where products.name like(?1) and products.brand_id=(?2) ",nativeQuery = true)
	List<Product> findALlBySearchBrand(String like, long id);
	@Query(value = "SELECT * FROM cdwebshoplaptop.products where products.name like(?1) and products.category_id=(?2) ",nativeQuery = true)
	Page<Product> findALlBySearchCategory(String like, long id,Pageable pageable);
	@Query(value = "SELECT * FROM cdwebshoplaptop.products where products.name like(?1) and products.brand_id=(?2) ",nativeQuery = true)
	Page<Product> findALlBySearchBrand(String like, long id,Pageable pageable);
	@Query(value = "SELECT * FROM cdwebshoplaptop.products where products.name like(?1)",nativeQuery = true)
	Page<Product> findProductByName(String name,Pageable pageable);
}
