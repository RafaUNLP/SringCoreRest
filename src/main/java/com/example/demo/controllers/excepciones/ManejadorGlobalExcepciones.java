package com.example.demo.controllers.excepciones;

import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
public class ManejadorGlobalExcepciones {

	public void prueba() {
		Exception e = new Exception("hola");
		e.getMessage(); //anda :D
	}
}
