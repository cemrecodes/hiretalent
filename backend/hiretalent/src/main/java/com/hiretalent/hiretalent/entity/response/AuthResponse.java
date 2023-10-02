package com.hiretalent.hiretalent.entity.response;

import lombok.Data;

@Data
public class AuthResponse {
	
	String message;
	Long userId;
	String role;
}