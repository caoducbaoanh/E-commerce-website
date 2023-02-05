package cdw.hk2.shop.laptop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdw.hk2.shop.laptop.model.Banner;
import cdw.hk2.shop.laptop.repository.IBannerRespository;

@Service
public class BannerServices {
	@Autowired
	private IBannerRespository iBannerRespository;

	public List<Banner> findAllBanner() {
		return iBannerRespository.findAll();

	}
	public List<Banner> findAll(){
		return iBannerRespository.findAll();
		
	}
	public void deleteBannerById(long idB) {
	 iBannerRespository.deleteById(idB);
	}
	public Banner saveBanner(Banner banner) {
return  iBannerRespository.save(banner);		
	}
	public Banner findBannerById(long idB) {
		// TODO Auto-generated method stub
		return iBannerRespository.findById(idB).orElse(null);
	}
}
