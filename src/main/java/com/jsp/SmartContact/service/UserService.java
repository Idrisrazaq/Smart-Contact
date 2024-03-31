package com.jsp.SmartContact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.SmartContact.entity.User;
import com.jsp.SmartContact.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public User saveUser(User user) {
		return userRepository.save(user);
		
	}
	
	public User findByUserName(String userEmail) {
		return userRepository.findByUserName(userEmail);
	}
	
}
