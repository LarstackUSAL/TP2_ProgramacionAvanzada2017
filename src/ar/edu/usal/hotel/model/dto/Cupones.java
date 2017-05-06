package ar.edu.usal.hotel.model.dto;

import java.util.Calendar;

public class Cupones {

	private int numeroDocumento;		
	private String nombre;				
	private String apellido;
	private Calendar fechaCheckIn;
	private double totalConsumido;
	private double descuentoCalculado;
	private Calendar fechaVencimiento;

	public Cupones(){}

	public Cupones(int numeroDocumento, String nombre, String apellido,
			Calendar fechaCheckIn, double totalConsumido,
			double descuentoCalculado, Calendar fechaVencimiento) {
		
		super();
		this.numeroDocumento = numeroDocumento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaCheckIn = fechaCheckIn;
		this.totalConsumido = totalConsumido;
		this.descuentoCalculado = descuentoCalculado;
		this.fechaVencimiento = fechaVencimiento;
	}

	public int getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(int numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Calendar getFechaCheckIn() {
		return fechaCheckIn;
	}

	public void setFechaCheckIn(Calendar fechaCheckIn) {
		this.fechaCheckIn = fechaCheckIn;
	}

	public double getTotalConsumido() {
		return totalConsumido;
	}

	public void setTotalConsumido(double totalConsumido) {
		this.totalConsumido = totalConsumido;
	}

	public double getDescuentoCalculado() {
		return descuentoCalculado;
	}

	public void setDescuentoCalculado(double descuentoCalculado) {
		this.descuentoCalculado = descuentoCalculado;
	}

	public Calendar getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Calendar fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
}
