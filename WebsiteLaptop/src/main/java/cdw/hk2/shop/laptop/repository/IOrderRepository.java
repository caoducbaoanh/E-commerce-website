package cdw.hk2.shop.laptop.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cdw.hk2.shop.laptop.model.Order;
import cdw.hk2.shop.laptop.model.Transaction;
import cdw.hk2.shop.laptop.model.User;


@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
	public List<Transaction> findAllByUser(User user);
	@Transactional
	@Modifying
	@Query(value = "SELECT * FROM cdwebshoplaptop.orders  where orders.user_id=?1",nativeQuery = true)
	List<Order> findAllOrderByIdUser(long id);
	@Query(value = "SELECT * FROM cdwebshoplaptop.orders  where date(orders.created_date)=date(?1)",nativeQuery = true)
	public List<Order> findAllByDate(Date date);
}
