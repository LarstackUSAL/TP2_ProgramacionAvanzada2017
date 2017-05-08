package ar.edu.usal.hotel.view;

import ar.edu.usal.hotel.utils.Validador;

public class MenuView {

	public int mostrarMenu() {

		String[] menu = {

				"1 - Check-in.",
				"2 - Registrar consumo.",
				"3 - Consultar habitaciones.",
				"4 - Consultar consumos."
		};

		System.out.println();
		for (int i = 0; i < menu.length; i++) {

			System.out.println(menu[i]);
		}
		System.out.println();
		int opcion = Validador.insertInt("Elegir una opcion: ", 1, menu.length, false);

		return opcion;
	}
}
