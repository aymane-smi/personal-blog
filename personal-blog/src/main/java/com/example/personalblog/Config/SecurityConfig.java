package com.example.personalblog.Config;

import javax.servlet.Filter;

import com.example.personalblog.Filters.CustomAuthentificationFilter;
import com.example.personalblog.Filters.CustomAuthorizationFilter;
import com.example.personalblog.Services.UserServices.UserServicesImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final UserServicesImpl userServicesImpl;
	private final BCryptPasswordEncoder bcryptPasswordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		System.out.println("configure 1");
		auth.userDetailsService(userServicesImpl).passwordEncoder(bcryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		System.out.println("configure 2");
		CustomAuthentificationFilter authFilter = new CustomAuthentificationFilter(AuthenticationManagerBean());
		authFilter.setFilterProcessesUrl("/api/auth/login");
		http.csrf().disable().authorizeRequests()
							 .antMatchers(HttpMethod.POST, "/api/auth/login")
							 .permitAll()
							 .anyRequest().authenticated()
							 .and()
							 .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
							 .addFilter(authFilter);
		
	}
	
	@Bean
	public AuthenticationManager AuthenticationManagerBean() throws Exception{
		return super.authenticationManager();
	}
}
