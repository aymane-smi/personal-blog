package com.example.personalblog.Filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


public class CustomAuthorizationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException{
		if(req.getServletPath().equals("/api/auth/"))
			chain.doFilter(req, res);
		else {
			String AuthHeader = req.getHeader("Authorization");
			if(AuthHeader != null && AuthHeader.startsWith("Bearer ")) {
				try {
					String token = AuthHeader.substring(("Bearer ").length());
					Algorithm algorithm = Algorithm.HMAC256("SECRET".getBytes());
					JWTVerifier verify = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verify.verify(token);
					String subject = decodedJWT.getSubject();
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subject, "", new ArrayList<>());
					SecurityContextHolder.getContext().setAuthentication(auth);
					chain.doFilter(req, res);
				}catch(Exception e) {
					res.setHeader("error", e.getMessage());
					res.setStatus(FORBIDDEN.value());
					Map<String, String> error = new HashMap<>();
					error.put("message", e.getMessage());
					res.setContentType(APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(res.getOutputStream(), error);
				}
			}else {
				chain.doFilter(req, res);
			}
		}
	}

}
