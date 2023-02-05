package cdw.hk2.shop.laptop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cdw.hk2.shop.laptop.dto.VillageDto;
@Repository
@Transactional
public interface IVillageDtoRespository extends JpaRepository<VillageDto, String> {
	@Query(value ="SELECT * FROM cdwebshoplaptop.village where village.wardid=?1" ,nativeQuery = true)
	List<VillageDto> findAllVillageByIdD(String ward);

}
