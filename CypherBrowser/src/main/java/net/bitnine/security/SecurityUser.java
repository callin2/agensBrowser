package net.bitnine.security;

import net.bitnine.domain.Member;
import net.bitnine.domain.MemberRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * DB에 저장된 Member 정보를 사용하여 userdetails 구현한 User를 생성.  출처 : 스타트 스프링 부트
 * @author lionkim
 *
 */
public class SecurityUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String ROLE_PREFIX = "ROLE_";
	
	private Member member;
	
	public SecurityUser (Member member) {
		
		super (member.getUsername(), member.getPassword(), makeGrantedAuthority(member.getRole()));   // username, password, 권한을 super class 생성자로 전달.
		this.member = member;
	}

	// 권한들을 반환하는 메소드
	private static Collection<? extends GrantedAuthority> makeGrantedAuthority(MemberRole role) {
		List<GrantedAuthority> list = new ArrayList<>();
		
		list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRole()));       // "ROLE_" prefix를 더하여 권한 list에 저장.
		
		return list;
	}

}
