package com.example.personalblog.DTO;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


public class CustomUserDetails extends User{
	

	public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String FullName) {
		super(username, password, authorities);
		//TODO Auto-generated constructor stub
		this.FullName = FullName;
	}
	
	String FullName;
	
	

}
