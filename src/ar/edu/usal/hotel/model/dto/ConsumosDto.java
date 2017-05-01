package ar.edu.usal.hotel.model.dto;

import java.util.Calendar;

public class ConsumosDto {

	private Calendar fecha;
	private ProductosDto producto;
	private int cantidad;
	
	public ConsumosDto(){}
	
	public ConsumosDto(Calendar fecha, int cantidad) {
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

	public ProductosDto getProducto() {
		return producto;
	}

	public void setProducto(String codigo, String descripcion, double precio) {
		
		ProductosDto producto = new ProductosDto(codigo, descripcion, precio);
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
	
	
	
}
