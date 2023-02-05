package cdw.hk2.shop.laptop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cdw.hk2.shop.laptop.model.Bill;

public interface IBillRespository extends JpaRepository<Bill, Long>{

}
