package com.example.personalblog.Filters;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.personalblog.PersonalBlogContext;
import com.example.personalblog.DTO.CustomUserDetails;
import com.example.personalblog.DTO.UserSignIn;
import com.example.personalblog.Services.UserServices.UserServicesImpl;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthentificationFilter extends UsernamePasswordAuthenticationFilter{

	private final AuthenticationManager authenticationManager;
	
	
	public CustomAuthentificationFilter(AuthenticationManager auth) {
		this.authenticationManager = auth;
	}
	
	@Override
	
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException{
		try {
			UserSignIn userCred = new ObjectMapper().readValue(req.getInputStream(), UserSignIn.class);
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userCred.getUsername(), userCred.getPassword());
			return authenticationManager.authenticate(authToken);
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain filter, Authentication auth) throws ServletException, IOException{
		CustomUserDetails user = (CustomUserDetails)auth.getPrincipal();
		UserServicesImpl userServices = (UserServicesImpl) PersonalBlogContext.getBean("userServicesImpl");
		com.example.personalblog.Models.User user_ = userServices.getUser(user.getUsername()); 
		Algorithm algorithm = Algorithm.HMAC256("SECRET".getBytes());
		String access_token = JWT.create().withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis()+ 60 * 1000 * 60))
				                                                          .withIssuer(req.getRequestURL().toString())
				                                                          .sign(algorithm);
		Map<String, Object> response = new HashMap<>();
		response.put("message", "user logged in successfully");
		response.put("user", user_);
		response.put("token", access_token);
		res.setContentType(APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(res.getOutputStream(), response);
		
	}
	
	
	
}
