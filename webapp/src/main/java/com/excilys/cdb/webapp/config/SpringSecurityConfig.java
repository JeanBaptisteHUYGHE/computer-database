package com.excilys.cdb.webapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.excilys.cdb.service.security.AuthenticationService;
import com.excilys.cdb.webapp.security.LoginFailureHandler;

@Component
@Configuration("SpringSecurityConfig")
@ImportResource("classpath:applicationContext.xml") // = @ComponentScan
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private Logger logger;
	private AuthenticationService authenticationService;
	private LoginFailureHandler loginFailureHandler;
	
	public SpringSecurityConfig(AuthenticationService authenticationService, LoginFailureHandler loginFailureHandler) {
		logger = LoggerFactory.getLogger(SpringSecurityConfig.class);
		this.authenticationService = authenticationService;
		this.loginFailureHandler = loginFailureHandler;
	}
	
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		logger.debug("SpringSecurityConfig.configure(): authentication");
		auth.userDetailsService(authenticationService);
	}
	    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.debug("SpringSecurityConfig.configure(): http");
				
		http.authorizeRequests()
			//.mvcMatchers("/addComputer").hasAuthority("admin")
			.mvcMatchers("/login", "/logout", "/static/**").permitAll()
			.anyRequest().authenticated();
		
		http.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/dashboard", true)
			.failureUrl("/login?error=true")
			.failureHandler(loginFailureHandler);

		http.logout()
			.logoutUrl("/logout")
			.deleteCookies("JSESSIONID")
            .logoutSuccessUrl("/login")
            .invalidateHttpSession(true);			
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

}
