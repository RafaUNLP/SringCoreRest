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

import jakarta.persistence.PersistenceException;

@RestController
@RequestMapping("/api/sugerencia")
public class SugerenciaController {
	
	@Autowired
	private SugerenciaDAOHibernateJPA sugerenciaDAO;
	
	
	@PostMapping("/createSugerencia")
	public ResponseEntity<Sugerencia> createSugerencia(@RequestBody Sugerencia sugerencia){
		try {
			Sugerencia sugerenciaPersistida = sugerenciaDAO.persist(sugerencia);
			return new ResponseEntity<>(sugerenciaPersistida, HttpStatus.CREATED);
		}catch(PersistenceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PutMapping("/updateSugerencia")
	public ResponseEntity<Sugerencia> updateUsuario(@RequestBody Sugerencia sugerencia){
		try {
			sugerenciaDAO.update(sugerencia);
			return new ResponseEntity<Sugerencia>(sugerencia, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping("/deleteSugerenciaById")
	public ResponseEntity<Sugerencia> deleteSugerenciaById(@RequestBody long id){
		try {
			sugerenciaDAO.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Sugerencia>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping("/deleteSugerencia")
	public ResponseEntity<Sugerencia> deleteSugerencia(@RequestBody Sugerencia sugerencia){
		try {
			sugerenciaDAO.delete(sugerencia);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Sugerencia>(HttpStatus.NO_CONTENT);	
		}
	}	
	
	
	@GetMapping("/getSugerencias")
	public ResponseEntity<List<Sugerencia>> getSugerencia(){
		try {
			List<Sugerencia> sugerencias = sugerenciaDAO.findAll();
			return new ResponseEntity<List<Sugerencia>>(sugerencias, HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<List<Sugerencia>>(HttpStatus.NO_CONTENT);
		}
	}	
	
	@GetMapping("/getSugerenciaById/{id}")
	public ResponseEntity<Sugerencia> getSugerenciaById(@PathVariable long id){
		try {
			Sugerencia sugerencia = sugerenciaDAO.findById(id);
			return new ResponseEntity<Sugerencia>(sugerencia, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("/getSugerenciasByDate/{date}")
	public ResponseEntity<List<Sugerencia>> getSugerenciasByDate(@PathVariable LocalDate date, @RequestParam  int max){
		try {
			List<Sugerencia> sugerencias = sugerenciaDAO.findByDate(date, max);
			return new ResponseEntity<List<Sugerencia>>(sugerencias, HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<List<Sugerencia>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/getSugerenciasOrdereredByDateDesc")
	public ResponseEntity<List<Sugerencia>> getSugerenciasOrdereredByDateDesc(){
		try {
			List<Sugerencia> sugerencias = sugerenciaDAO.findAllOrderedByDateDesc();
			return new ResponseEntity<List<Sugerencia>>(sugerencias, HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<List<Sugerencia>>(HttpStatus.NO_CONTENT);
		}
	}	
	
	@GetMapping("/getSugerenciasOrdereredByDateAsc")
	public ResponseEntity<List<Sugerencia>> getSugerenciasOrdereredByDateAsc(){
		try {
			List<Sugerencia> sugerencias = sugerenciaDAO.findAllOrderedByDateAsc();
			return new ResponseEntity<List<Sugerencia>>(sugerencias, HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<List<Sugerencia>>(HttpStatus.NO_CONTENT);
		}
	}	
	
	
	

}
