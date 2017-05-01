package ar.edu.usal.hotel.view;

import ar.edu.usal.hotel.utils.Validador;

public class CheckInView {

	public CheckInView(){}
	
	public int ingresarDatosPreliminares() {
		
		int numeroDocumento = Validador.insertInt("Ingresar numero de documento: ", 1, 99999999, false);
		
		return numeroDocumento;
	}

	
}
