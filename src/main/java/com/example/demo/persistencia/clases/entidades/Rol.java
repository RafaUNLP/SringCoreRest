package com.example.demo.persistencia.clases.entidades;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name="tipo_rol")
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
	
	public abstract boolean puedeTernerTurnos();

}
