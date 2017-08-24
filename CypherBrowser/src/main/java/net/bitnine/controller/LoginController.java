package net.bitnine.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 로그인 컨트롤러
 * @author 김형우
 *
 */
@Controller
public class LoginController {

	@GetMapping("/login")
	public void login() {

	}

	@GetMapping("/accessDenied")
	public void accessDenied() {

	}

	@GetMapping("/logout")
	public void logout() {

	}
}
