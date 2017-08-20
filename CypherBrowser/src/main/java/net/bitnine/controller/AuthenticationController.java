package net.bitnine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.bitnine.domain.Member;
import net.bitnine.persistence.MemberRepository;

@Controller
@RequestMapping(value="/api/v1/db/auth/")
public class AuthenticationController {

	@Autowired private MemberRepository repository;
	
    @RequestMapping(value="/h2", method=RequestMethod.GET)
    public String loginGet() {
        return "h2";
    }

    @RequestMapping(value="/h2", method=RequestMethod.POST)
    public String loginPost() {
        return "h2";
    }

    @RequestMapping(value="/all", method=RequestMethod.GET)
    public String getAuthentication() {

		/*List<Member> userList =  repository.findAll();
		
		for (Member user : userList) {
			System.out.println("user: " + user);
		}*/
		
		return "h2";
    }
}
