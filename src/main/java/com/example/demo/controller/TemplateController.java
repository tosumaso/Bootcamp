package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

	@GetMapping("login")
	public String getLogin() {
		return "SecurityPractice/login";
	}
	
	@GetMapping("courses")
	public String getCourses() {
		return "SecurityPractice/courses";
	}
	
	@GetMapping("newUser")
	public String getNewUserPage() {
		return "SecurityPractice/newUser"; 
	}
}
