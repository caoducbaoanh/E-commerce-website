package cdw.hk2.shop.laptop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdw.hk2.shop.laptop.dto.WardDto;
import cdw.hk2.shop.laptop.repository.IWarDtoRespository;

@Service
public class WardDtoServices {
	@Autowired
	private IWarDtoRespository wardDto;

	public List<WardDto> findAllWard() {
		return wardDto.findAll();
	}

	public WardDto findWardByID(String id) {
		return wardDto.findById(id).orElse(null);
	}

	public List<WardDto> findAllWardByIDD(String district) {
		// TODO Auto-generated method stub
		return wardDto.findALlWardIdD(district);
	}

}
