package cdw.hk2.shop.laptop.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdw.hk2.shop.laptop.model.Review;
import cdw.hk2.shop.laptop.repository.IReviewRepository;

@Service
public class ReviewServices {
	@Autowired
	private IReviewRepository iReviewRepository;

	public List<Review> findAllReviews() {
		return iReviewRepository.findAll();
	}

	public String deteleReviewsById(long id) {
		iReviewRepository.deleteById(id);;
		return "delete";
	}

	public Review saveReview(Review review) {
		return iReviewRepository.save(review);

	}

	public boolean checkReview(long idU, long idP) {
		Review review= iReviewRepository.existsByUserAndProduct(idU, idP);
		System.out.println();
		if(review!=null) {
			return false;
		}else {
			return true;

		}
	}
	public List<Review> findAllbyDate(Date date) {
		return iReviewRepository.findAllDate(date);
	}

}
