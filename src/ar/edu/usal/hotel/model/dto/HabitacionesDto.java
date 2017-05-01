package ar.edu.usal.hotel.model.dto;

public class HabitacionesDto {

	private int numero;
	private int capacidad;
	private char categoria;
	private boolean tieneBalcon;
	private String comentario;
	private PreciosDto precio;
	
	public HabitacionesDto(){}
	
	public HabitacionesDto(int numero, int capacidad, char categoria,
			boolean tieneBalcon, String comentario) {
		super();
		this.numero = numero;
		this.capacidad = capacidad;
		this.categoria = categoria;
		this.tieneBalcon = tieneBalcon;
		this.comentario = comentario;
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

	public PreciosDto getPrecio() {
		return precio;
	}

	public void setPrecio(PreciosDto precio) {
		this.precio = precio;
	}
	
	
}
