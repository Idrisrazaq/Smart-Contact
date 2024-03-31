package com.jsp.SmartContact.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.jsp.SmartContact.entity.Contact;
import com.jsp.SmartContact.repository.ContactRepository;

public class ContactService {
	
	@Autowired
	private ContactRepository contactRepository;
	
	public void addContact(Contact contact) {
		contactRepository.save(contact);
	}
	
}
