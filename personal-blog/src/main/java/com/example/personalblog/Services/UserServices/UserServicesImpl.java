package com.example.personalblog.Services.UserServices;

import java.util.ArrayList;

import javax.transaction.Transactional;

import com.example.personalblog.DTO.CustomUserDetails;
import com.example.personalblog.Models.User;
import com.example.personalblog.Repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServicesImpl implements UserDetailsService{
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		User user = userRepository.findByUsername(username);
		log.info("user '{}' found with password {}", user.getUsername(), user.getPassword());
		CustomUserDetails customUser = new CustomUserDetails(user.getUsername(), user.getPassword(), new ArrayList<>(), user.getFullName());
		log.info("{}", customUser);
		return customUser;
	}
	
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public User getUser(String username) {
		return userRepository.findByUsername(username);
	}
	

}
