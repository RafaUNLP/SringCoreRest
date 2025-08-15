package com.example.demo.persistencia.clases.entidades;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints={
	    @UniqueConstraint(name = "dni_unico" ,columnNames="dni"),
	    @UniqueConstraint(name = "email_unico" ,columnNames="email")
	})
public class Usuario extends EntidadBase{
	
	@NotNull @Size(min=8,max=8,message="El dni debe contar con 8 caracteres numéricos")
	private String dni;/*conviene que el dni sea un string para validar mejor y poder tener ceros a la izq*/
	
	@NotNull @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Size(min=8,message="La contraseña debe contar con al menos 8 caracteres") 
	private String password;
	
	@NotNull
	@Column(columnDefinition="text")
	private String imagen;
	
	@Column(columnDefinition="text")
	private String tipoMime;
	
	@NotNull @Size(max=30,message="El nombre no debe superar los 30 caracteres")
	private String nombre;
	
	@NotNull @Size(max=30,message="El apellido no debe superar los 30 caracteres")
	private String apellido;
	
    @Email(message = "Correo electrónico inválido")
	private String email;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Rol rol;
    
	@OneToMany
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	private Set<Sugerencia> sugerencias;
	
	@ManyToMany
	@JoinTable(
	    name = "usuario_turno",
	    joinColumns = @JoinColumn(name = "usuario_id"),
	    inverseJoinColumns = @JoinColumn(name = "turno_id")
	)
	private Set<Turno> turnos = new HashSet<>();
	
	@OneToMany(mappedBy = "usuario")
    private Set<Compra> compras;
	
	public Usuario() {
		this.sugerencias = new HashSet<Sugerencia>();
		this.turnos = new HashSet<Turno>();
		this.compras = new HashSet<Compra>();
	} //lo requiere Hibernate, que espera POJOs

	public Usuario(String dni, String password, String pathImagen, String nombre, String apellido, String email, String tipoMime) {
		this(); //constructor por defecto
		this.dni = dni;
		this.password = password;
		this.imagen = pathImagen;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.tipoMime = tipoMime;
	}
	
	public Rol getRol () {
		return this.rol;
	}
	
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	public Set<Sugerencia> getSugerencias(){
		if(rol.puedeSugerir())
			return this.sugerencias;
		return new HashSet<Sugerencia>();
	}
	
	public boolean addSugerencia (Sugerencia sugerencia) {
		if(this.rol.puedeSugerir())
			this.sugerencias.add(sugerencia);
		return this.rol.puedeSugerir();
	}
	
	public void removeSugerencia (Sugerencia sugerencia) {
		if(this.rol.puedeSugerir())
			this.sugerencias.remove(sugerencia);
	}
	
	public Set<Turno> getTurnos(){
		if (this.rol.puedeTenerTurnos())
			return this.turnos;
		return new HashSet<Turno>();
	}
	
	public void addTurno (Turno turno) {
		if(this.rol.puedeTenerTurnos())
			this.turnos.add(turno);
	}
	
	public void removeTurno (Turno turno) {
		if(this.rol.puedeTenerTurnos())
			this.turnos.remove(turno);
	}
	
	public Set<Compra> getCompras(){
		if (this.rol.puedeComprar())
			return this.compras;
		return new HashSet<Compra>();
	}
	
	public void addTurno (Compra compra) {
		if(this.rol.puedeComprar())
			this.compras.add(compra);
	}
	
	public void removeTurno (Compra compra) {
		if(this.rol.puedeComprar())
			this.compras.remove(compra);
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
