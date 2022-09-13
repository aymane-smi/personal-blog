package com.example.personalblog.Controllers;


import java.util.HashMap;
import java.util.Map;

import com.example.personalblog.DTO.UserDTO;
import com.example.personalblog.Services.UserServices.UserServicesImpl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
	
	private final UserServicesImpl userServices;
	
	@PostMapping("/edit")
	public Map<String, String> editUser(@RequestBody UserDTO user){
		log.info("{}", user);
		Map<String, String> response = new HashMap();
		if(user.getUsername().equals(""))
			throw new UsernameNotFoundException("the username does'nt exist in the request body"); 
		userServices.editUser(user.getUsername(), user.getFullname(), user.getNewUsername(), user.getNewPassword());
		return response;
	}

}