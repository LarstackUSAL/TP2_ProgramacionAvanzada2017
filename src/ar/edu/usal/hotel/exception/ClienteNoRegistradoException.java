package ar.edu.usal.hotel.exception;

import java.io.IOException;
import java.util.Calendar;

import ar.edu.usal.hotel.model.dao.ClientesDao;
import ar.edu.usal.hotel.model.dto.Clientes;
import ar.edu.usal.hotel.view.CheckInView;

public class ClienteNoRegistradoException extends Exception {

	private int numeroDocumento;
	
	public ClienteNoRegistradoException(int numeroDocumento){
		
		this.numeroDocumento = numeroDocumento;
	}
	
	public Clientes registrarCliente(CheckInView checkInView) {
		
		String nombre = checkInView.ingresarNombre();
		String apellido = checkInView.ingresarApellido();
		Calendar fechaNacimiento = checkInView.ingresarFechaNacimiento();
		
		Clientes cliente = new Clientes(numeroDocumento, nombre, apellido, fechaNacimiento);
		
		ClientesDao clientesDao = ClientesDao.getInstance();
		try {
			
			clientesDao.agregarClienteAlArchivo(cliente);
		
		} catch (IOException e) {
			
			System.out.println("Se ha verificado un error al agregar el nuevo cliente al archivo.");
		}		
		
		return cliente;
	}

	@Override
	public void printStackTrace()
	{
		System.out.println("El cliente con numero de documento " + numeroDocumento + " no se encuentra registrado.");
	}

}
