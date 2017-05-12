package ar.edu.usal.hotel.view;

import java.util.ArrayList;

import ar.edu.usal.hotel.utils.Validador;

public class ConsultarConsumosView {

	public ConsultarConsumosView(){}

	public void mostrarConsumos(ArrayList<String> clientesList, ArrayList<String> consumosList, String precioTotalGeneral) {
		
		System.out.println("CLIENTES DE LA HABITACION:");
		
		for (int i = 0; i < clientesList.size(); i++) {
			
			System.out.println(clientesList.get(i));
		}
		
		System.out.println();
		System.out.println("CONSUMOS:");
		for (int i = 0; i < consumosList.size(); i++) {
			
			System.out.println("Fecha: " + consumosList.get(i));
		}
		
		if(consumosList == null || consumosList.isEmpty()){
			
			System.out.println("La habitacion no posee consumos.");
		}
		
		System.out.println("Precio total de los consumos: " + precioTotalGeneral);
	}

	public int insertNumeroHabitacion(ArrayList<String> habitacionesList) {
		
		System.out.println("HABITACIONES ELEGIDAS POR LOS CLIENTES:");
		for (int i = 0; i < habitacionesList.size(); i++) {
			
			System.out.println(habitacionesList.get(i));
		}
		
		return Validador.insertInt("Ingresar numero habitacion: ", null, null, false);
	}
}
