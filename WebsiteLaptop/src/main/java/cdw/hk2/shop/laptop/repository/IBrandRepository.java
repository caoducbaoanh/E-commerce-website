package cdw.hk2.shop.laptop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.annotations.QueryEmbedded;

import cdw.hk2.shop.laptop.model.Brand;


@Repository
public interface IBrandRepository extends JpaRepository<Brand, Long> {
	@Transactional
	@Query(value ="	Select * FROM cdwebshoplaptop.brands where brands.name =?1" ,nativeQuery = true)
	Brand findBrandByName(String name);
}
