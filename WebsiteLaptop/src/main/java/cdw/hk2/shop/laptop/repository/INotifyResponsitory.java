package cdw.hk2.shop.laptop.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cdw.hk2.shop.laptop.model.Notify;
@Repository
@Transactional
public interface INotifyResponsitory extends JpaRepository<Notify, Long> {


}
