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
import com.example.demo.persistencia.clases.DAO.UsuarioDAOHibernateJPA;
import com.example.demo.persistencia.clases.entidades.Rol;
import com.example.demo.persistencia.clases.entidades.Usuario;

import jakarta.persistence.PersistenceException;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioDAOHibernateJPA usuarioDAO;
	
	@PostMapping("/createUsuario")
	public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario){
		try {
			Usuario usuarioPersistido = usuarioDAO.persist(usuario);
			return new ResponseEntity<Usuario>(usuarioPersistido, HttpStatus.CREATED);
		}catch(PersistenceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PutMapping("/updateUsuario")
	public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario){
		try {
			usuarioDAO.update(usuario);
			return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping("/deleteUsuarioById/{id}")
	public ResponseEntity<Usuario> deleteUsuarioById(@PathVariable long id){
		try {
			usuarioDAO.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping("/deleteUsuario")
	public ResponseEntity<Usuario> deleteUsuario(@RequestBody Usuario usuario){
		try {
			usuarioDAO.delete(usuario);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);	
		}
	}
	
	
	@GetMapping("/getUsuarioById/{id}")
	public ResponseEntity<Usuario> getUsuarioById(@PathVariable long id){
		try {
			Usuario usuario = usuarioDAO.findById(id);
			return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("/getUsuarioByDni/{dni}")
	public ResponseEntity<Usuario> getUsuarioByDni(@PathVariable String dni){
		try {
			Usuario usuario = usuarioDAO.findByDni(dni);
			return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("/getUsuariosByRol/{rol}")
	public ResponseEntity<List<Usuario>> getUsuariosByRol(@PathVariable Rol rol){
		try {
			List<Usuario> usuarios = usuarioDAO.findByRol(rol);
			return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<List<Usuario>> (HttpStatus.NO_CONTENT);	
		}
	}	
	
	
	@GetMapping("/getUsuarios")
	public ResponseEntity<List<Usuario>> getUsuarios(){		
		try {
			List<Usuario> usuarios = usuarioDAO.findAll();
			return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Usuario>>(HttpStatus.NO_CONTENT);
		}		
	}
	

	@GetMapping("/getUsuariosOrderedByNameAsc")
	public ResponseEntity<List<Usuario>> getUsuariosOrderedByNameAsc(){
		try {
			List<Usuario> usuarios = usuarioDAO.findAllOrderedByNameAsc();
			return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<List<Usuario>> (HttpStatus.NO_CONTENT);	
		}
	}
	
	
	

}
