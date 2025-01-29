package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.persistencia.clases.DAO.RolDAOHibernateJPA;
import com.example.demo.persistencia.clases.DAO.UsuarioDAOHibernateJPA;
import com.example.demo.persistencia.clases.entidades.Rol;
import com.example.demo.persistencia.clases.entidades.Usuario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios/")
@Tag(name="Usuarios", description="CRUD de usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioDAOHibernateJPA usuarioDAO;
	@Autowired
	private RolDAOHibernateJPA rolDAO;
	@Autowired
	private PasswordEncoder encoder;
	
	@PostMapping()
	@Operation(summary="Crear un usuario")
	public ResponseEntity<Usuario> create(@Valid @RequestBody Usuario usuario){
		try {
			Usuario usuarioEmail = usuarioDAO.findByEmail(usuario.getEmail());
			Rol rol = rolDAO.findByName(usuario.getRol().getNombre());
			usuario.setRol(rol);
			usuario.setPassword(encoder.encode(usuario.getPassword()));
			Usuario usuarioPersistido = usuarioDAO.persist(usuario);
			return new ResponseEntity<Usuario>(usuarioPersistido, HttpStatus.CREATED);
		}catch(PersistenceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PutMapping()
	@Operation(summary="Actualizar un usuario")
	public ResponseEntity<Usuario> update(@Valid @RequestBody Usuario usuario){
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
	
	@GetMapping("rol/{nombreRol}")
	@Operation(summary="Recupear los usuarios que tienen un determinado rol")
	public ResponseEntity<List<Usuario>> getByRol(@PathVariable String nombreRol){
		try {
			Rol encontrado = rolDAO.findByName(nombreRol);
			if (encontrado == null)
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
			List<Usuario> usuarios = usuarioDAO.findByRol(encontrado);
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
