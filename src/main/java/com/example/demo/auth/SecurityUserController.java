package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.SecurityUser;

@Controller
public class SecurityUserController {
	
	@Autowired
	ApplicationUserService userService;
	
	@ModelAttribute
	public UserRegistrationForm setForm() {
		return new UserRegistrationForm();
	}

	@PostMapping("/newUser")
	public String createNewUser(UserRegistrationForm form) {
		SecurityUser user = new SecurityUser();
		user.setUserName(form.getUserName());
		user.setActive(true);
		user.setRoles("ROLE_STUDENT");
		userService.createUser(user, form.getPassword());
		return "redirect:/login";
	}
}
