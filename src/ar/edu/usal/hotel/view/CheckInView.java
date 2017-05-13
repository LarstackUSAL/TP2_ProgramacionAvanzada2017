package ar.edu.usal.hotel.view;

import java.util.Calendar;
import java.util.List;

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

	public int ingresarHabitacionRequerida(List habitaciones) {
		
		System.out.println("No tenemos una habitacion disponible acorde al criterio ingresado.");
		System.out.println("Le aconsejamos las siguientes habitaciones:");
		
		for (int i = 0; i < habitaciones.size(); i++) {
			
			System.out.println(habitaciones.get(i));
		}
		
		return Validador.insertInt("Ingresar numero habitacion requerida: ", null, null, false);
	}

	public void mostrarFechaEgreso(String fechaEgresoString) {

		if(fechaEgresoString != null){
			
			System.out.println("Check-in realizado correctamente. La fecha de egreso es: " + fechaEgresoString);
		}else{
			
			System.out.println("No hay habitaciones disponibles con esa capacidad.");
		}		
	}

	public void errorActualizarArchivo() {

		System.out.println("Se ha verificado un error al actualizar el archivo de habitaciones.");
		System.out.println("El check-in no ser&aacute efectuado.");		
	}

	public void clienteYaAlojado(String cliente) {
		
		System.out.println("El cliente " + cliente + " ya se encuentra alojado en el hotel o pendiente de check-in.");
	}
}
