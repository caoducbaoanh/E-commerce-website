package cdw.hk2.shop.laptop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import cdw.hk2.shop.laptop.dto.AccountDto;
import cdw.hk2.shop.laptop.services.UserServiceImpl;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	@Autowired
	private UserServiceImpl userService;

	@Override
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public String getNameUser() {
		Authentication authentication = authenticationFacade.getAuthentication();

		return authentication.getName();

	}

	public long getIdUser() {
		AccountDto account = userService.getUserByUsername(getNameUser());
		return account.getId();

	}

}
