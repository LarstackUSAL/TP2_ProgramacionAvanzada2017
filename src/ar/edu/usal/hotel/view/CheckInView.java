package ar.edu.usal.hotel.view;

import java.util.Calendar;

import ar.edu.usal.hotel.utils.Validador;

public class CheckInView {

	public CheckInView(){}
	
	public int ingresarDatosPreliminares() {
		
		int numeroDocumento = Validador.insertInt("Ingresar numero de documento: ", 1, 99999999, false);
		
		return numeroDocumento;
	}

	public void mostrarDatosCliente(String datosCliente) {
		
		if(datosCliente.trim().isEmpty()) datosCliente = "El cliente no se encuentra registrado.";
			
		System.out.println(datosCliente);	
	}

	public String ingresarNombre() {
		
		return Validador.insertString("Ingresar nombre:", 20);		
	}

	public String ingresarApellido() {
		
		return Validador.insertString("Ingresar apellido:", 20);
	}

	public Calendar ingresarFechaNacimiento() {
				
		return Validador.insertFecha("Ingresar fecha de nacimiento:");
	}

	public boolean agregarOtroPasajero() {

		return Validador.insertBooleanSyN("Agregar otro pasajero? (s/n):");
	}

	public char ingresarCategoria() {
		
		return Validador.insertChar("Ingresar la categoria requerida: ");		 
	}

	public int ingresarDiasPermanencia() {
		
		return Validador.insertInt("Ingresar dias de permanencia:", 1, null, false);
	}

	public boolean quiereBalcon() {
		
		return Validador.insertBooleanSyN("Quiere que la habitacion tenga balcon? (s/n):");
	}

	
}
