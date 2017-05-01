package ar.edu.usal.hotel.model.dto;

import java.util.ArrayList;
import java.util.Calendar;

public class ClientesHabitacionDto {

	private ArrayList<ClientesDto> clientes;
	private HabitacionesDto habitacion;
	private int diasPermanencia;
	private Calendar fechaEgreso;
	private Calendar fechaIngreso;
	private ArrayList<ConsumosDto> consumos;
	
	public ClientesHabitacionDto(){}
	
	public ClientesHabitacionDto(ArrayList<ClientesDto> clientes,
			HabitacionesDto habitacion, int diasPermanencia,
			Calendar fechaEgreso, Calendar fechaIngreso,
			ArrayList<ConsumosDto> consumos) {
		super();
		this.clientes = clientes;
		this.habitacion = habitacion;
		this.diasPermanencia = diasPermanencia;
		this.fechaEgreso = fechaEgreso;
		this.fechaIngreso = fechaIngreso;
		this.consumos = consumos;
	}

	public ArrayList<ClientesDto> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<ClientesDto> clientes) {
		this.clientes = clientes;
	}

	public HabitacionesDto getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(HabitacionesDto habitacion) {
		this.habitacion = habitacion;
	}

	public int getDiasPermanencia() {
		return diasPermanencia;
	}

	public void setDiasPermanencia(int diasPermanencia) {
		this.diasPermanencia = diasPermanencia;
	}

	public Calendar getFechaEgreso() {
		return fechaEgreso;
	}

	public void setFechaEgreso(Calendar fechaEgreso) {
		this.fechaEgreso = fechaEgreso;
	}

	public Calendar getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Calendar fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public ArrayList<ConsumosDto> getConsumos() {
		return consumos;
	}

	public void setConsumos(ArrayList<ConsumosDto> consumos) {
		this.consumos = consumos;
	}
	
	
}
