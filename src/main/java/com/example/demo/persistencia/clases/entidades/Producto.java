package com.example.demo.persistencia.clases.entidades;

import javax.persistence.Entity;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Producto extends Item{

	@NotNull @Size(max=50,message="El nombre no debe superar los 50 caracteres")
	private String nombre;
	
	@NotNull @DecimalMin(value = "0.0", message = "El precio debe ser al menos 0.0")
    @DecimalMax(value = "99999999.9", message = "El precio no debe ser mayor que 99.999.999,9")
	private double precio;
	
	public Producto() {} //Hibernate y POJOs

	public Producto(String nombre, double precio) {
		this.nombre = nombre;
		this.precio = precio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	
}
