package net.bitnine.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@GetMapping("/login")
	public void login() {

	}
	
	/*@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(HttpServletRequest request) {
	    String referrer = request.getHeader("Referer");
	    request.getSession().setAttribute("prevPage", referrer);
	    return "login";
	}*/

	@GetMapping("/accessDenied")
	public void accessDenied() {

	}

	@GetMapping("/logout")
	public void logout() {

	}
}
