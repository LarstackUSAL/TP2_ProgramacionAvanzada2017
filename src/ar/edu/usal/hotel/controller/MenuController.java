package ar.edu.usal.hotel.controller;

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
			checkInController.prepararCheckIn();
			break;
		case 0:

			SalidaSistemaController();
			break;
		}
	}








}
}
