package cdw.hk2.shop.laptop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdw.hk2.shop.laptop.dto.DistrictDto;
import cdw.hk2.shop.laptop.repository.IDistrictDtoRespository;

@Service
public class DistrictServices {
	@Autowired
	private IDistrictDtoRespository iDistrictDtoRespository;

	public List<DistrictDto> findAllDistrict() {
		return iDistrictDtoRespository.findAll();

	}

	public DistrictDto findDistrictById(String id) {
		return iDistrictDtoRespository.findById(id).orElse(null);
	}

	public List<DistrictDto> findAllDistrictByIDP(String province) {
		// TODO Auto-generated method stub
		return iDistrictDtoRespository.findAllDistrictByID(province);
	}
}
