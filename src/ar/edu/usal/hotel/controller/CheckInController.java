package ar.edu.usal.hotel.controller;

import ar.edu.usal.hotel.model.dao.ClientesDao;
import ar.edu.usal.hotel.model.dao.HabitacionesDao;
import ar.edu.usal.hotel.view.CheckInView;

public class CheckInController {

	public void prepararCheckIn() {
		
		CheckInView checkInView = new CheckInView();
		
		int numeroDocumento = checkInView.ingresarDatosPreliminares();
		
		HabitacionesDao habitacionesDao = HabitacionesDao.getInstance();
		
		ClientesDao clientes = ClientesDao.getInstance();
		
		
	}

}
