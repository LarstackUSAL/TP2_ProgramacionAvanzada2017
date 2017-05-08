package ar.edu.usal.hotel.controller;

import ar.edu.usal.hotel.model.dao.ClientesDao;
import ar.edu.usal.hotel.model.dao.HabitacionesDao;
import ar.edu.usal.hotel.view.MenuView;

public class MenuController {

	public MenuController(){}

	public static void main(String[] args) {
		
		MenuView menuView = new MenuView();
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
		case 0:

//			SalidaSistemaController();
			break;
		}
	}
}
