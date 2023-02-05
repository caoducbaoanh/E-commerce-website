package cdw.hk2.shop.laptop.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cdw.hk2.shop.laptop.model.Review;

@Repository
@Transactional
public interface IReviewRepository extends JpaRepository<Review, Long> {
	@Query(value = "SELECT * FROM cdwebshoplaptop.reviews where reviews.user_id =?1 and reviews.product_id=?2", nativeQuery = true)
	Review existsByUserAndProduct(long  idU, long idP);
	@Query(value = "SELECT * FROM cdwebshoplaptop.reviews where date(reviews.time)= date(?1)",nativeQuery = true)
	List<Review> findAllDate(Date date);
}
