package ar.edu.usal.hotel.model.dto;

import java.util.ArrayList;
import java.util.Calendar;

public class ClientesDto {

	private String nombre;
	private String apellido;
	private Calendar fechaNacimiento;
	private ArrayList<CuponesDto> cupones;
	
	public ClientesDto(){}
	
	public ClientesDto(String nombre, String apellido,
			Calendar fechaNacimiento, ArrayList<CuponesDto> cupones) {
		
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.cupones = cupones;
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
	public ArrayList<CuponesDto> getCupones() {
		return cupones;
	}
	public void setCupones(ArrayList<CuponesDto> cupones) {
		this.cupones = cupones;
	}
	
	
}
