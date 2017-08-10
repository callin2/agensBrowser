package net.bitnine.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.extern.java.Log;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Log
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired UserService userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		log.info("security config..........................................................");
        
        http.authorizeRequests().antMatchers("/h2console/**").permitAll();
        
        http.authorizeRequests().antMatchers("/guest/**").permitAll();
        
        http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");
        
        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
        
        http.formLogin().loginPage("/login");
        
        http.exceptionHandling().accessDeniedPage("/accessDenied");
        
        http.logout().logoutUrl("/logout").invalidateHttpSession(true);
        
        http.userDetailsService(userService);
	}
	
	@Autowired
	public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication()
			.withUser("manager")
			.password("1111")
			.roles("MANAGER");
	}
	
	
}


























