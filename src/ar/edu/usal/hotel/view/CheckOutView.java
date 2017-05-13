package ar.edu.usal.hotel.view;

import java.util.ArrayList;

import ar.edu.usal.hotel.utils.Validador;

public class CheckOutView {

	public CheckOutView(){}

	public int insertNumeroHabitacion(ArrayList<String> habitacionesList) {
		
		System.out.println();
		System.out.println("HABITACIONES ELEGIDADS POR LOS CLIENTES:");
		for (int i = 0; i < habitacionesList.size(); i++) {
			
			System.out.println(habitacionesList.get(i));
		}
		
		return Validador.insertInt("Ingresar numero habitacion: ", null, null, false);
	}

	public void errorActualizarCupones() {

		System.out.println("Se ha verificado un error al actualizar el archivo de cupones.");
		System.out.println("El descuento no ser&aacute aplicado.");
	}

	public void mostrarImporteTotal(String importeTotal) {

		System.out.println("El check-out se ha realizado con exito.");
		System.out.println("El importe a abonar es de " + importeTotal);
		
	}

	public void habitacionNoOcupada(int numeroHabitacion) {
		
		System.out.println("La habitacion " + numeroHabitacion + " no se encuentra ocupada actualmente.");
	}
}
