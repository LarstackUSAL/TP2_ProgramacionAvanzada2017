package ar.edu.usal.hotel.model.dto;

public class Habitaciones {

	private int numero;
	private int capacidad;
	private char categoria;
	private boolean tieneBalcon;
	private String comentario;
	private Precios precio;
	private boolean disponible;
	
	public Habitaciones(){}
	
	public Habitaciones(int numero, int capacidad, char categoria,
			boolean tieneBalcon, String comentario) {
		super();
		this.numero = numero;
		this.capacidad = capacidad;
		this.categoria = categoria;
		this.tieneBalcon = tieneBalcon;
		this.comentario = comentario;
		this.disponible = true;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public char getCategoria() {
		return categoria;
	}

	public void setCategoria(char categoria) {
		this.categoria = categoria;
	}

	public boolean isTieneBalcon() {
		return tieneBalcon;
	}

	public void setTieneBalcon(boolean tieneBalcon) {
		this.tieneBalcon = tieneBalcon;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Precios getPrecio() {
		return precio;
	}

	public void setPrecio(Precios precio) {
		this.precio = precio;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}	
}
