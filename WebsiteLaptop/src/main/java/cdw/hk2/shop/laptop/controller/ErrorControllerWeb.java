package cdw.hk2.shop.laptop.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cdw.hk2.shop.laptop.model.Cart;

@Controller
public class ErrorControllerWeb implements ErrorController{
	  @RequestMapping("/error")
	public String handleError(Model model,HttpServletRequest request) {
		String error="";
		String codeError="";
		HttpSession session = request.getSession();
		session.setAttribute("cart",new Cart());
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				error="Lỗi 404 Not Found là  bạn cố gắng truy cập vào một trang web (đường link ) đã không còn tồn tại.";
				model.addAttribute("msgerror",error);
				codeError="404";
				model.addAttribute("codeError",codeError);
				return "error";
			}
			else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				error="Lỗi 500 Internal Server Error:Tạm thời không truy cập được trang web";
				model.addAttribute("msgerror",error);
				codeError="500";

				model.addAttribute("codeError",codeError);
				return "error";
			}
			else if(statusCode==HttpStatus.METHOD_NOT_ALLOWED.value()) {
				error="HTTP Error 405 Method Not Allowed:Request method không được hỗ trợ cho các tài nguyên được yêu cầu";
				model.addAttribute("msgerror",error);
				codeError="405 ";
				model.addAttribute("codeError",codeError);
				return "error";
			}else if(statusCode==HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()) {
				error="HTTP 415 Unsupported Media Type:Server sẽ không chấp nhận Request, bởi vì kiểu phương tiện không được hỗ trợ.";
				model.addAttribute("msgerror",error);
				codeError="415 ";
				model.addAttribute("codeError",codeError);
				return "error";
				
			}
		}
		return "error";
	}
	
}
