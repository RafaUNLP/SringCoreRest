package com.example.demo.persistencia.clases.DTO;
import java.time.LocalDate;

import com.example.demo.persistencia.clases.entidades.Sugerencia.CategoriaSugerencia;
import com.example.demo.persistencia.clases.entidades.Usuario;
import com.example.demo.persistencia.clases.entidades.Sugerencia;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SugerenciaDTO {

	    private Long id;
	
		@NotNull @Size(max=256,message="Las sugerencias no pueden superar los 256 caracteres")
		private String texto;
		
		@NotNull
		private LocalDate fecha;
		
		@NotNull
		private Long usuarioId;
		
		private String nombreAutor;
		
		@NotNull
		private CategoriaSugerencia categoria;
		
		public SugerenciaDTO() {}
		
//		public SugerenciaDTO(String texto, LocalDate fecha, Usuario usuario) {
//			this.texto = texto;
//			this.fecha = fecha;
//			this.usuarioId = usuario.getId();
//			this.nombreAutor = usuario.getNombre() + " " +usuario.getApellido();
//		}
//
//		public SugerenciaDTO(Long id, String texto, LocalDate fecha, Usuario usuario) {
//			this.id = id;
//			this.texto = texto;
//			this.fecha = fecha;
//			this.usuarioId = usuario.getId();
//			this.nombreAutor = usuario.getNombre() + " " + usuario.getApellido();
//		}
		
		public SugerenciaDTO(Sugerencia original) {
			this.id = original.getId();
			this.texto = original.getTexto();
			this.fecha = original.getFecha();
			this.usuarioId = original.getUsuario().getId();
			this.nombreAutor = original.getUsuario().getNombre() + " " + original.getUsuario().getApellido();
			this.categoria = original.getCategoria();
		}
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
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

		public CategoriaSugerencia getCategoria() {
			return categoria;
		}

		public void setCategoria(CategoriaSugerencia categoria) {
			this.categoria = categoria;
		}

		public String getNombreAutor() {
			return nombreAutor;
		}

		public void setNombreAutor(String nombreAutor) {
			this.nombreAutor = nombreAutor;
		}
		
		
}
