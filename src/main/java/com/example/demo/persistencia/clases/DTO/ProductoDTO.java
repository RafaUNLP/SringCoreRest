package com.example.demo.persistencia.clases.DTO;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductoDTO {

	@NotNull @Size(max=50,message="El nombre no debe superar los 50 caracteres")
	private String nombre;
	
	@NotNull @DecimalMin(value = "0.0", message = "El precio debe ser al menos 0.0")
    @DecimalMax(value = "99999999.9", message = "El precio no debe ser mayor que 99.999.999,9")
	private double precio;

	public ProductoDTO(String nombre, double precio) {
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
