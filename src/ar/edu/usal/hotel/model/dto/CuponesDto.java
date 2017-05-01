package ar.edu.usal.hotel.model.dto;

import java.util.Calendar;

public class CuponesDto {

	private boolean esUtilizado;
	private double descuento;
	private Calendar fechaNacimiento;
	
	public CuponesDto(){}
	
	public CuponesDto(boolean esUtilizado, double descuento,
			Calendar fechaNacimiento) {
		super();
		this.esUtilizado = esUtilizado;
		this.descuento = descuento;
		this.fechaNacimiento = fechaNacimiento;
	}

	public boolean isEsUtilizado() {
		return esUtilizado;
	}

	public void setEsUtilizado(boolean esUtilizado) {
		this.esUtilizado = esUtilizado;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public Calendar getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Calendar fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	
}
