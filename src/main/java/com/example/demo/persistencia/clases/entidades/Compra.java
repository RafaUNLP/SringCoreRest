package com.example.demo.persistencia.clases.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity //en la creacion cuando dentro hay un menu, no mandar el TIPOMENU sino TIPOITEM con el tipomenu concreto
public class Compra extends EntidadBase{

	@NotNull @DecimalMin(value = "0.0", message = "El precio debe ser al menos 0.0")
    @DecimalMax(value = "999999999.9", message = "El precio no debe ser mayor que 999.999.999,9")
	private double precio;
	
	@NotNull
	private LocalDate fecha;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	@JsonIgnore
	private Usuario usuario;
	
	@OneToMany
    @JoinColumn(name = "compra_id", referencedColumnName = "id")
	private List<Item> items;
	
	public Compra () {
		this.items = new ArrayList<Item>();
	} //Hibernate y POJOs

	public Compra(double precio,LocalDate fecha) {
		this(); //constructor por defecto
		this.precio = precio;
		this.fecha = fecha;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public List<Item> getItems(){
		return this.items;
	}
	
}
