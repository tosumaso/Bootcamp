package com.example.demo.auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.SecurityUser;

public interface ApplicationUserDao extends JpaRepository<SecurityUser,Integer>{
	Optional<SecurityUser> findByUserName(String userName);

}
