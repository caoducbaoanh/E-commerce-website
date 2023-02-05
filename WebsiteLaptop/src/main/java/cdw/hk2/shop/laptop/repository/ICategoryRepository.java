package cdw.hk2.shop.laptop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cdw.hk2.shop.laptop.model.Category;
import cdw.hk2.shop.laptop.model.Product;


@Repository
@Transactional
public interface ICategoryRepository extends JpaRepository<Category, Long> {
@Query(value = "SELECT * FROM cdwebshoplaptop.categories where categories.brand_id is null",nativeQuery = true)
	List<Category> findAllCategoy();
@Query(value = "SELECT * FROM cdwebshoplaptop.categories where categories.id= ?1",nativeQuery = true)
Category findCategoryById(long id);
}

