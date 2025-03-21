package com.example.demo.persistencia.clases.entidades;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name="tipo_rol")
@JsonTypeInfo(
	    use = JsonTypeInfo.Id.NAME, 
	    include = JsonTypeInfo.As.PROPERTY, 
	    property = "tipoRol"  // El nombre del campo que se usará para identificar el tipo
	)
	@JsonSubTypes({
	    @JsonSubTypes.Type(value = ClienteRol.class, name = "cliente"),
	    @JsonSubTypes.Type(value = ResponsableDeTurnoRol.class, name = "responsable-turno"),
	    @JsonSubTypes.Type(value = AdministradorRol.class, name = "administrador")
	})
public abstract class Rol extends EntidadBase{
	
	@NotNull @Size(max=30,message="El nombre no debe superar los 30 caracteres")
	private String nombre;
	
	public Rol() {} //Hibernate y POJOs
	
	public Rol(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public boolean equals(Rol otro) { /*para que no importe si son o no la misma ref de memoria*/
		return this.nombre.toLowerCase().trim().equals(otro.getNombre().toLowerCase().trim());
	}
	
	public abstract boolean puedeSugerir();
	
	public abstract boolean puedeComprar();
	
	public abstract boolean puedeTenerTurnos();

}
