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

import com.example.demo.persistencia.clases.DAO.RolDAOHibernateJPA;
import com.example.demo.persistencia.clases.entidades.Rol;

import jakarta.persistence.PersistenceException;


@RestController
@RequestMapping("/api/rol")
public class RolController {
	@Autowired
	private RolDAOHibernateJPA rolDAO;
	
	@PostMapping("/createRol")
	public ResponseEntity<Rol> createUsuario(@RequestBody Rol rol){
		try {
			Rol rolPersistido = rolDAO.persist(rol);
			return new ResponseEntity<Rol>(rolPersistido, HttpStatus.CREATED);
		}catch(PersistenceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PutMapping("/updateRol")
	public ResponseEntity<Rol> updateRol(@RequestBody Rol rol){
		try {
			rolDAO.update(rol);
			return new ResponseEntity<Rol>(rol, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Rol>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping("/deleteRolById/{id}")
	public ResponseEntity<Rol> deleteRolById(@PathVariable long id){
		try {
			rolDAO.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Rol>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping("/deleteRol")
	public ResponseEntity<Rol> deleteRol(@RequestBody Rol rol){
		try {
			rolDAO.delete(rol);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Rol>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("/getRolById/{id}")
	public ResponseEntity<Rol> getRolById(@PathVariable long id){
		try {
			Rol rol = rolDAO.findById(id);
			return new ResponseEntity<Rol>(rol, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("/getRolByName/{name}")
	public ResponseEntity<Rol> getRolByName(@PathVariable String name){
		try {
			Rol rol = rolDAO.findByName(name);
			return new ResponseEntity<Rol>(rol, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("/getRoles")
	public ResponseEntity<List<Rol>> getRoles(){		
		try {
			List<Rol> roles= rolDAO.findAll();
			return new ResponseEntity<List<Rol>>(roles, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Rol>>(HttpStatus.NO_CONTENT);
		}		
	}
}
