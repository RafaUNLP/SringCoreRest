package com.example.demo.persistencia.clases.DTO;

import java.time.format.DateTimeFormatter;

import com.example.demo.persistencia.clases.entidades.Turno;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TurnoDTO {

	private Long id;
	
	@NotNull @Size(max=30,message="El nombre no debe superar los 30 caracteres")
	private String nombre;
	
	@NotNull
	@JsonFormat(pattern = "HH:mm")
	private String horaEntrada;
	
	@NotNull
	@JsonFormat(pattern = "HH:mm")
	private String horaSalida;
	
	public TurnoDTO() {} //Hibernate y POJOs
		
		public TurnoDTO(String nombre, String horaEntrada, String horaSalida) {
			this.nombre = nombre;
			this.horaEntrada = horaEntrada;
			this.horaSalida = horaSalida;
		}
		
		public TurnoDTO(Turno turno) {
			this.id = turno.getId();
			this.nombre = turno.getNombre();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			this.horaEntrada = turno.getHoraEntrada().format(formatter);
			this.horaSalida = turno.getHoraSalida().format(formatter);
		}
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
	
		public String getNombre() {
			return nombre;
		}
	
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
	
		public String getHoraEntrada() {
			return horaEntrada;
		}
	
		public void setHoraEntrada(String horaEntrada) {
			this.horaEntrada = horaEntrada;
		}
	
		public String getHoraSalida() {
			return horaSalida;
		}
	
		public void setHoraSalida(String horaSalida) {
			this.horaSalida = horaSalida;
		}
		
		
	}

