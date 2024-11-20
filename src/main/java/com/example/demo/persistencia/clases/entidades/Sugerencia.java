package com.example.demo.persistencia.clases.entidades;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
