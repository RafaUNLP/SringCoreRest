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
import com.example.demo.persistencia.clases.DAO.TurnoDAOHibernateJPA;
import com.example.demo.persistencia.clases.DAO.UsuarioDAOHibernateJPA;
import com.example.demo.persistencia.clases.DTO.TurnoDTO;
import com.example.demo.persistencia.clases.DTO.UsuarioDTO;
import com.example.demo.persistencia.clases.entidades.Rol;
import com.example.demo.persistencia.clases.entidades.Turno;
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
	private TurnoDAOHibernateJPA turnoDAO;
	@Autowired
	private PasswordEncoder encoder;
	
	@PostMapping()
	@Operation(summary="Crear un usuario")
	public ResponseEntity<Usuario> create(@Valid @RequestBody Usuario usuario){
		try {
			Usuario usuarioEmail = usuarioDAO.findByEmail(usuario.getEmail());
			
			if(usuarioEmail != null)
				throw new Exception("No es posible utilizar ese email en estos momentos. Por favor, elije otro");
			
			Usuario usuarioDNI = usuarioDAO.findByEmail(usuario.getEmail());
			
			if(usuarioDNI != null)
				throw new Exception("No es posible utilizar ese DNI en estos momentos. Por favor, comunícate con nostros para poder solucionarlo");
			
			Rol rol = rolDAO.findByName(usuario.getRol().getNombre());
			usuario.setRol(rol);
			usuario.setPassword(encoder.encode(usuario.getPassword()));
			Usuario usuarioPersistido = usuarioDAO.persist(usuario);
			return new ResponseEntity<Usuario>(usuarioPersistido, HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PutMapping()
	@Operation(summary="Actualizar un usuario")
	public ResponseEntity<Usuario> update(@Valid @RequestBody UsuarioDTO usuarioDTO){
		try {
			Usuario original = usuarioDAO.findById(usuarioDTO.getId());
			if(original == null)
				throw new Exception("Usuario no encontrado");
			
			if(usuarioDTO.getEmail() != original.getEmail()) {//cambió el mail
				Usuario otroUsuarioConEseMail = usuarioDAO.findByEmail(usuarioDTO.getEmail());
				if(otroUsuarioConEseMail != null && otroUsuarioConEseMail.getId() != original.getId())
					throw new ConstraintViolationException("No es posible utilizar ese email en estos momentos. Por favor, manten el anterior o elije otro",null);
			}
			
			if(usuarioDTO.getDni() != original.getDni()) {//cambió el mail
				Usuario otroUsuarioConEseDNI = usuarioDAO.findByDni(usuarioDTO.getDni());
				if(otroUsuarioConEseDNI != null && otroUsuarioConEseDNI.getId() != original.getId())
					throw new ConstraintViolationException("No es posible utilizar ese DNI en estos momentos. Por favor, manten el anterior o comunícate con nostros para resolverlo", null);
			}
			
			original.setDni(usuarioDTO.getDni());
			original.setEmail(usuarioDTO.getEmail());
	        original.setImagen(usuarioDTO.getImagen());
	        original.setNombre(usuarioDTO.getNombre());
	        original.setApellido(usuarioDTO.getApellido());
			original = usuarioDAO.update(original);
			return new ResponseEntity<Usuario>(original, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Usuario>(HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping("/asignar-turno/{usuarioId}/{turnoId}")
	@Operation(summary="Asignar un turno a un usuario responsable de turnos")
	public ResponseEntity<Usuario> asignTurno(@PathVariable long usuarioId, @PathVariable long turnoId){
		try {
			Usuario original = usuarioDAO.findById(usuarioId);
			if(original == null)
				throw new Exception("Usuario no encontrado");
			boolean yaAsignado = original.getTurnos().stream().anyMatch(t -> t.getId() == turnoId);
			if(!yaAsignado) {
				Turno turnoOriginal = turnoDAO.findById(turnoId);
				original.addTurno(turnoOriginal);
				original = usuarioDAO.update(original);
			}
			return new ResponseEntity<Usuario>(original, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);	
		}
	}
	
	@PutMapping("/sacar-turno/{usuarioId}/{turnoId}")
	@Operation(summary="Saca un turno a un usuario responsable de turnos")
	public ResponseEntity<Usuario> removeTurno(@PathVariable long usuarioId, @PathVariable long turnoId){
		try {
			Usuario original = usuarioDAO.findById(usuarioId);
			if(original == null)
				throw new Exception("Usuario no encontrado");
			boolean yaAsignado = original.getTurnos().stream().anyMatch(t -> t.getId() == turnoId);
			if(yaAsignado) {
				Turno turnoOriginal = turnoDAO.findById(turnoId);
				original.removeTurno(turnoOriginal);
				original = usuarioDAO.update(original);
			}
			return new ResponseEntity<Usuario>(original, HttpStatus.OK);
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
	@Operation(summary="Recuperar un usuario por su Id")
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
	@Operation(summary="Recuperar un usuario por su DNI")
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
	@Operation(summary="Recuperar los usuarios que tienen un determinado rol")
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
	@Operation(summary="Recuperar todos los usuarios")
	public ResponseEntity<List<Usuario>> getAll(){		
		try {
			List<Usuario> usuarios = usuarioDAO.findAll();
			return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Usuario>>(HttpStatus.NO_CONTENT);
		}		
	}
	

	@GetMapping("/ordenados-por-nombre")
	@Operation(summary="Recuperar todos los usuarios ordenados por nombre")
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
