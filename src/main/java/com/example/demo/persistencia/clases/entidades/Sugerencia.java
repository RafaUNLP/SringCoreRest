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
	
	@NotNull
	private CategoriaSugerencia categoria;
	
	@ManyToOne
	@JoinColumn(name="usuario_id",referencedColumnName="id")
	private Usuario usuario;
	
    @NotNull
	public enum CategoriaSugerencia{
		Alimentos,Atencion,Precios,Infraestructura}

	public Sugerencia() {} //Hibernate y POJOs
	
	public Sugerencia(String texto, LocalDate fecha, CategoriaSugerencia categoria, Usuario usuario) {
		this.texto = texto;
		this.fecha = fecha;
		this.usuario = usuario;
		this.categoria = categoria;
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
	
	public CategoriaSugerencia getCategoria() {
		return categoria;
	}
	
	public void setCategoria(CategoriaSugerencia categoria) {
		this.categoria = categoria;
	}
	
	public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
	
}


