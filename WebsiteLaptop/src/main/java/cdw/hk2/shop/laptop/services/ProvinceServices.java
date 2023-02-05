package cdw.hk2.shop.laptop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdw.hk2.shop.laptop.dto.ProvinceDto;
import cdw.hk2.shop.laptop.repository.IProvinceDtoRespository;

@Service
public class ProvinceServices {
	@Autowired
	private IProvinceDtoRespository dtoRespository;

	public List<ProvinceDto> findAllProvince() {
		return dtoRespository.findAll();
	}
	public ProvinceDto FindByIdProvince(String id) {
		return dtoRespository.findById(id).orElse(null);
		
	}
}
