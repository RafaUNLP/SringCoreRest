package com.example.demo.persistencia.clases.DTO;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.persistencia.clases.entidades.Compra;
import com.example.demo.persistencia.clases.entidades.Rol;
import com.example.demo.persistencia.clases.entidades.Sugerencia;
import com.example.demo.persistencia.clases.entidades.Turno;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;


public class UsuarioDTO {
	
	@NotNull
    private Long id;
    
	@NotNull @Size(min=8,max=8,message="El dni debe contar con 8 caracteres numéricos")
	private String dni;/*conviene que el dni sea un string para validar mejor y poder tener ceros a la izq*/
	
	@NotNull
	private String imagen;
	
	@NotNull
	private String tipoMime;
	
	@NotNull @Size(max=30,message="El nombre no debe superar los 30 caracteres")
	private String nombre;
	
	@NotNull @Size(max=30,message="El apellido no debe superar los 30 caracteres")
	private String apellido;
	
    @Email(message = "Correo electrónico inválido")
	private String email;

    private Rol rol;
    
	private Set<Sugerencia> sugerencias;
	
    private Set<Turno> turnos;
	
    private Set<Compra> compras;
	
	public UsuarioDTO() {
		this.sugerencias = new HashSet<Sugerencia>();
		this.turnos = new HashSet<Turno>();
		this.compras = new HashSet<Compra>();
	} //lo requiere Hibernate, que espera POJOs

	public UsuarioDTO(Long id, String dni, String pathImagen, String nombre, String apellido, String email, Rol rol) {
	    this();
	    this.id = id;
	    this.dni = dni;
	    this.imagen = pathImagen;
	    this.nombre = nombre;
	    this.apellido = apellido;
	    this.email = email;
	    this.rol = rol;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	
	public Rol getRol () {
		return this.rol;
	}
	
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	public Set<Sugerencia> getSugerencias(){
		if(this.rol != null && rol.puedeSugerir())
			return this.sugerencias;
		return new HashSet<Sugerencia>();
	}
	
	public void addSugerencia (Sugerencia sugerencia) {
		if(this.rol != null && this.rol.puedeSugerir())
			this.sugerencias.add(sugerencia);
	}
	
	public void removeSugerencia (Sugerencia sugerencia) {
		if(this.rol != null && this.rol.puedeSugerir())
			this.sugerencias.remove(sugerencia);
	}
	
	public Set<Turno> getTurnos(){
		if (this.rol != null && this.rol.puedeTenerTurnos())
			return this.turnos;
		return new HashSet<Turno>();
	}
	
	public void addTurno (Turno turno) {
		if(this.rol != null && this.rol.puedeTenerTurnos())
			this.turnos.add(turno);
	}
	
	public void removeTurno (Turno turno) {
		if(this.rol != null && this.rol.puedeTenerTurnos())
			this.turnos.remove(turno);
	}
	
	public Set<Compra> getCompras(){
		if (this.rol != null && this.rol.puedeComprar())
			return this.compras;
		return new HashSet<Compra>();
	}
	
	public void addTurno (Compra compra) {
		if(this.rol != null && this.rol.puedeComprar())
			this.compras.add(compra);
	}
	
	public void removeTurno (Compra compra) {
		if(this.rol != null && this.rol.puedeComprar())
			this.compras.remove(compra);
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String pathImagen) {
		this.imagen = pathImagen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String mail) {
		this.email = mail;
	}
	
	public String getTipoMime() {
		return tipoMime;
	}

	public void setTipoMime(String tipoMime) {
		this.tipoMime = tipoMime;
	}
}

