package ar.edu.usal.hotel.model.dto;

public class PreciosDto {

	private char categoria;
	private int capacidad;
	private double precio;
	
	public PreciosDto(){}
	
	public PreciosDto(char categoria, int capacidad, double precio) {
		super();
		this.categoria = categoria;
		this.capacidad = capacidad;
		this.precio = precio;
	}
	
	public char getCategoria() {
		return categoria;
	}
	public void setCategoria(char categoria) {
		this.categoria = categoria;
	}
	public int getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	
}
