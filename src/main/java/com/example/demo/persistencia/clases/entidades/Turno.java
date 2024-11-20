package com.example.demo.persistencia.clases.entidades;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Turno extends EntidadBase{

	@NotNull @Size(max=30,message="El nombre no debe superar los 30 caracteres")
	private String nombre;
	
	@NotNull
	private LocalTime horaEntrada;
	
	@NotNull
	private LocalTime horaSalida;

	public Turno() {} //Hibernate y POJOs
	
	public Turno(String nombre, LocalTime horaEntrada, LocalTime horaSalida) {
		this.nombre = nombre;
		this.horaEntrada = horaEntrada;
		this.horaSalida = horaSalida;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalTime getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(LocalTime horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public LocalTime getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(LocalTime horaSalida) {
		this.horaSalida = horaSalida;
	}
	
	
}
