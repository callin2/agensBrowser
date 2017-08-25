package net.bitnine.security;

import net.bitnine.persistence.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * jdbc 인증을 사용하는 서비스.  출처 : 스타트 스프링 부트
 * @author lionkim
 *
 */
@Service
public class SecurityUserService implements UserDetailsService {
	
	@Autowired MemberRepository repository;

	// 사용자의 이름으로 DB를 검색하여 반환된 Member 정보를 사용하여 UserDetails를 반환.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return 
				repository.findById(username)
				.filter(m -> m != null)
				.map(m -> new SecurityUser(m)).get();
	}

}
