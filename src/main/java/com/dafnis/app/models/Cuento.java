
package com.dafnis.app.models;

public class Cuento{
	private int id;
	private String titulo;
	private String autor;
	private String categoria;
	private String nacionalidad;
	private String años;

	public Cuento(int id, String titulo, String autor, String categoria, String nacionalidad, String años){
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.categoria = categoria;
		this.nacionalidad = nacionalidad;
		this.años = años;
	}

	public Cuento(){}

	public void setTitulo(String titulo){
		this.titulo = titulo;
	}
	public void setAutor(String autor){
		this.autor = autor;
	}
	public void setCategoria(String categoria){
		this.categoria = categoria;
	}
	public void setNacionalidad(String nacionalidad){
		this.nacionalidad = nacionalidad;
	}
	public void setAños(String años){
		this.años = años;
	}

	public int getId(){
		return id;
	}
	public String getTitulo(){
		return titulo;
	}
	public String getAutor(){
		return autor;
	}
	public String getCategoria(){
		return categoria;
	}
	public String getNacionalidad(){
		return nacionalidad;
	}
	public String getAños(){
		return años;
	}

	public String toString(){
		return "Cuento: id=" + id + ", titulo=" + titulo + ", autor=" + autor + ", categoria=" + categoria + ", nacionalidad=" + nacionalidad + ", años=" + años;
	}
}















