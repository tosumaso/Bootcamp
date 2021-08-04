package com.example.demo.auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.SecurityUser;

public class ApplicationUser implements UserDetails{ //UserDetails: ユーザー情報を定義するインターフェース
	
	private final String password;
	private final String userName;
	private final List<? extends GrantedAuthority> grandtedAuthorities;
	private final boolean active;
	
	

	public ApplicationUser(SecurityUser securityUser) {
		this.password = securityUser.getPassword();
		this.userName = securityUser.getUserName();
		this.active = securityUser.isActive();
		this.grandtedAuthorities = Arrays.stream(securityUser.getRoles().split(","))
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return grandtedAuthorities;
	}

	@Override
	public String getPassword() {
	
		return password;
	}

	@Override
	public String getUsername() {
		
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return active;
	}

}
