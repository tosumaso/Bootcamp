package com.example.demo.Config;

public enum ApplicationUserPermission {

	STUDENTS_READ("student:read"),
	STUDENTS_WRITE("students:write"),
	COURSE_READ("course:read"),
	COURSE_WRITE("course:write");
	
	private final String permission;

	private ApplicationUserPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
	
}
