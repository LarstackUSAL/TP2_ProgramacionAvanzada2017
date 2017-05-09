package ar.edu.usal.hotel.controller;

import java.io.IOException;
import java.util.ArrayList;

import ar.edu.usal.hotel.exception.NumeroHabitacionNoValidoException;
import ar.edu.usal.hotel.model.dao.ClientesHabitacionDao;
import ar.edu.usal.hotel.model.dao.ConsumosDao;
import ar.edu.usal.hotel.model.dao.CuponesDao;
import ar.edu.usal.hotel.model.dao.HabitacionesDao;
import ar.edu.usal.hotel.model.dto.Clientes;
import ar.edu.usal.hotel.model.dto.ClientesHabitacion;
import ar.edu.usal.hotel.model.dto.Cupones;
import ar.edu.usal.hotel.model.dto.Habitaciones;
import ar.edu.usal.hotel.model.interfaces.ICalculoImportes;
import ar.edu.usal.hotel.view.CheckOutView;

public class CheckOutController  implements ICalculoImportes {

	private CheckOutView checkOutView;
	
	public CheckOutController(){
		
		this.checkOutView = new CheckOutView();
	}
	
	public void checkOut() {
		
		int numeroHabitacion = this.getNumeroHabitacion();
		
		ClientesHabitacionDao clientesHabitacionDao = ClientesHabitacionDao.getInstance();
		
		ClientesHabitacion clientesHabitacion = clientesHabitacionDao.loadClientesHabitacionPorNumero(numeroHabitacion); 
		
		this.calcularImporte(clientesHabitacion);
	}

	@Override
	public double calcularImporte(ClientesHabitacion clientesHabitacion) {
		
		double total = 0.0;
		
		int numeroHabitacion = clientesHabitacion.getHabitacion().getNumero();
		
		HabitacionesDao habitacionesDao = HabitacionesDao.getInstance();
		
		double calculoEstadia = habitacionesDao.calcularImporte(clientesHabitacion);

		if(clientesHabitacion.getDiasPermanencia() > ICalculoImportes.DIAS_PARA_DESCUENTO){
			
			calculoEstadia = calculoEstadia - (calculoEstadia * ICalculoImportes.PORCENTAJE_DESCUENTO_SUPERA_SIETE_DIAS);
		}		
		
		Clientes cliente = clientesHabitacion.getClienteResponsable();
		
		ConsumosDao consumosDao = ConsumosDao.getInstance(numeroHabitacion);
		
		double calculoConsumos = consumosDao.calcularImporte(clientesHabitacion);
		
		CuponesDao cuponesDao = CuponesDao.getInstance();
		
		Cupones cupon = cuponesDao.loadCuponCliente(cliente);
		
		if(cupon != null){
			
			try {
				
				cupon.setEsUtilizado(true);
				cuponesDao.actualizarCupones();
				
				calculoConsumos = calculoConsumos - cupon.getDescuentoCalculado();
				
			} catch (IOException e) {
				
				checkOutView.errorActualizarCupones();
			}
		}
		
		return calculoEstadia + calculoConsumos;
	}
	
	private Integer getNumeroHabitacion() {
		
		int numeroHabitacionRequerida;
		ClientesHabitacionDao clientesHabitacionDao = ClientesHabitacionDao.getInstance();
		ArrayList<ClientesHabitacion> clientesHabitacionList = clientesHabitacionDao.getClientesHabitacion();
		
		ArrayList<Habitaciones> habitacionesList = new ArrayList();
		ArrayList<String> habitacionesString = new ArrayList();
		
		for (int i = 0; i < clientesHabitacionList.size(); i++) {
			
			ClientesHabitacion clientesHabitacion = clientesHabitacionList.get(i);
			
			Habitaciones habitacion = clientesHabitacion.getHabitacion();
			
			String datosHabitacion = 
					"Numero habitacion: " + habitacion.getNumero();
					
			habitacionesString.add(datosHabitacion);
		}
		
		boolean numeroHabitacionValido = false;
		do{
			numeroHabitacionRequerida = checkOutView.insertNumeroHabitacion(habitacionesString);
			
			try {
				
				numeroHabitacionValido = this.validarNumeroHabitacion(habitacionesList, numeroHabitacionRequerida);
			
			} catch (NumeroHabitacionNoValidoException e) {
			
				e.printStackTrace();
			}
			
		}while(!numeroHabitacionValido);
		
		return numeroHabitacionRequerida;
	}

	private boolean validarNumeroHabitacion(ArrayList<Habitaciones> habitacionesTmp, int numeroHabitacion) throws NumeroHabitacionNoValidoException {
		
		for (int i = 0; i < habitacionesTmp.size(); i++) {
			
			if(habitacionesTmp.get(i).getNumero() == numeroHabitacion)
				return true;
		}
		
		throw new NumeroHabitacionNoValidoException(numeroHabitacion);
	}
	
}
