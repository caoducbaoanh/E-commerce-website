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

import com.fasterxml.jackson.core.sym.Name;
import com.sun.net.httpserver.Authenticator.Success;

import cdw.hk2.shop.laptop.model.Notify;
import cdw.hk2.shop.laptop.model.User;
import cdw.hk2.shop.laptop.security.AuthenticationFacade;
import cdw.hk2.shop.laptop.services.NotifyServices;
import cdw.hk2.shop.laptop.services.UserServiceImpl;
import cdw.hk2.shop.laptop.utils.TimeUtlis;

@Controller
@RequestMapping("/admin/manage/notify")
public class AdNotifyController {
	@Autowired
	private NotifyServices notifyServices;
	@Autowired
	private AuthenticationFacade authentication;
	@Autowired
	private UserServiceImpl userServive;
	@Autowired
	private TimeUtlis time;
	@GetMapping("/list")
	public String manageUser(Model model) {
		String title="Quản lý thông báo";
		model.addAttribute("title",title);
		Notify notify= new Notify();
		model.addAttribute("notifys", notify);
		List<User> list= userServive.findAllUser();
		model.addAttribute("listUser", list);
		return "/admin/manage_notifys";
	}
	@RequestMapping(value = "/get/list",method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Notify>>  getListNotify(){
		try {
			return new ResponseEntity<List<Notify>>(notifyServices.getAllList(),HttpStatus.OK);
		} catch (Exception e) {
			@SuppressWarnings("unused")
			String messenger ="error:"+e.getMessage();
			return new ResponseEntity<List<Notify>>(HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/delete")
	public ResponseEntity<Boolean> deleteUserById(Model model,@RequestParam (name = "id") String id) {
		try {
			long idU =Long.parseLong(id);
			boolean success=true;
			notifyServices.deleteNotifyById(idU);
			return new ResponseEntity<Boolean>(success,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
		}
		
		 
	}
	@GetMapping("send")
	public ResponseEntity<Boolean> sendNotify(Model model,@RequestParam(name = "content") String content ,@RequestParam(name = "theme") String theme,@RequestParam(name = "user") String user){
		boolean success=true;
		System.out.println(user);
		Notify notify = new Notify();notify.setContent(content);notify.setTime(time.convertToDateViaSqlTimestamp());notify.setChecks(false);notify.setKeyword(theme);
		try {
				notifyServices.sendNotify(notify, user);
			return new ResponseEntity<Boolean>(success,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(success,HttpStatus.BAD_REQUEST);
		}
	}
}
