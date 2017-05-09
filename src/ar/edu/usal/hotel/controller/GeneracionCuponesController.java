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

	public GeneracionCuponesController(){}
	
	public void generarCupones(String[] args) {
		int importeRandom = 0;
		Random rnd = new Random();
		importeRandom = rnd.nextInt();
		Calendar fechaActual = Calendar.getInstance();
		
		ClientesHabitacionDao clientesHabitacionDao = ClientesHabitacionDao.getInstance();
		ArrayList<ClientesHabitacion> clientesHabitacionList = clientesHabitacionDao.getClientesHabitacion();
		
		for (int i = 0; i < clientesHabitacionList.size(); i++)
		{
			
			ClientesHabitacion clientesHabitacionIterado = clientesHabitacionList.get(i); 
			ConsumosDao consumosDao = ConsumosDao.getInstance(clientesHabitacionIterado.getHabitacion().getNumero());
			
			double totalConsumos = consumosDao.calcularImporte(clientesHabitacionIterado);
			
			if(clientesHabitacionList.get(i).getFechaIngreso().YEAR == fechaActual.YEAR 
					&& clientesHabitacionList.get(i).getFechaIngreso().MONTH == Integer.parseInt(args[0])
						&& totalConsumos > importeRandom){
						
				Calendar vencimientoCupon = Calendar.getInstance();
				vencimientoCupon.add(Calendar.MONTH, 12);
				
				int numeroDocumento = clientesHabitacionIterado.getClienteResponsable().getNumeroDocumento();
				String nombre = clientesHabitacionIterado.getClienteResponsable().getNombre();
				String apellido = clientesHabitacionIterado.getClienteResponsable().getApellido();
				Calendar fechaCheckIn = clientesHabitacionIterado.getFechaIngreso();
				Calendar fechaVencimiento = vencimientoCupon;
				
				double descuentoCalculado = totalConsumos * ICalculoImportes.PORCENTAJE_DESCUENTO;
				
				CuponesDao cuponesDao = CuponesDao.getInstance();
				
				String cuponGenerado = ""; 
						
				try {
					
					cuponesDao.grabarCupon(numeroDocumento, nombre, apellido, fechaCheckIn, totalConsumos, 
							descuentoCalculado, fechaVencimiento);
				
					cuponGenerado = "SE HA GENERADO EL SIGUIENTE CUPON:\n" 
							+ "Nombre: " + nombre + " " + apellido + "\n"
							+ " Fecha check-in: " + Validador.calendarToString(fechaCheckIn, "dd-MM-yyyy") + "\n"
							+ " Total consumido: " + totalConsumos + "\n"
							+ " Descuento otorgado: " + descuentoCalculado + "\n"; 
					
				} catch (IOException e) {
					
					cuponGenerado = "Se ha verificado un error al generar un cupon.";
				}
				
				GeneracionCuponesView.cuponGenerado(cuponGenerado);
			}
		}
		
	}

	
}
