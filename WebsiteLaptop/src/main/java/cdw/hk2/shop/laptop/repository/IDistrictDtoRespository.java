package cdw.hk2.shop.laptop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cdw.hk2.shop.laptop.dto.DistrictDto;
@Repository
@Transactional
public interface IDistrictDtoRespository extends JpaRepository<DistrictDto, String> {
@Query(value ="SELECT * FROM cdwebshoplaptop.district where district.provinceid=?1" ,nativeQuery = true)
	List<DistrictDto> findAllDistrictByID(String province);

}
