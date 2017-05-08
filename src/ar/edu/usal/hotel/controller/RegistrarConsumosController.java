package ar.edu.usal.hotel.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ar.edu.usal.hotel.exception.ClienteNoRegistradoException;
import ar.edu.usal.hotel.exception.ProductoInexistenteException;
import ar.edu.usal.hotel.model.dao.ClientesDao;
import ar.edu.usal.hotel.model.dao.ClientesHabitacionDao;
import ar.edu.usal.hotel.model.dao.ConsumosDao;
import ar.edu.usal.hotel.model.dao.ProductosDao;
import ar.edu.usal.hotel.model.dto.Clientes;
import ar.edu.usal.hotel.model.dto.ClientesHabitacion;
import ar.edu.usal.hotel.model.dto.Consumos;
import ar.edu.usal.hotel.model.dto.Habitaciones;
import ar.edu.usal.hotel.model.dto.Productos;
import ar.edu.usal.hotel.view.RegistrarConsumosView;

public class RegistrarConsumosController {

	private RegistrarConsumosView registrarConsumosView;

	public RegistrarConsumosController() {
		super();
	}
	
	public void registrarConsumos(){
		
		Clientes cliente = this.loadCliente();
		
		if(cliente!=null){
			
			ClientesHabitacionDao clientesHabitacionDao = ClientesHabitacionDao.getInstance();
			Habitaciones habitacionConsumos = clientesHabitacionDao.loadHabitacionDelCliente(cliente);
			
			boolean seguirConsumiendo = true;
			do{
				
				Consumos consumo = this.consumirProducto();
				
				ConsumosDao consumosDao = ConsumosDao.getInstance(habitacionConsumos.getNumero());
				try {
					consumosDao.grabarConsumo(consumo, habitacionConsumos.getNumero());
				
				} catch (IOException e) {
				
					System.out.println("Se ha verificado un error al grabar el consumo en el archivo.");
				}
				
				seguirConsumiendo = registrarConsumosView.realizarOtroConsumo();
				
			}while(seguirConsumiendo);
			
		}
		
	}

	private Clientes loadCliente() {

		ClientesDao clientesDao = ClientesDao.getInstance();
		ArrayList<Clientes> clientes = clientesDao.getClientes();
		ClientesHabitacionDao clientesHabitacionDao = ClientesHabitacionDao.getInstance();
		ArrayList<ClientesHabitacion> clientesHabitacion = clientesHabitacionDao.getClientesHabitacion();
		ArrayList<String> clientesList = new ArrayList<String>();
		
		Clientes clienteCargado = null;
		
		for (int i = 0; i < clientesHabitacion.size(); i++) {
			
			ClientesHabitacion clientesHabitacionIterada = clientesHabitacion.get(i);
			
			for (int j = 0; j < clientesHabitacionIterada.getClientes().size(); j++) {

				Clientes cliente = clientesHabitacionIterada.getClientes().get(j);
				
				String datosCliente = 
					"Numero Documento: " + cliente.getNumeroDocumento() + "\n" + 
							"Nombre: " + cliente.getNombre() + "\n" +
							"Apellido: " + cliente.getApellido() + "\n"+
							"\n";
					
				clientesList.add(datosCliente);
			}
		}
		
		if(clientesList.isEmpty()){
			
			registrarConsumosView.noHayClientesEnHotel();
			
		}else{
			
			boolean numeroValido = false;
			
			do{
				int numeroDocumento = registrarConsumosView.ingresarNumeroDocumento(clientesList);
			
				try {
					
					clienteCargado = clientesDao.loadClientePorNumeroDocumento(numeroDocumento);
					numeroValido = true;
					
				} catch (ClienteNoRegistradoException e) {
					
					e.printStackTrace();
				}
			
			}while(!numeroValido);
		}
		
		return clienteCargado;
	}
	
	private Consumos consumirProducto() {
		
		ProductosDao productosDao = ProductosDao.getInstance();
		ArrayList<Productos> productos = productosDao.getProductos();
		Productos productoCargado = null;
		
		ArrayList<String> productosString = new ArrayList();
		
		for (int i = 0; i < productos.size(); i++) {
			
			Productos producto = productos.get(i);
			
			String datosProducto= 
					"Descripcion: " + producto.getDescripcion().trim() + "\n" + 
					"Codigo: " + producto.getCodigo().trim() + "\n" +
					"Precio: " + String.valueOf(producto.getPrecio()).trim() + "\n"+
					"\n";
					
			productosString.add(datosProducto);
		}
		
		if(productosString.isEmpty()){
			
			registrarConsumosView.noHayProductos();
			
		}else{
			
			boolean codigoValido = false;
			
			do{
				String codigoProducto = registrarConsumosView.ingresarCodigoProducto(productosString);
			
				try {
					
					productoCargado = productosDao.loadProductoPorCodigo(codigoProducto);
					codigoValido = true;
					
				} catch (ProductoInexistenteException e) {
					
					e.printStackTrace();
				}
			
			}while(!codigoValido);
		}
		
		int cantidadConsumida = registrarConsumosView.ingresarCantidadConsumida();
		
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(new Date());
		
		return new Consumos(fecha, productoCargado, cantidadConsumida);
	}
}
