package ar.edu.usal.hotel.exception;

public class NumeroHabitacionNoValidoException extends Exception {

	private int numeroIngresado;
	
	
	public NumeroHabitacionNoValidoException(int numeroIngresado) {
		
		super();
		this.numeroIngresado = numeroIngresado;
	}

	@Override
	public void printStackTrace()
	{
		System.out.println("El numero " + numeroIngresado + " no es de una habitacion valida.");
	}
}
