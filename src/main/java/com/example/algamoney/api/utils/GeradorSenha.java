package com.example.algamoney.api.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenha {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("caa1.123"));
		String xx = "$2a$10$yzp5qnh.L4XUNOxb0KZiLOuzk2PDM7QHe971C0UcrZGtXHUiWj562";
		String senha = "senha.123";
		if (encoder.matches(senha, xx)) {
			System.out.println("positivo");
		} else {
			System.out.println("negativo");
		}
		System.out.println(encoder.encode("caa1@test.com.123"));
		System.out.println(encoder.encode("caa1@test.com"));
		System.out.println(encoder.encode("pw: senha.123"));
		System.out.println(encoder.encode("senha.123"));
		System.out.println(encoder.encode("admin"));
//		$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.
		
//		Map<String, PasswordEncoder> encoders = new HashMap<>();
//        encoders.put("bcrypt", new BCryptPasswordEncoder());
//        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
//        passwordEncoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder());
//        System.err.println(passwordEncoder.encode("admin"));
	}
	
}
