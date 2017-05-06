package ar.edu.usal.hotel.model.dto;

import java.util.ArrayList;
import java.util.Calendar;

public class ClientesHabitacion {

	private ArrayList<Clientes> clientes;
	private Habitaciones habitacion;
	private int diasPermanencia;
	private Calendar fechaEgreso;
	private Calendar fechaIngreso;
	private ArrayList<Consumos> consumos;
	
	public ClientesHabitacion(){}
	
	public ClientesHabitacion(ArrayList<Clientes> clientes,
			Habitaciones habitacion, int diasPermanencia,
			Calendar fechaEgreso, Calendar fechaIngreso,
			ArrayList<Consumos> consumos) {
		super();
		this.clientes = clientes;
		this.habitacion = habitacion;
		this.diasPermanencia = diasPermanencia;
		this.fechaEgreso = fechaEgreso;
		this.fechaIngreso = fechaIngreso;
		this.consumos = consumos;
	}

	public ArrayList<Clientes> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<Clientes> clientes) {
		this.clientes = clientes;
	}

	public Habitaciones getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitaciones habitacion) {
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

	public ArrayList<Consumos> getConsumos() {
		return consumos;
	}

	public void setConsumos(ArrayList<Consumos> consumos) {
		this.consumos = consumos;
	}
	
	
}
