package cdw.hk2.shop.laptop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cdw.hk2.shop.laptop.model.ProductImage;

@Repository
@Transactional
public interface IProductImageRepository extends JpaRepository<ProductImage, Long> {
	@Modifying
	@Query(value = "Delete  FROM cdwebshoplaptop.product_images where product_images.product_id=?1",nativeQuery = true)
	void deleteProductImageByIdP(long id);
}
