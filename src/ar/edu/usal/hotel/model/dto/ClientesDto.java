package ar.edu.usal.hotel.model.dto;

import java.util.Calendar;

public class ClientesDto {

	private int numeroDocumento;
	private String nombre;
	private String apellido;
	private Calendar fechaNacimiento;
	private CuponesDto cupon;
	
	public ClientesDto(){}
	
	public ClientesDto(int numeroDocumento, String nombre, String apellido,
			Calendar fechaNacimiento, CuponesDto cupon) {
		
		super();
		this.setNumeroDocumento(numeroDocumento);
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.cupon = cupon;
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

	public CuponesDto getCupon() {
		return cupon;
	}

	public void setCupon(CuponesDto cupon) {
		this.cupon = cupon;
	}

	public int getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(int numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
}
