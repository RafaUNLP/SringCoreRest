package com.example.demo.persistencia.clases.entidades;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Sugerencia extends EntidadBase{

	@NotNull @Size(max=256,message="Las sugerencias no pueden superar los 256 caracteres")
	private String texto;
	
	@NotNull
	private LocalDate fecha;
	
	@ManyToOne
	@JoinColumn(name="usuario_id",referencedColumnName="id")
	private Usuario usuario;

	public Sugerencia() {} //Hibernate y POJOs
	
	public Sugerencia(String texto, LocalDate fecha) {
		this.texto = texto;
		this.fecha = fecha;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	
}
