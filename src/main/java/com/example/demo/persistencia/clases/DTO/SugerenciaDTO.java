package com.example.demo.persistencia.clases.DTO;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SugerenciaDTO {

		@NotNull @Size(max=256,message="Las sugerencias no pueden superar los 256 caracteres")
		private String texto;
		
		@NotNull
		private LocalDate fecha;
		
		@NotNull
		private Long usuarioId;
		
		public SugerenciaDTO(String texto, LocalDate fecha, Long usuarioId) {
			this.texto = texto;
			this.fecha = fecha;
			this.usuarioId = usuarioId; 	
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
		
		public Long getUsuarioId() {
			return usuarioId;
		}

		public void setUsuarioId(Long usuarioId) {
			this.usuarioId = usuarioId;
		}
		
}
