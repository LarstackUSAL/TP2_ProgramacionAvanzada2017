package ar.edu.usal.hotel.model.dto;

import java.util.Calendar;

public class Consumos {

	private Calendar fecha;
	private Productos producto;
	private int cantidad;
	
	public Consumos(){}
	
	public Consumos(Calendar fecha, int cantidad) {
		super();
		this.fecha = fecha;
		this.cantidad = cantidad;
	}

	public Calendar getFecha() {
		return fecha;
	}

	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}

	public Productos getProducto() {
		return producto;
	}

	public void setProducto(String codigo, String descripcion, double precio) {
		
		Productos producto = new Productos(codigo, descripcion, precio);
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
	
	
	
}
