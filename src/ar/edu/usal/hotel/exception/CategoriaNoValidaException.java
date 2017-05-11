package ar.edu.usal.hotel.exception;

public class CategoriaNoValidaException extends Exception {
	
	private char categoriaIngresada;
	
	
	public CategoriaNoValidaException(char categoriaIngresada) {
		
		super();
		this.categoriaIngresada = categoriaIngresada;
	}

	@Override
	public void printStackTrace()
	{
		System.out.println("La categoria " + categoriaIngresada + " no es valida.");
	}
}
