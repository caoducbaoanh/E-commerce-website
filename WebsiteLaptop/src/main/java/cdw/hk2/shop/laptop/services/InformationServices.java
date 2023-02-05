package cdw.hk2.shop.laptop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdw.hk2.shop.laptop.model.Information;
import cdw.hk2.shop.laptop.model.User;
import cdw.hk2.shop.laptop.repository.IinformationRespository;
import cdw.hk2.shop.laptop.security.AuthenticationFacade;

@Service
public class InformationServices {
@Autowired
private IinformationRespository iRespository;
@Autowired
private AuthenticationFacade authenticationFacade;
@Autowired 
private UserServiceImpl userServiceImpl;
public Information updateInfor(Information infor) {
	User user = userServiceImpl.findUserById(authenticationFacade.getIdUser());
	Information getInformation= user.getInformation();
	getInformation.setCompany(infor.getCompany());
	getInformation.setCountry(infor.getCountry());
	getInformation.setDateBrithday(infor.getDateBrithday());
	getInformation.setDistrict(infor.getDistrict());
	getInformation.setNote(infor.getNote());
	getInformation.setPhone(infor.getPhone());
	getInformation.setProvince(infor.getProvince());
	getInformation.setSex(infor.getSex());
	getInformation.setVillage(infor.getVillage());
	getInformation.setWard(infor.getWard());
	return iRespository.save(getInformation);
	
}
}
