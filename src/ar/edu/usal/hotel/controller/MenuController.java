package ar.edu.usal.hotel.controller;

import ar.edu.usal.hotel.view.MenuView;

public class MenuController {

	public MenuController(){}

	public static void main(String[] args) {

		MenuView menuView = new MenuView();
		boolean salir = false;

		do{

			int opcion = menuView.mostrarMenu();
			switch(opcion) {

			case 1:

				CheckInController checkInController = new CheckInController();
				checkInController.checkIn();
				break;
			case 2:

				RegistrarConsumosController registrarConsumosController = new RegistrarConsumosController();
				registrarConsumosController.registrarConsumos();
				break;
			case 3:

				ConsultarHabitacionesController consultarHabitacionesController = new ConsultarHabitacionesController();
				consultarHabitacionesController.consultarHabitaciones();
				break;
			case 4:

				ConsultarConsumosController consultarConsumosController = new ConsultarConsumosController();
				consultarConsumosController.consultarConsumos();
				break;
			case 5:

				CheckOutController checkOutController = new CheckOutController();
				checkOutController.checkOut();
				break;
			case 6:

				GeneracionCuponesController generacionCuponesController = new GeneracionCuponesController();
				generacionCuponesController.generarCupones(args);
				break;
			case 0:

				salir = true;
				break;
			}

		}while(!salir);
	}
}
