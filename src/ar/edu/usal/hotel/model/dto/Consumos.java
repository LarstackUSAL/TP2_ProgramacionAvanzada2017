package ar.edu.usal.hotel.model.dto;

import java.util.Calendar;

public class Consumos {

	private Calendar fecha;
	private Productos producto;
	private int cantidad;
	
	public Consumos(){}
	
	public Consumos(Calendar fecha, Productos producto, int cantidad) {
		super();
		this.fecha = fecha;
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public Calendar getFecha() {
		return fecha;
	}

	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Productos getProducto() {
		return producto;
	}

	public void setProducto(Productos producto) {
		this.producto = producto;
	}	
}
