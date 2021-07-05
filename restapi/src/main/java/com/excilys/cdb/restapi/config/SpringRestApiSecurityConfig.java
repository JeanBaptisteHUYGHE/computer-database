package com.excilys.cdb.restapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.excilys.cdb.restapi.security.JWTAuthenticationFilter;
import com.excilys.cdb.restapi.security.JWTAuthorizationFilter;
import com.excilys.cdb.service.security.AuthenticationService;

@Component
@Configuration("SpringSecurityConfig")
@ComponentScan({
	"com.excilys.cdb.restapi", 
	"com.excilys.cdb.service"
	})
@EnableWebSecurity
public class SpringRestApiSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private Logger logger;
	private AuthenticationService authenticationService;
	
	public SpringRestApiSecurityConfig(AuthenticationService authenticationService) {
		logger = LoggerFactory.getLogger(SpringRestApiSecurityConfig.class);
		this.authenticationService = authenticationService;
	}
	
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		logger.debug("SpringSecurityConfig.configure(): authentication");
		auth.userDetailsService(authenticationService);
	}
	    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.debug("SpringSecurityConfig.configure(): http");
		
		http.cors();
		
		http.csrf()
			.disable();
		
		http.authorizeRequests()
			.mvcMatchers("/addComputer").hasAuthority("admin")
			.mvcMatchers("/login", "/logout", "/user/login").permitAll()
			.anyRequest().authenticated();
		
		http.addFilter(new JWTAuthenticationFilter(authenticationManager()))
			.addFilter(new JWTAuthorizationFilter(authenticationManager()))
            // this disables session creation on Spring Security
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}
	
	@Bean 
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}
