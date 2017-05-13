package ar.edu.usal.hotel.view;

import java.util.ArrayList;

import ar.edu.usal.hotel.utils.Validador;

public class RegistrarConsumosView {

	public RegistrarConsumosView(){}

	public void noHayClientesEnHotel() {
		
		System.out.println("No se pueden realizar consumos ya que no hay clientes en el hotel.");
	}
	
	public int ingresarNumeroDocumento(ArrayList<String> clientesList) {
		
		System.out.println("\nLISTA DE CLIENTES EN EL HOTEL: ");
		
		for (int i = 0; i < clientesList.size(); i++) {
			
			System.out.println(clientesList.get(i));
		}
		
		int numeroDocumento = Validador.insertInt("Ingresar numero de documento del cliente: ", 1, 99999999, false);
		
		return numeroDocumento;
	}

	public void noHayProductos() {

		System.out.println("No se pueden realizar consumos, ya que no hay productos cargados en el sistema.");
	}

	public String ingresarCodigoProducto(ArrayList<String> productosList) {
	
		for (int i = 0; i < productosList.size(); i++) {
			
			System.out.println(productosList.get(i));
		}
		
		String codigo = Validador.insertString("Ingresar el codigo del producto: ", null);
		
		return codigo;
	}

	public int ingresarCantidadConsumida() {
		
		return Validador.insertInt("Ingresar la cantidad consumida:", 1, null, false);
	}

	public boolean realizarOtroConsumo() {
		
		return Validador.insertBooleanSyN("Quiere registrar otro consumo? (s/n):");
	}

	public void registroConsumoSuccess() {
		
		System.out.println("Se han registrado los consumos correctamente.");
	}
	
}
