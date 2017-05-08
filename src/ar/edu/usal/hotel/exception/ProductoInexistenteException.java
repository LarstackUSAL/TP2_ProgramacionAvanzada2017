package ar.edu.usal.hotel.exception;

public class ProductoInexistenteException extends Exception {

	private String codigo;
	
	public ProductoInexistenteException(String codigo){
		
		this.codigo = codigo;
	}
	
	@Override
	public void printStackTrace()
	{
		System.out.println("El producto con codigo " + codigo + " es inexistente.");
	}
}
