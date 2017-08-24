package net.bitnine.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.java.Log;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Log
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired SecurityUserService securityUserService;

    @Autowired private RESTAuthenticationSuccessHandler authenticationSuccessHandler;


	@Override
	protected void configure(HttpSecurity http) throws Exception {

	    http.csrf().disable();
	        
        http.authorizeRequests().antMatchers("/h2console/**").permitAll()
        .and()
            .headers().frameOptions().disable()
        .and()
            .authorizeRequests().antMatchers("/api/v1/db/login").permitAll()            // login permit all
		    .antMatchers("/api/v1/db/query").hasAnyRole("USER", "ADMIN")            //  권한 USER, AMDIN 만 접근
            .antMatchers("/api/v1/db/admin").hasRole("ADMIN")                           //  권한 AMDIN 만 접근
        .and()
            .formLogin().loginPage("/login").successHandler(authenticationSuccessHandler)       // 권한이 없을 경우 /login 으로 이동. 인증 성공시 authenticationSuccessHandler 핸들러 실행. 여기서 토큰 발급
        .and()
            .exceptionHandling().accessDeniedPage("/accessDenied")                  // 인증 후 권한이 없는 페이지 접근시
        .and()
            .logout().logoutUrl("/logout").invalidateHttpSession(true)
        .and()
            .userDetailsService(securityUserService);
	}
    
    /*@Bean
    public AuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new RESTAuthenticationSuccessHandler();
        handler.setUseReferer(true);
        return handler;
    }*/
}


























