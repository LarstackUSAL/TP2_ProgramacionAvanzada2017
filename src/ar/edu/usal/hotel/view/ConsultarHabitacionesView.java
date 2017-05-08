package ar.edu.usal.hotel.view;

import java.util.ArrayList;

public class ConsultarHabitacionesView {

	public ConsultarHabitacionesView(){}

	public void mostrarHabitaciones(ArrayList<String> habitacionesList) {
		
		for (int i = 0; i < habitacionesList.size(); i++) {
			
			System.out.println(habitacionesList.get(i));
		}
	}
	
}
