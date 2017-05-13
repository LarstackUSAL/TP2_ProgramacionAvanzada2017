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

		ClientesHabitacion clientesHabitacion = clientesHabitacionDao.loadClientesHabitacionActualDeLaHabitacion(numeroHabitacion); 

		if(clientesHabitacion != null){
			double totalImporte = this.calcularImporte(clientesHabitacion);

			clientesHabitacion.setEstadiaEnCurso(false);
			try {

				clientesHabitacionDao.actualizarArchivo();

				clientesHabitacion.getHabitacion().setDisponible(true);

				HabitacionesDao habitacionesDao = HabitacionesDao.getInstance();
				habitacionesDao.actualizarArchivo();

			} catch (IOException e) {

				System.out.println("Se ha verificado un error al actualizar los archivos de estadias y habitaciones.");
			}

			checkOutView.mostrarImporteTotal(String.valueOf(totalImporte));
		}else{

			checkOutView.habitacionNoOcupada(numeroHabitacion);
		}
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

		ConsumosDao consumosDao = new ConsumosDao();

		double calculoConsumos = consumosDao.calcularImporte(clientesHabitacion);

		CuponesDao cuponesDao = new CuponesDao();

		ArrayList<Cupones> cupones = cliente.getCupones();

		if(cupones != null && !cupones.isEmpty()){

			for (int i = 0; i < cupones.size(); i++){

				Cupones cupon = cupones.get(i);
				try {
					if(!cupon.isEsUtilizado()){

						cupon.setEsUtilizado(true);
						cuponesDao.actualizarCupones();

						calculoConsumos = calculoConsumos - cupon.getDescuentoCalculado();
					}
				} catch (IOException e) {

					checkOutView.errorActualizarCupones();
				}
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
			if(clientesHabitacion.isEstadiaEnCurso()){
				Habitaciones habitacion = clientesHabitacion.getHabitacion();

				String datosHabitacion = 
						"Numero habitacion: " + habitacion.getNumero();

				habitacionesList.add(habitacion);
				habitacionesString.add(datosHabitacion);
			}
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