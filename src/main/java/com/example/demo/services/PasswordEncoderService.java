package com.example.demo.services;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderService implements PasswordEncoder {

	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Override
	public String encode(CharSequence original) {
		return encoder.encode(original);
	}

	@Override
	public boolean matches(CharSequence from, String to) {
		return encoder.matches(from, to);
	}
}
