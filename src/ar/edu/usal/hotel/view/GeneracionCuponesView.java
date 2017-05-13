package ar.edu.usal.hotel.view;

public class GeneracionCuponesView {

	public GeneracionCuponesView(){}

	public void cuponGenerado(String cuponGenerado) {
		
		System.out.println(cuponGenerado);
	}

	public void mesNoValido(String mes) {
		
		System.out.println("El valor " + mes + " ingresado como argumento no es un mes valido.");
	}	
}
