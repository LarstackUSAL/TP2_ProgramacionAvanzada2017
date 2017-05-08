package ar.edu.usal.hotel.view;

import java.util.ArrayList;

import ar.edu.usal.hotel.utils.Validador;

public class CheckOutView {

	public CheckOutView(){}

	public int insertNumeroHabitacion(ArrayList<String> habitacionesList) {
		
		for (int i = 0; i < habitacionesList.size(); i++) {
			
			System.out.println(habitacionesList.get(i));
		}
		
		return Validador.insertInt("Ingresar numero habitacion: ", null, null, false);
	}
	
	
}
