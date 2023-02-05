package cdw.hk2.shop.laptop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cdw.hk2.shop.laptop.model.Cart;

@Repository
@Transactional
public interface ICartRepository extends JpaRepository<Cart, Long> {
	@Modifying
	@Query(value = " DELETE FROM cdwebshoplaptop.products_carts WHERE products_carts.carts_id =?1", nativeQuery = true)
	void deleteCartByProduct(long id);
	@Modifying
	@Query(value = " DELETE FROM cdwebshoplaptop.products_carts WHERE products_carts.products_id =?1", nativeQuery = true)
	void deleteProductByCart(long id);
	
}
