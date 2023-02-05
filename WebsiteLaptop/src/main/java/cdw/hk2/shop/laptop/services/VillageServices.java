package cdw.hk2.shop.laptop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdw.hk2.shop.laptop.dto.VillageDto;
import cdw.hk2.shop.laptop.repository.IVillageDtoRespository;

@Service
public class VillageServices {
	@Autowired
	private IVillageDtoRespository villageDtoRespository;

	public VillageDto findVillageDtoByStringId(String id) {

		return villageDtoRespository.findById(id).orElse(null);

	}

	public List<VillageDto> findAllVillageByID(String ward) {
		// TODO Auto-generated method stub
		return villageDtoRespository.findAllVillageByIdD(ward);
	}
}
