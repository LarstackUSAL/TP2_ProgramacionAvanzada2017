package ar.edu.usal.hotel.exception;

public class HabitacionSinConsumoException extends Exception {

	private int numeroHabitacion;
	
	
	public HabitacionSinConsumoException(int numeroHabitacion) {
		
		super();
		this.numeroHabitacion = numeroHabitacion;
	}

	@Override
	public void printStackTrace()
	{
		System.out.println("La habitacion numero " + numeroHabitacion + " no tiene consumos cargados.");
	}
}
