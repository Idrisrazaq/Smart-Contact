package com.jsp.SmartContact.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jsp.SmartContact.entity.Contact;
import com.jsp.SmartContact.entity.Message;
import com.jsp.SmartContact.entity.User;
import com.jsp.SmartContact.repository.ContactRepository;
import com.jsp.SmartContact.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private Long uId;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@PreAuthorize("hasAuthority('USER_ROLE')")
	@GetMapping("/index")
	public String dashboard(Model model,Principal principal) {
		
		String userName=principal.getName();
//		System.out.println(userName);
		
		User user = userRepository.findByUserName(userName);
		uId=user.getUserId();
		System.out.println(user);
		
		model.addAttribute("user", user);
		
		return "normal/user_dashboard";
	}
	
	@GetMapping("/addcontact")
	public String addContact(Model model,Principal principal) {
		
		String name=principal.getName();
		
		User user=userRepository.findByUserName(name);
		
		//model.addAttribute("user", user);
		
		model.addAttribute("contact", new Contact());
		return "normal/addcontact";
	}
	
	@PostMapping("/registercontact")
	public String registerContact(@Valid @ModelAttribute Contact contact,BindingResult result,Model model,Principal principal) {
		
		try {
			String name=principal.getName();
			
			User user=userRepository.findByUserName(name);
			
			contact.setUser(user);
			
			user.getContactList().add(contact);
			
			System.out.println(contact);
			System.out.println(user);
			
			userRepository.save(user);
//			contactRepository.save(contact);
			model.addAttribute("message", new Message("successfully registered", "alert-success"));
			
			return "normal/addcontact";
		}catch(Exception e) {
			model.addAttribute("message", new Message("some problem occured", "alert-danger"));
			return "normal/addcontact";
		}
		
		
	}
	
	@GetMapping("/viewcontact/{page}")
	public String loadContacts(@PathVariable("page") Integer page,Model model,Principal principal) {
		
		String userEmail=principal.getName();
		
		User user=this.userRepository.findByUserName(userEmail);
		
		PageRequest pagable = PageRequest.of(page, 4);
		Page<Contact> findAllContactsByUserId = contactRepository.findAllContactsByUserId(user,pagable);
		
		
		
		model.addAttribute("contactlist", findAllContactsByUserId);
		model.addAttribute("currentpage", page);
		model.addAttribute("totalpages", findAllContactsByUserId.getTotalPages());
		//incomplete
		return "normal/showcontacts";
	}
	
}
