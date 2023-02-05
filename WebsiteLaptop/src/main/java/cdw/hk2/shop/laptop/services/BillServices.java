package cdw.hk2.shop.laptop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdw.hk2.shop.laptop.model.Bill;
import cdw.hk2.shop.laptop.model.Review;
import cdw.hk2.shop.laptop.repository.IBillRespository;
@Service
public class BillServices {
@Autowired
private IBillRespository iBillRespository;

public List<Bill> findAllBill() {
	// TODO Auto-generated method stub
	return iBillRespository.findAll();
}

public void deteleBillsById(long id) {
iBillRespository.deleteById(id);	
}

public Bill save(Bill bill) {
	
return iBillRespository.save(bill);	
}

}
