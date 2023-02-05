package cdw.hk2.shop.laptop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cdw.hk2.shop.laptop.dto.ProvinceDto;

public interface IProvinceDtoRespository extends JpaRepository<ProvinceDto, String> {

}
