package com.example.demo.controllers;

import java.time.LocalDate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.persistencia.clases.DAO.SugerenciaDAOHibernateJPA;
import com.example.demo.persistencia.clases.entidades.Sugerencia;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.PersistenceException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sugerencias/")
@Tag(name="Sugerencias", description="CRUD de sugerencias de los usuarios")
public class SugerenciaController {
	
	@Autowired
	private SugerenciaDAOHibernateJPA sugerenciaDAO;
	
	
	@PostMapping()
	@Operation(summary="Crear una sugerencia")
	public ResponseEntity<Sugerencia> createSugerencia(@Valid @RequestBody Sugerencia sugerencia){
		try {
			Sugerencia sugerenciaPersistida = sugerenciaDAO.persist(sugerencia);
			return new ResponseEntity<>(sugerenciaPersistida, HttpStatus.CREATED);
		}catch(PersistenceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PutMapping()
	@Operation(summary="Actualizar una sugerencia")
	public ResponseEntity<Sugerencia> updateUsuario(@Valid @RequestBody Sugerencia sugerencia){
		try {
			sugerenciaDAO.update(sugerencia);
			return new ResponseEntity<Sugerencia>(sugerencia, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping("{id}")
	@Operation(summary="Eliminar una sugerencia por su Id")
	public ResponseEntity<Sugerencia> deleteSugerenciaById(@PathVariable long id){
		try {
			sugerenciaDAO.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Sugerencia>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping()
	@Operation(summary="Eliminar una sugerencia")
	public ResponseEntity<Sugerencia> deleteSugerencia(@RequestBody Sugerencia sugerencia){
		try {
			sugerenciaDAO.delete(sugerencia);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Sugerencia>(HttpStatus.NO_CONTENT);	
		}
	}	
	
	
	@GetMapping()
	@Operation(summary="Recuperar todas las sugerencias")
	public ResponseEntity<List<Sugerencia>> getSugerencia(){
		try {
			List<Sugerencia> sugerencias = sugerenciaDAO.findAll();
			return new ResponseEntity<List<Sugerencia>>(sugerencias, HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<List<Sugerencia>>(HttpStatus.NO_CONTENT);
		}
	}	
	
	@GetMapping("{id}")
	@Operation(summary="Recuperar una sugerencia por su Id")
	public ResponseEntity<Sugerencia> getSugerenciaById(@PathVariable long id){
		try {
			Sugerencia sugerencia = sugerenciaDAO.findById(id);
			if(sugerencia == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<Sugerencia>(sugerencia, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("/de-una-fecha/{date}/{max}")
	@Operation(summary="Recuperar todas las sugerencias de una fecha")
	public ResponseEntity<List<Sugerencia>> getSugerenciasByDate(@PathVariable LocalDate date, @PathVariable  int max){
		try {
			if(max < 1)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			List<Sugerencia> sugerencias = sugerenciaDAO.findByDate(date, max);
			return new ResponseEntity<List<Sugerencia>>(sugerencias, HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<List<Sugerencia>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("ordenadas-por-fecha/{orden}")
	@Operation(summary="Recuperar todas las sugerencias ordenadas por fecha ('ascendente' o 'descendente')")
	public ResponseEntity<List<Sugerencia>> getSugerenciasOrdereredByDate(@PathVariable String orden){
	    try {
	        List<Sugerencia> sugerencias;

	        if ("ascendente".equalsIgnoreCase(orden)) {
	            sugerencias = sugerenciaDAO.findAllOrderedByDateAsc();
	        } else if ("descendente".equalsIgnoreCase(orden)) {
	            sugerencias = sugerenciaDAO.findAllOrderedByDateDesc();
	        } else {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }

	        return new ResponseEntity<>(sugerencias, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	}
	
	

}
