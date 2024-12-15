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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.PersistenceException;

@RestController
@RequestMapping("/api/usuarios/")
@Tag(name="Usuarios", description="CRUD de usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioDAOHibernateJPA usuarioDAO;
	
	@PostMapping("login")
	@Operation(summary="Login de un usuario")
	public ResponseEntity<Usuario> login(@RequestBody String email, @RequestBody String password){
		try {
			Usuario usuario = usuarioDAO.findByEmail(email);
			if(usuario == null)
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			if(usuario.getPassword() != password) //tenemos que agregar el hasheo o spring security
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
		}catch(PersistenceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PostMapping()
	@Operation(summary="Crear un usuario")
	public ResponseEntity<Usuario> create(@RequestBody Usuario usuario){
		try {
			Usuario usuarioEmail = usuarioDAO.findByEmail(usuario.getEmail());
			if(usuarioEmail != null)
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
			Usuario usuarioDni = usuarioDAO.findByDni(usuario.getDni());
			if(usuarioDni != null)
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
			Usuario usuarioPersistido = usuarioDAO.persist(usuario);
			return new ResponseEntity<Usuario>(usuarioPersistido, HttpStatus.CREATED);
		}catch(PersistenceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PutMapping()
	@Operation(summary="Actualizar un usuario")
	public ResponseEntity<Usuario> update(@RequestBody Usuario usuario){
		try {
			usuarioDAO.update(usuario);
			return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping("{id}")
	@Operation(summary="Recuperar un usuario por su Id")
	public ResponseEntity<Usuario> deleteById(@PathVariable long id){
		try {
			usuarioDAO.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@DeleteMapping()
	@Operation(summary="Eliminar un usuario")
	public ResponseEntity<Usuario> delete(@RequestBody Usuario usuario){
		try {
			usuarioDAO.delete(usuario);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);	
		}
	}
	
	
	@GetMapping("{id}")
	@Operation(summary="Recupear un usuario por su Id")
	public ResponseEntity<Usuario> getById(@PathVariable long id){
		try {
			Usuario usuario = usuarioDAO.findById(id);
			if(usuario == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("dni/{dni}")
	@Operation(summary="Recupear un usuario por su DNI")
	public ResponseEntity<Usuario> getByDni(@PathVariable String dni){
		try {
			Usuario usuario = usuarioDAO.findByDni(dni);
			return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@GetMapping("rol/{rol}")
	@Operation(summary="Recupear los usuarios que tienen un determinado rol")
	public ResponseEntity<List<Usuario>> getByRol(@PathVariable Rol rol){
		try {
			List<Usuario> usuarios = usuarioDAO.findByRol(rol);
			return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<List<Usuario>> (HttpStatus.NO_CONTENT);	
		}
	}	
	
	
	@GetMapping()
	@Operation(summary="Recupear todos los usuarios")
	public ResponseEntity<List<Usuario>> getAll(){		
		try {
			List<Usuario> usuarios = usuarioDAO.findAll();
			return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Usuario>>(HttpStatus.NO_CONTENT);
		}		
	}
	

	@GetMapping("/ordenados-por-nombre")
	@Operation(summary="Recupear todos los usuarios ordenados por nombre")
	public ResponseEntity<List<Usuario>> getAllAcending(){
		try {
			List<Usuario> usuarios = usuarioDAO.findAllOrderedByNameAsc();
			return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<List<Usuario>> (HttpStatus.NO_CONTENT);	
		}
	}
	
	
	

}
