package com.example.demo.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.persistencia.clases.DAO.TurnoDAOHibernateJPA;
import com.example.demo.persistencia.clases.entidades.Turno;
import com.example.demo.persistencia.clases.entidades.Usuario;

import jakarta.persistence.PersistenceException;


@RestController
@RequestMapping("/api/turno")
public class TurnoController {
	@Autowired
	private TurnoDAOHibernateJPA turnoDAO;
	
	@PostMapping("/createTurno")
	public ResponseEntity<Turno> createUsuario(@RequestBody Turno turno){
		try {
			Turno turnoPersistido = turnoDAO.persist(turno);
			return new ResponseEntity<Turno>(turnoPersistido, HttpStatus.CREATED);
		}catch(PersistenceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PutMapping("/updateTurno")
	public ResponseEntity<Turno> updateTurno(@RequestBody Turno turno){
		try {
			turnoDAO.update(turno);
			return new ResponseEntity<Turno>(turno, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Turno>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping("/deleteTurnoById/{id}")
	public ResponseEntity<Turno> deleteTurnoById(@PathVariable long id){
		try {
			turnoDAO.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Turno>(HttpStatus.NO_CONTENT);	
		}
	}
	@DeleteMapping("/deleteTurno")
	public ResponseEntity<Turno> deleteUsuario(@RequestBody Turno turno){
		try {
			turnoDAO.delete(turno);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Turno>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("/getTurnoById/{id}")
	public ResponseEntity<Turno> getTurnoById(@PathVariable long id){
		try {
			Turno turno = turnoDAO.findById(id);
			return new ResponseEntity<Turno>(turno, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("/getTurnos")
	public ResponseEntity<List<Turno>> getTurnos(){		
		try {
			List<Turno> turnos = turnoDAO.findAll();
			return new ResponseEntity<List<Turno>>(turnos, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Turno>>(HttpStatus.NO_CONTENT);
		}		
	}
	@GetMapping("/getTurnosOrderedByInitialHour")
	public ResponseEntity<List<Turno>> getTurnosOrderedByInitialHour(){		
		try {
			List<Turno> turnos = turnoDAO.findAllOrderedByInitialHour();
			return new ResponseEntity<List<Turno>>(turnos, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Turno>>(HttpStatus.NO_CONTENT);
		}		
	}
	
	
	
	
	
}
