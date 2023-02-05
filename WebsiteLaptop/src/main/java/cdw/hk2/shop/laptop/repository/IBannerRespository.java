package cdw.hk2.shop.laptop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cdw.hk2.shop.laptop.model.Banner;
@Repository
public interface IBannerRespository  extends JpaRepository<Banner, Long>{

}
