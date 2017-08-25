package net.bitnine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
