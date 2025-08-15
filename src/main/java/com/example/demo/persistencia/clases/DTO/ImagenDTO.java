package com.example.demo.persistencia.clases.DTO;

public class ImagenDTO {

	private String urlImagen;
	
	public ImagenDTO () {}
	
	public ImagenDTO (String url) {
		this.urlImagen = url;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

}
