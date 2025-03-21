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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.PersistenceException;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/turnos/")
@Tag(name="Turnos", description="CRUD de los turnos en los que podrán trabajar los responsables de turnos")
public class TurnoController {
	@Autowired
	private TurnoDAOHibernateJPA turnoDAO;
	
	@PostMapping()
	@Operation(summary="Crear un turno")
	public ResponseEntity<Turno> createUsuario(@Valid @RequestBody Turno turno){
		try {
			Turno turnoPersistido = turnoDAO.persist(turno);
			return new ResponseEntity<Turno>(turnoPersistido, HttpStatus.CREATED);
		}catch(PersistenceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PutMapping()
	@Operation(summary="Actualizar un turno")
	public ResponseEntity<Turno> updateTurno(@Valid @RequestBody Turno turno){
		try {
			turnoDAO.update(turno);
			return new ResponseEntity<Turno>(turno, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Turno>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping("{id}")
	@Operation(summary="Eliminar un turno por su Id")
	public ResponseEntity<Turno> deleteTurnoById(@PathVariable long id){
		try {
			turnoDAO.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Turno>(HttpStatus.NO_CONTENT);	
		}
	}
	@DeleteMapping()
	@Operation(summary="Eliminar un turno")
	public ResponseEntity<Turno> deleteUsuario(@RequestBody Turno turno){
		try {
			turnoDAO.delete(turno);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Turno>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("{id}")
	@Operation(summary="Recuperar un turno por su Id")
	public ResponseEntity<Turno> getTurnoById(@PathVariable long id){
		try {
			Turno turno = turnoDAO.findById(id);
			if(turno == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<Turno>(turno, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping()
	@Operation(summary="Recuperar todos los turnos")
	public ResponseEntity<List<Turno>> getTurnos(){		
		try {
			List<Turno> turnos = turnoDAO.findAll();
			return new ResponseEntity<List<Turno>>(turnos, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Turno>>(HttpStatus.NO_CONTENT);
		}		
	}
	
	@GetMapping("/ordenados-por-hora")
	@Operation(summary="Recuperar todos los turnos ordenados por hora de inicio")
	public ResponseEntity<List<Turno>> getTurnosOrderedByInitialHour(){		
		try {
			List<Turno> turnos = turnoDAO.findAllOrderedByInitialHour();
			return new ResponseEntity<List<Turno>>(turnos, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Turno>>(HttpStatus.NO_CONTENT);
		}		
	}
	
	
	
	
	
}
