package com.example.demo.controllers.excepciones;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.LazyInitializationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ManejadorGlobalExcepciones {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> manejarValidacionesDatosRecibidos(MethodArgumentNotValidException ex) {
		Map<String,String> errores = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) ->
				errores.put(((FieldError) error).getField(), error.getDefaultMessage()));
		return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, String>> manejarIntegridadDeDatos(DataIntegrityViolationException ex) {
	    Map<String, String> errores = new HashMap<>();
	    String mensaje = ex.getMessage();
	    System.out.println("MENSAJE DE ERROR: " + mensaje);
	    if (mensaje.contains("duplicate")) {
	    	if(mensaje.contains("(dni)"))
	            errores.put("dni", "El dni proporcionado ya está registrado");
	        if (mensaje.contains("(email)"))
	            errores.put("email", "El correo electrónico proporcionado ya está registrado");
	    } else {
	        errores.put("error", "Error inesperado en la base de datos: " + mensaje);
	    }
	    
	    return new ResponseEntity<>(errores, HttpStatus.CONFLICT);
	}

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, String>> manejarTipoContenidoNoSoportado(HttpMediaTypeNotSupportedException ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("error", "Tipo de contenido no soportado: " + ex.getContentType());
        return new ResponseEntity<>(errores, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> manejarCuerpoNoReadable(HttpMessageNotReadableException ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("error", "No se puede leer el cuerpo de la solicitud: " + ex.getMessage());
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> manejarErrorDeValidacion(ConstraintViolationException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
            errores.put(violation.getPropertyPath().toString(), violation.getMessage())
        );
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> manejarEntidadNoEncontrada(EntityNotFoundException ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("error", "Entidad no encontrada: " + ex.getMessage());
        return new ResponseEntity<>(errores, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LazyInitializationException.class)
    public ResponseEntity<Map<String, String>> manejarLazyInitialization(LazyInitializationException ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("error", "Error de acceso a datos: " + ex.getMessage());
        return new ResponseEntity<>(errores, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
