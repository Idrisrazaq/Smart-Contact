package com.jsp.SmartContact.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.security.Principal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jsp.SmartContact.entity.Contact;
import com.jsp.SmartContact.entity.User;
import com.jsp.SmartContact.repository.ContactRepository;
import com.jsp.SmartContact.repository.UserRepository;

class UserControllerTest {
	
	@Autowired
	private UserRepository usrRepository;
	@Autowired
	private ContactRepository contactRepository;
	
	
//	@Test
//	public void testContactList(Principal principal) {
//		
//		String userEmail=principal.getName();
//		
//		User findByUserName = usrRepository.findByUserName(userEmail);
//		
//		List<Contact> list = contactRepository.findAllContactsByUserId(findByUserName.getUserId());
//		
//		for(Contact c:list) {
//			System.out.println(c);
//		}
//		
//	}

}
