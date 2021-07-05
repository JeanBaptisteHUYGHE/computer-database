package com.excilys.cdb.restapi.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.excilys.cdb.restapi.dto.UserRDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        
        this.setFilterProcessesUrl(SecurityConstants.SIGN_UP_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            UserRDto userRDto = new ObjectMapper()
                    .readValue(req.getInputStream(), UserRDto.class);
            
            System.out.println("attemptAuthentication(), inputStream: " + req.get);
            System.out.println("attemptAuthentication(), userRDto: " + userRDto);
            
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRDto.getRole());

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                    		userRDto.getLogin(),
                    		userRDto.getPassword(),
                            Arrays.asList(authority))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        String token = JWT.create()
                .withSubject(((UserRDto) auth.getPrincipal()).getLogin())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));

        String body = ((UserRDto) auth.getPrincipal()).getLogin() + " " + token;

        res.getWriter().write(body);
        res.getWriter().flush();
    }
}
