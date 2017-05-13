package ar.edu.usal.hotel.model.dto;

import java.util.ArrayList;
import java.util.Calendar;

import ar.edu.usal.hotel.model.dao.ClientesHabitacionDao;

public class ClientesHabitacion {
	
	private int idEstadia;
	private ArrayList<Clientes> clientes;
	private Habitaciones habitacion;
	private int diasPermanencia;
	private Calendar fechaEgreso;
	private Calendar fechaIngreso;
	private boolean estadiaEnCurso;
	private ArrayList<Consumos> consumos = new ArrayList();
	private Clientes clienteResponsable;
	
	public ClientesHabitacion(){}
	
	public ClientesHabitacion(Clientes clienteResponsable, ArrayList<Clientes> clientes,
			Habitaciones habitacion, int diasPermanencia,
			Calendar fechaEgreso, Calendar fechaIngreso, int idEstadia) {
		
		super();
		this.idEstadia = idEstadia;
		this.setClienteResponsable(clienteResponsable);
		this.clientes = clientes;
		this.habitacion = habitacion;
		this.diasPermanencia = diasPermanencia;
		this.fechaEgreso = fechaEgreso;
		this.fechaIngreso = fechaIngreso;
		this.setEstadiaEnCurso(true);
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

	public Clientes getClienteResponsable() {
		return clienteResponsable;
	}

	public void setClienteResponsable(Clientes clienteResponsable) {
		this.clienteResponsable = clienteResponsable;
	}

	public Consumos createConsumo(Calendar fecha, Productos productoConsumido, int cantidad) {
		
		Consumos consumo = new Consumos(fecha, productoConsumido, cantidad); 
		this.consumos.add(consumo);
		
		return consumo;
	}

	public boolean isEstadiaEnCurso() {
		return estadiaEnCurso;
	}

	public void setEstadiaEnCurso(boolean estadiaEnCurso) {
		this.estadiaEnCurso = estadiaEnCurso;
	}

	public int getIdEstadia() {
		return idEstadia;
	}

	public void setIdEstadia(int idEstadia) {
		this.idEstadia = idEstadia;
	}
}
