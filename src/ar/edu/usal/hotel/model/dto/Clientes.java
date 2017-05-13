package ar.edu.usal.hotel.model.dto;

import java.util.ArrayList;
import java.util.Calendar;

public class Clientes {

	private int numeroDocumento;
	private String nombre;
	private String apellido;
	private Calendar fechaNacimiento;
	private ArrayList<Cupones> cupones = new ArrayList<Cupones>();
	
	public ArrayList<Cupones> getCupones() {
		return cupones;
	}

	public Clientes(){}
	
	public Clientes(int numeroDocumento, String nombre, String apellido,
			Calendar fechaNacimiento) {
		
		super();
		this.setNumeroDocumento(numeroDocumento);
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
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
	public Calendar getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Calendar fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public int getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(int numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Cupones generarCupon(int numeroDocumento, String nombre, String apellido, Calendar fechaCheckIn, Calendar fechaVencimiento, double totalConsumido, double descuentoCalculado, boolean esUtilizado) {
		
		Cupones cupon = new Cupones(numeroDocumento, nombre, apellido, fechaCheckIn, totalConsumido, descuentoCalculado, fechaVencimiento, esUtilizado);
		this.cupones.add(cupon);
		
		return cupon;
	}
	
}
