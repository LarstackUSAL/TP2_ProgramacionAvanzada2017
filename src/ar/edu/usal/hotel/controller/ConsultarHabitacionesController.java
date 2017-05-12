package ar.edu.usal.hotel.controller;

import java.util.ArrayList;

import ar.edu.usal.hotel.model.dao.HabitacionesDao;
import ar.edu.usal.hotel.model.dto.Habitaciones;
import ar.edu.usal.hotel.view.ConsultarHabitacionesView;

public class ConsultarHabitacionesController {

	private ConsultarHabitacionesView consultarHabitacionesView;
	
	public ConsultarHabitacionesController(){
		
		this.consultarHabitacionesView = new ConsultarHabitacionesView();
	}

	public void consultarHabitaciones() {

		HabitacionesDao habitacionesDao = HabitacionesDao.getInstance();
		
		Habitaciones[] habitaciones = habitacionesDao.getHabitaciones();
		
		ArrayList<String> habitacionesString = new ArrayList<String>();
		
		for (int i = 0; i < habitaciones.length; i++) {
			
			Habitaciones habitacion = habitaciones[i];

			String datosHabitacion = 
					"Numero habitacion: " + habitacion.getNumero() + "\n" + 
							"Capacidad maxima: " + habitacion.getCapacidad() + "\n" +
							"Categoria: " + habitacion.getCategoria() + "\n"+
							"Precio: " + habitacion.getPrecio().getPrecio() + "\n"+
							"Comentario: " + habitacion.getComentario() + "\n"+
							"\n";
					
				habitacionesString.add(datosHabitacion);
		}
		
		consultarHabitacionesView.mostrarHabitaciones(habitacionesString);
	}
	
	
}
