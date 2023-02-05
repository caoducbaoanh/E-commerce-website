package cdw.hk2.shop.laptop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cdw.hk2.shop.laptop.dto.AccountDto;
import cdw.hk2.shop.laptop.services.UserServiceImpl;

@Controller
public class RegisterUserController {

@Autowired
private UserServiceImpl userServiceImpl;
@GetMapping("/dang-ky")
public String registerView() {
	return "register";
	
}
@PostMapping("/register/create/user")
public String createNewUser(Model model,@RequestParam (value = "username") String username,@RequestParam (value = "password") String password) {
	AccountDto accountDto = new AccountDto();
	accountDto.setEmail(username);
	accountDto.setPassword(password);
	accountDto.setEmailVerified(true);
	if(userServiceImpl.checkUserName(username)) {
		userServiceImpl.save(accountDto);

	}else {
		System.out.println("error_user name");
	}
	
	return "register" ;
	
}
}
