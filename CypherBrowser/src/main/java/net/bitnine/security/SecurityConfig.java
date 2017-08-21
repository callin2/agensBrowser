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

    @Autowired private LoginSuccessHandler loginSuccessHandler;


	@Override
	protected void configure(HttpSecurity http) throws Exception {

	    http.csrf().disable();
	    
        http.authorizeRequests().antMatchers("/h2console/**").permitAll();
        
        http.authorizeRequests().antMatchers("/guest/**").permitAll();
        
        http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");
        
        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");

        http.headers().frameOptions().disable();
        

		
		http.authorizeRequests().antMatchers("/api/v1/db/login").permitAll();

        http.authorizeRequests().antMatchers("/api/v1/db/query").hasRole("USER");
        
        http.authorizeRequests().antMatchers("/api/v1/db/admin").hasRole("ADMIN");

        http.formLogin().loginPage("/login").successHandler(authenticationSuccessHandler);
//        http.formLogin().loginPage("/login").successHandler(loginSuccessHandler);
//        http.formLogin().loginPage("/login").successHandler(successHandler());

        //http.formLogin().loginPage("/api/v1/db/testLogin");
		
		

        
        http.exceptionHandling().accessDeniedPage("/accessDenied");
        
        http.logout().logoutUrl("/logout").invalidateHttpSession(true);
        
        http.userDetailsService(securityUserService);
	}
    
    /*@Bean
    public AuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new RESTAuthenticationSuccessHandler();
        handler.setUseReferer(true);
        return handler;
    }*/
}


























