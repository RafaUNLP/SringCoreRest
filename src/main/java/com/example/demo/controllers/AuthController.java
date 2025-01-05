
package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.persistencia.clases.DAO.UsuarioDAOHibernateJPA;
import com.example.demo.persistencia.clases.entidades.Usuario;
import com.example.demo.services.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.PersistenceException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/auth/")
@Tag(name="Autenticación", description="Login de usuarios")
public class AuthController {
	
	private int segundosToken = 86400; //24 hs
	
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UsuarioDAOHibernateJPA usuarioDAO;
	@Autowired
	private PasswordEncoder encoder;
	
	@PostMapping("login")
	@Operation(summary="Login de un usuario")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest pedido){
		try {
			LoginResponse respuesta;
			String ingresada = pedido.getPassword();
//			if(ingresada == null) {
//				respuesta = new LoginResponse("No se recibió una contraseña", null);
//				return new ResponseEntity<LoginResponse>(respuesta, HttpStatus.CONFLICT);
//			}
			Usuario usuario = usuarioDAO.findByEmail(pedido.getEmail());
			if(usuario == null) {
				respuesta = new LoginResponse("No se encotró el usuario", null);
				return new ResponseEntity<LoginResponse>(respuesta, HttpStatus.NOT_FOUND);
			}
			if(!encoder.matches(ingresada,usuario.getPassword())) {
				respuesta = new LoginResponse("Credenciales inválidas", null);
				return new ResponseEntity<LoginResponse>(respuesta, HttpStatus.UNAUTHORIZED);
			}

			String token = this.tokenService.generarToken(usuario.getEmail(), this.segundosToken);
			respuesta = new LoginResponse(token, usuario);
			return new ResponseEntity<LoginResponse>(respuesta, HttpStatus.OK);
			
		}catch(PersistenceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}

	//clases auxiliares, son estáticas (muy a mí pesar) para permitir la deserialización
	private static class LoginResponse {
		private String token;
		private Usuario usuario;
		
		public LoginResponse(String token, Usuario usuario) {
			this.token = token;
			this.usuario = usuario;
		}

		public Usuario getUsuario() {
			return usuario;
		}

		public String getToken() {
			return token;
		}
	}
	
	// Hacerlas estáticas para permitir la deserialización
	private static class LoginRequest {
		private String email;
		private String password;
		
		public LoginRequest() {}

		public String getEmail() {
			return email;
		}

		public String getPassword() {
			return password;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
	
}

