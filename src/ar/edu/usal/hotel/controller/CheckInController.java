package ar.edu.usal.hotel.controller;

import java.util.ArrayList;
import java.util.Calendar;

import ar.edu.usal.hotel.model.dao.ClientesDao;
import ar.edu.usal.hotel.model.dao.HabitacionesDao;
import ar.edu.usal.hotel.model.dto.ClientesDto;
import ar.edu.usal.hotel.model.dto.CuponesDto;
import ar.edu.usal.hotel.utils.Validador;
import ar.edu.usal.hotel.view.CheckInView;

public class CheckInController {

	private CheckInView checkInView;
	
	public void prepararCheckIn() {

		ArrayList<E>
		checkInView = new CheckInView();
		boolean agregarOtroCliente = true;
		
		do{
			int numeroDocumento = checkInView.ingresarDatosPreliminares();
			
			boolean esClienteRegistrado = this.checkClienteRegistrado(numeroDocumento);
			
			if(!esClienteRegistrado){
				
				//!!!!LOS CLIENTES NUEVOS AGREGARLOS AL ARRAY nuevosClientes
				this.registrarCliente(); //se ingresan los datos por separados del cliente 
			}
			
			agregarOtroCliente = checkInView.agregarOtroPasajero() //pregunta si desea agregar pasajero
			
		}while(agregarOtroCliente);
	}

	private boolean checkClienteRegistrado(int numeroDocumento){

		String mensajeRetornado = "";
		
		HabitacionesDao habitacionesDao = HabitacionesDao.getInstance();
		ClientesDao clientes = ClientesDao.getInstance();

		for (int i = 0; i < clientes.getClientes().size(); i++) {

			ClientesDto cliente = clientes.getClientes().get(i);

			if(cliente.getNumeroDocumento() == numeroDocumento){

				Calendar fechaNacimientoCalendar = cliente.getFechaNacimiento();
				String fechaNacimiento = Validador.darFormatoFechaCalendar(fechaNacimientoCalendar, "dd-MM-yyyy");

				CuponesDto cuponCliente = cliente.getCupon();
				String tieneCupon = cuponCliente!=null ? "El cliente tiene cupones de descuento." : 
					"El cliente no posee cupones de descuento.";

				mensajeRetornado = 
						"Nombre: " + cliente.getNombre() + "\n"
								+ "Apellido: " + cliente.getApellido() + "\n"
								+ "Fecha Nacimiento: " + fechaNacimiento + "\n"
								+ tieneCupon;

				checkInView.mostrarDatosCliente();
				return true;
			}
		}

		return false;
	}
}

