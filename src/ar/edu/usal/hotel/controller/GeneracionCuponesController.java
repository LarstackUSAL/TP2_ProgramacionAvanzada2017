package ar.edu.usal.hotel.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import ar.edu.usal.hotel.model.dao.ClientesHabitacionDao;
import ar.edu.usal.hotel.model.dao.ConsumosDao;
import ar.edu.usal.hotel.model.dao.CuponesDao;
import ar.edu.usal.hotel.model.dto.ClientesHabitacion;
import ar.edu.usal.hotel.model.dto.Cupones;
import ar.edu.usal.hotel.model.interfaces.ICalculoImportes;
import ar.edu.usal.hotel.utils.Validador;
import ar.edu.usal.hotel.view.GeneracionCuponesView;

public class GeneracionCuponesController {

	GeneracionCuponesView generacionCuponesView;

	public GeneracionCuponesController(){

		this.generacionCuponesView = new GeneracionCuponesView();
	}

	public void generarCupones(String[] args) {

		if(Validador.validarMes(Integer.parseInt(args[0]))){
			int importeRandom = 0;
			Random rnd = new Random();
			do{
				importeRandom = rnd.nextInt();
			}while(importeRandom <= 0);

			//hardcode prueba LR
//					importeRandom = 300;

			Calendar fechaActual = Calendar.getInstance();

			ClientesHabitacionDao clientesHabitacionDao = ClientesHabitacionDao.getInstance();
			ArrayList<ClientesHabitacion> clientesHabitacionList = clientesHabitacionDao.getClientesHabitacion();

			boolean cuponGeneradoOk = false;

			for (int i = 0; i < clientesHabitacionList.size(); i++){

				ClientesHabitacion clientesHabitacionIterado = clientesHabitacionList.get(i); 
				ConsumosDao consumosDao = new ConsumosDao();

				double totalConsumos = consumosDao.calcularImporte(clientesHabitacionIterado);

				if(clientesHabitacionList.get(i).getFechaIngreso().get(Calendar.YEAR) == fechaActual.get(Calendar.YEAR) 
						&& (clientesHabitacionList.get(i).getFechaIngreso().get(Calendar.MONTH)+1) == Integer.parseInt(args[0])
						&& totalConsumos > importeRandom){

					Calendar vencimientoCupon = Calendar.getInstance();
					vencimientoCupon.add(Calendar.MONTH, 12);

					int numeroDocumento = clientesHabitacionIterado.getClienteResponsable().getNumeroDocumento();
					String nombre = clientesHabitacionIterado.getClienteResponsable().getNombre();
					String apellido = clientesHabitacionIterado.getClienteResponsable().getApellido();
					Calendar fechaCheckIn = clientesHabitacionIterado.getFechaIngreso();
					Calendar fechaVencimiento = vencimientoCupon;

					double descuentoCalculado = totalConsumos * ICalculoImportes.PORCENTAJE_DESCUENTO;

					CuponesDao cuponesDao = new CuponesDao();

					String mensajeCuponGenerado = ""; 

					try {

						Cupones cuponGenerado = clientesHabitacionIterado.getClienteResponsable().generarCupon(numeroDocumento, nombre, apellido,
								fechaCheckIn, fechaVencimiento, totalConsumos, descuentoCalculado, false);

						cuponesDao.grabarCupon(cuponGenerado);

						mensajeCuponGenerado = "SE HA GENERADO EL SIGUIENTE CUPON:\n" 
								+ "Nombre: " + nombre + " " + apellido + "\n"
								+ " Fecha check-in: " + Validador.calendarToString(fechaCheckIn, "dd-MM-yyyy") + "\n"
								+ " Total consumido: " + totalConsumos + "\n"
								+ " Descuento otorgado: " + descuentoCalculado + "\n"; 

						cuponGeneradoOk = true;

					} catch (IOException e) {

						mensajeCuponGenerado = "Se ha verificado un error al generar un cupon.";
					}

					generacionCuponesView.cuponGenerado(mensajeCuponGenerado);

				}
			}

			if(!cuponGeneradoOk) generacionCuponesView.cuponGenerado("No se han generado cupones.");
		}
		else{

			generacionCuponesView.mesNoValido(args[0]);
		}
	}
}
