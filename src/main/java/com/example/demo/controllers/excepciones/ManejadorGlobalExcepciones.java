package com.example.demo.controllers.excepciones;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ManejadorGlobalExcepciones {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> manejarValidaciones(MethodArgumentNotValidException ex) {
		Map<String,String> errores = new  HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) ->
				errores.put(((FieldError) error).getField(), error.getDefaultMessage()));
		return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
	}
	
}
