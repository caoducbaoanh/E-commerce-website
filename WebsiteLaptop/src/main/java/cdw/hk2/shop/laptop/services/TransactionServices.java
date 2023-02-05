package cdw.hk2.shop.laptop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdw.hk2.shop.laptop.model.Order;
import cdw.hk2.shop.laptop.model.Transaction;
import cdw.hk2.shop.laptop.repository.ITransactionRepository;

@Service
public class TransactionServices {
	@Autowired
 private ITransactionRepository iRepository;
 public Transaction saveTransaction(Transaction transaction) {
	 return iRepository.save(transaction);
 }
public List<Transaction> findAllTransations() {
	// TODO Auto-generated method stub
	return iRepository.findAll();
}
public List<Transaction> findAllByDateDESC() {
	// TODO Auto-generated method stub
	return iRepository.findAllByDateDESC();
}
public Transaction findTtransById(long id) {
	return iRepository.findById(id).orElse(null);
}
}
