package com.example.personalblog.Services.UserServices;


import com.example.personalblog.Models.User;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServices extends UserDetailsService{
	User save(User user);
	User getUser(String username);
}
