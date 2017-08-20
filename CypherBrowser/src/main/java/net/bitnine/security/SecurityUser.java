package net.bitnine.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import net.bitnine.domain.Member;
import net.bitnine.domain.MemberRole;

public class SecurityUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String ROLE_PREFIX = "ROLE_";
	
	private Member member;
	
	public SecurityUser (Member member) {
		
		super (member.getUsername(), member.getPassword(), makeGrantedAuthority(member.getRole()));
		this.member = member;
	}

	private static Collection<? extends GrantedAuthority> makeGrantedAuthority(MemberRole role) {
		List<GrantedAuthority> list = new ArrayList<>();
		
		list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRole()));
		
		return list;
	}

}
