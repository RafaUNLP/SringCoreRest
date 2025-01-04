package com.example.demo.services;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderService {

	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public String encode (String original){
		return encoder.encode(original);
	}
	
	public boolean match (String from, String to) {
		return encoder.matches(from, to);
	}
}
