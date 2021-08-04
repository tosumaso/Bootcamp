package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Comment;
import com.example.demo.entity.User;

public interface CommentRepository extends JpaRepository<Comment,Integer>{

	List<Comment>findByUser(User user);
}
