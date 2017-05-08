package ar.edu.usal.hotel.view;

import java.util.ArrayList;

import ar.edu.usal.hotel.utils.Validador;

public class ConsultarConsumosView {

	public ConsultarConsumosView(){}

	public void mostrarConsumos(ArrayList<String> clientesList, ArrayList<String> consumosList) {
		
		for (int i = 0; i < clientesList.size(); i++) {
			
			System.out.println(clientesList.get(i));
		}
		
		for (int i = 0; i < consumosList.size(); i++) {
			
			System.out.println(consumosList.size());
		}
		
	}

	public int insertNumeroHabitacion(ArrayList<String> habitacionesList) {
		
		for (int i = 0; i < habitacionesList.size(); i++) {
			
			System.out.println(habitacionesList.get(i));
		}
		
		return Validador.insertInt("Ingresar numero habitacion: ", null, null, false);
	}
}
