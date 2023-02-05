package cdw.hk2.shop.laptop.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cdw.hk2.shop.laptop.model.Cart;
import cdw.hk2.shop.laptop.model.Product;
import cdw.hk2.shop.laptop.model.User;
import cdw.hk2.shop.laptop.repository.ICartRepository;

@Service
public class CartServices {
	@Autowired
	private ICartRepository iCartRepository;
	@PersistenceContext
	private EntityManager entityManager;

	public List<Cart> findAllCart() {
		return iCartRepository.findAll();

	}

	public Cart findCartById(long id) {
		return iCartRepository.findById(id).orElse(null);

	}

	public Cart saveCart(Cart cart) {
		return iCartRepository.save(cart);
	}

	public Cart updateCart(User user,Product product) {
		Cart cart = iCartRepository.findById(user.getId()).orElse(null);

		if(product!=null) {
			List<Product> listP=cart.getProduct();
			cart.setTotal_price(cart.getTotal_price()+product.getPrice());
			listP.add(product);
			cart.setProduct(listP);
			cart.setQuantity(listP.size());
		
			iCartRepository.save(cart);
		}else {
			
			
		}
		Cart cart1 = iCartRepository.findById(user.getId()).orElse(null);

		return cart1 ;
	}
	
	public void deleteCartByProduct(long id) {
		iCartRepository.deleteCartByProduct(id);
//		entityManager.createQuery("delete FROM cdwebshoplaptop.products_carts where products_carts.carts_id=?").setParameter(1,id).executeUpdate();
	}

	public void deleteProductByCart(long id) {
		// TODO Auto-generated method stub
		iCartRepository.deleteProductByCart(id);
		
	}

}
