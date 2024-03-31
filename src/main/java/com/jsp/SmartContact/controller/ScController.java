package com.jsp.SmartContact.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsp.SmartContact.entity.Message;
import com.jsp.SmartContact.entity.User;
import com.jsp.SmartContact.service.UserService;
import com.jsp.SmartContact.utility.Mail;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ScController {
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute User user,BindingResult result,Model model) {
		try {
			if(result.hasErrors()) {
				System.out.println(result.toString());
				model.addAttribute("user", user);
				return "signup";
			}
			user.setUserRole("USER_ROLE");
			user.setEnabled(true);
			user.setUserPassword(bcryptPasswordEncoder.encode(user.getUserPassword()));
			
			System.out.println(user);
			
			userService.saveUser(user);
	        	
			model.addAttribute("user", new User());
			model.addAttribute("message", new Message("successfully registered", "alert-success"));
//			session.setAttribute("message", new Message("successfully registered", "alert-success"));
			
		}catch(Exception e) {
			
			
			model.addAttribute("user", user);
			model.addAttribute("message",new Message("something went wrong", "alert-danger"));
//			session.setAttribute("message", new Message("something went wrong"+e.getMessage(), "alert-danger"));
			return "signup";
		}
		
		return "signup";
//		return model;
	}
	
	@GetMapping("/login")
	public String customLogin(Model model) {
		
		return "login";
	}
	
//	@PostMapping("/logout")
//	public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
//	    // .. perform logout
//	    this.logoutHandler.doLogout(request, response, authentication);
//	    return "redirect:/home";
//	}
	
	SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
	
	@GetMapping("/logout")
	public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response,Model model) {
	    // .. perform logout
	    this.logoutHandler.logout(request, response, authentication);
	    System.out.println(authentication.getName()+" is logged out");
	    model.addAttribute("message",new Message("You have been logged out", "alert-success"));
	    return "login";
	}
	
	@GetMapping("/forgotpassword")
	public String forgotPassword() {
		return "forgotpassword";
	}
	
	@PostMapping("/sendotp")
	public String sendOtpToMail(@RequestParam("username") String username,Model model,HttpSession session) {
		
		User user = userService.findByUserName(username);
		
		if(user==null) {
			model.addAttribute("notfound", new Message("Invalid User", "alert-danger"));
			return "forgotpassword";
		}
		model.addAttribute("found", new Message("OTP sent to your mail", "alert-success"));
		
		Random random=new Random();
		int rd=random.nextInt(100000,999999);
		System.out.println(rd);
		
		Mail mail=new Mail();
		
		mail.sendMail(rd,username,mailSender);
		
		session.setAttribute("otp", rd);
		session.setAttribute("username", username);
		
		return "verifyotp";
	}
	
	@Autowired
	private JavaMailSender mailSender;
	
	@PostMapping("/validateotp")
	public String verfiyOTP(@RequestParam("username") String otp,HttpSession session,Model model) {
		int oldOTP = (int)session.getAttribute("otp");
		int enteredOTP=Integer.parseInt(otp);
		if(oldOTP==enteredOTP) {
			model.addAttribute("username", session.getAttribute("username"));
			return "resetpassword";
		}
		session.invalidate();
		model.addAttribute("message", new Message("OTP does not match", "alert-danger"));
		return "verifyotp";
	}
	
	@PostMapping("/resetpassword")
	public String resetPassword(@RequestParam("username") String username,@RequestParam("password") String password,Model model,HttpSession session) {
		
		User user = userService.findByUserName(username);
		user.setUserPassword(bcryptPasswordEncoder.encode(password));
		
		User saveUser = userService.saveUser(user);
		if(saveUser==null) {
			return "resetpassword";
		}
		
		session.removeAttribute("username");
		session.removeAttribute("otp");
		
		model.addAttribute("message", new Message("Password Reset successfully", "alert-success"));
		System.out.println(username+" "+password);
		return "resetpassword";
	}
}
