package cdw.hk2.shop.laptop.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cdw.hk2.shop.laptop.model.User;
import cdw.hk2.shop.laptop.services.UserServiceImpl;

@Controller
@RequestMapping("/admin/manage/user")
public class AdManageUserController {
@Autowired
private UserServiceImpl userServiceImpl;
@GetMapping("/list")
public String manageUser(Model model) {
	String title="Quản lý tài khoản";
	model.addAttribute("title",title);
	return "/admin/manage_users";
}
@RequestMapping(value = "/get/list",method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public ResponseEntity<List<User>>  getListUser(){
	try {
		return new ResponseEntity<List<User>>(userServiceImpl.findAllUser(),HttpStatus.OK);
	} catch (Exception e) {
		@SuppressWarnings("unused")
		String messenger ="error:"+e.getMessage();
		return new ResponseEntity<List<User>>(HttpStatus.BAD_REQUEST);
	}
}
@GetMapping("/delete")
public ResponseEntity<Boolean> deleteUserById(Model model,@RequestParam (name = "id") String id) {
	try {
		System.out.println(id);
		long idU =Long.parseLong(id);
		boolean success=true;
		userServiceImpl.deleteUserById(idU);
		return new ResponseEntity<Boolean>(success,HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
	}
	
	 
}
@GetMapping("/activity")
public ResponseEntity<Boolean> activityUser(@RequestParam (name = "id") String id,@RequestParam(name  = "activity") String activity)  {
	long idU =Long.parseLong(id);
	boolean success=false;
if (activity.equals("true")) {
	userServiceImpl.activityUserById(idU,false);
	success=true;
}else {
	userServiceImpl.activityUserById(idU,true);
	success=true;
}
return new ResponseEntity<Boolean>(success,HttpStatus.OK);	
}
@GetMapping("/set/role")
public ResponseEntity<Boolean> seUserRoleByRole(@RequestParam (name = "idU") String id,@RequestParam(name = "roles") String role){
	long idU =Long.parseLong(id);
	boolean success=false;
	if(role.equals("true")) {
		success=true;
		userServiceImpl.setUserRole(idU,"ROLE_ADMIN");
	}else {
		userServiceImpl.setUserRole(idU,"ROLE_USER");
		success=true;
	}
	return new ResponseEntity<Boolean>(success,HttpStatus.OK);	
}
}
