package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Artist;
import com.example.demo.entity.Comment;
import com.example.demo.entity.User;
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class ReferenceController {

	@Autowired
	CommentRepository commentrepository;
	
	@Autowired
	UserRepository userrepository;
	
	@Autowired
	ArtistRepository artistrepository;
	
	@GetMapping("/test0629") //comment(many)からuser(one)を取得
	public String get0629(Model model) {
		List<Comment> comments=commentrepository.findAll();
		model.addAttribute("comments", comments);
		return "lesson05_01/onetomany";
	}
	
	@GetMapping("/practice0629") //user(one)からcomment(many)を取得
	public String get0630(Model model) {
		List<User>users = userrepository.findAll();
		model.addAttribute("users", users);
		return "lesson05_01/onetomany";
	}
		
	@GetMapping("/test0630")
	public String test0630(Model model) {
		List<Artist> artists = artistrepository.findAll();
		model.addAttribute("artists", artists);
		return "lesson05_01/manytomany";
	}
	
	@GetMapping("/loginForm")
	public String getLoginForm() {
		return "Practice0626/login";
	}
	
	
	
}
