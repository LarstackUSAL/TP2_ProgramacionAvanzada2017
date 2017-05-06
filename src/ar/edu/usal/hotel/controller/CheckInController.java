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
	private ArrayList<ClientesDto> clientesCheckIn = new ArrayList();
	private ArrayList<ClientesDto> nuevosClientes = new ArrayList();

	public void checkIn() {
		
		this.prepararDatosClientes();
		this.asignarHabitacion();
	}

	public void prepararDatosClientes() {

		checkInView = new CheckInView();
		boolean agregarOtroCliente = true;
		
		do{
			int numeroDocumento = checkInView.ingresarDatosPreliminares();
			
			boolean esClienteRegistrado = this.checkClienteRegistrado(numeroDocumento);
			
			if(!esClienteRegistrado){
				
				ClientesDto nuevoCliente = this.registrarCliente(numeroDocumento);
				
				this.nuevosClientes.add(nuevoCliente);
				this.clientesCheckIn.add(nuevoCliente);
			}
			
			agregarOtroCliente = checkInView.agregarOtroPasajero();
			
		}while(agregarOtroCliente);
	}

	private boolean checkClienteRegistrado(int numeroDocumento){

		String datosCliente = "";
		
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

				datosCliente = 
						"Nombre: " + cliente.getNombre() + "\n"
								+ "Apellido: " + cliente.getApellido() + "\n"
								+ "Fecha Nacimiento: " + fechaNacimiento + "\n"
								+ tieneCupon;

				checkInView.mostrarDatosCliente(datosCliente);
				
				return true;
			}
		}

		checkInView.mostrarDatosCliente(datosCliente);
		
		return false;
	}
	
	private ClientesDto registrarCliente(int numeroDocumento) {
		
		String nombre = checkInView.ingresarNombre();
		String apellido = checkInView.ingresarApellido();
		Calendar fechaNacimiento = checkInView.ingresarFechaNacimiento();
		
		return new ClientesDto(numeroDocumento, nombre, apellido, fechaNacimiento);
	}
	
	private void asignarHabitacion() {
		
		boolean categoriaValida = false; 
		
		do{
			
			char categoria = checkInView.ingresarCategoria();
					
			for (int i = 0; i < HabitacionesDao.CATEGORIAS_VALIDAS.length; i++) {
				
				if(categoria == HabitacionesDao.CATEGORIAS_VALIDAS[i]){
					
					categoriaValida = true;
					break;
				}
			}
			
		}while(!categoriaValida);
			
		int diasPermanencia = checkInView.ingresarDiasPermanencia();
		boolean tieneBalcon = checkInView.quiereBalcon();
		int capacidad = clientesCheckIn.size();
		
		//BUSCAR UNA HABITACION con estos parametros y asignarla...sino aconsejar otro tipo de habitacion (misma capacidad) 
		
	}
}

