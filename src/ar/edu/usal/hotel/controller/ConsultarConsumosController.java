package ar.edu.usal.hotel.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ar.edu.usal.hotel.exception.HabitacionSinConsumoException;
import ar.edu.usal.hotel.exception.NumeroHabitacionNoValidoException;
import ar.edu.usal.hotel.model.dao.ClientesHabitacionDao;
import ar.edu.usal.hotel.model.dao.ConsumosDao;
import ar.edu.usal.hotel.model.dto.Clientes;
import ar.edu.usal.hotel.model.dto.ClientesHabitacion;
import ar.edu.usal.hotel.model.dto.Consumos;
import ar.edu.usal.hotel.model.dto.Habitaciones;
import ar.edu.usal.hotel.utils.Validador;
import ar.edu.usal.hotel.view.ConsultarConsumosView;

public class ConsultarConsumosController {

	private ConsultarConsumosView consultarConsumosView;

	public ConsultarConsumosController(){

		this.consultarConsumosView = new ConsultarConsumosView();
	}

	public void consultarConsumos() {


		Integer numeroHabitacion = this.getNumeroHabitacion();

		if(numeroHabitacion != null){

			ConsumosDao consumosDao = new ConsumosDao();

			ClientesHabitacionDao clientesHabitacionDao = ClientesHabitacionDao.getInstance();

			ArrayList<ClientesHabitacion> clientesHabitacionList = clientesHabitacionDao.getClientesHabitacionPorHabitacion(numeroHabitacion); 

			for (int i = 0; i < clientesHabitacionList.size(); i++){

				ClientesHabitacion clientesHabitacion = clientesHabitacionList.get(i);
				
				if(clientesHabitacion.isEstadiaEnCurso()){
					
					ArrayList<Clientes> clientesList = clientesHabitacion.getClientes();

					ArrayList<String> clientesString = new ArrayList<String>();

					for (int j = 0; j < clientesList.size(); j++) {

						Clientes cliente = clientesList.get(j);

						Calendar fechaActual = Calendar.getInstance();
						fechaActual.setTime(new Date());

						int anioActual = fechaActual.get(Calendar.YEAR);
						int anioNacimiento = cliente.getFechaNacimiento().get(Calendar.YEAR);
						int edad = anioActual - anioNacimiento;

						String datosCliente =
								"Nombre: " + cliente.getNombre().trim() + " " + cliente.getApellido().trim() + ", " + edad;

						clientesString.add(datosCliente);
					}

					ArrayList<String> consumosString = new ArrayList();

					double precioTotalGeneral = 0.0;

					ArrayList<Consumos> consumosHabitaciones = clientesHabitacion.getConsumos();

					for (int j = 0; j < consumosHabitaciones.size(); j++) {

						Consumos consumo = consumosHabitaciones.get(j);

						String precioTotal = String.valueOf(((consumo.getProducto().getPrecio())*consumo.getCantidad()));
						String datosConsumo =
								"Fecha: " + Validador.calendarToString(consumo.getFecha(), "dd-MM-yyyy") + "\n" + 
										"Producto: " + consumo.getProducto().getDescripcion() + "\n" +
										"Cantidad: " + consumo.getCantidad() + "\n" +		
										"Precio unitario: " + consumo.getProducto().getPrecio() + "\n"+
										"Precio total: " + precioTotal + "\n"+
										"\n";

						consumosString.add(datosConsumo);

						precioTotalGeneral += Double.parseDouble(precioTotal);
					}

					consultarConsumosView.mostrarConsumos(clientesString, consumosString, String.valueOf(precioTotalGeneral));
				}
			}
		}else{

			consultarConsumosView.noHayHabitacionesOcupadas();
		}
	}

	private Integer getNumeroHabitacion() {

		Integer numeroHabitacionRequerida = null;
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

		if(!habitacionesString.isEmpty()){
			boolean numeroHabitacionValido = false;
			do{
				numeroHabitacionRequerida = consultarConsumosView.insertNumeroHabitacion(habitacionesString);

				try {

					numeroHabitacionValido = this.validarNumeroHabitacion(habitacionesList, numeroHabitacionRequerida);

				} catch (NumeroHabitacionNoValidoException e) {

					e.printStackTrace();
				}

			}while(!numeroHabitacionValido);
		}
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
