package com.hiretalent.hiretalent.entity.response;

import lombok.Data;

@Data
public class LinkedinAuthResponse {
	private String access_token;
	private String expires_in;
	private String refresh_token;
	private String refresh_token_expires_in;
	private String scope;
}
