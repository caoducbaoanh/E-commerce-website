package cdw.hk2.shop.laptop.services;

import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetailsService;

import cdw.hk2.shop.laptop.dto.AccountDto;
import cdw.hk2.shop.laptop.model.User;

public interface IUserService extends UserDetailsService{
	User save(AccountDto accountDto);
}
