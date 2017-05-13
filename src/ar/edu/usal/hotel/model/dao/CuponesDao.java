package ar.edu.usal.hotel.model.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

import ar.edu.usal.hotel.model.dto.Clientes;
import ar.edu.usal.hotel.model.dto.Cupones;
import ar.edu.usal.hotel.utils.Validador;

public class CuponesDao {

	public CuponesDao(){}

	public boolean loadCuponesCliente(Clientes cliente){

		File cuponesFile = new File("./archivos/CUPONES.txt");
		Scanner cuponesScanner;

		boolean cuponesCargados = false;
		try {

			try {
				cuponesFile.createNewFile();

			} catch (IOException e) {

				System.out.println("Se ha verificado un error al cargar el archivo de cupones.");
			}

			cuponesScanner = new Scanner(cuponesFile);

			while(cuponesScanner.hasNextLine()){

				String linea = cuponesScanner.nextLine();
				String[] cuponesArray = linea.split(";");

				int numeroDocumento = Integer.parseInt(cuponesArray[0]);		

				if(numeroDocumento == cliente.getNumeroDocumento()){

					String nombre = cuponesArray[1];				
					String apellido = cuponesArray[2];
					String fechaCheckInTxt = cuponesArray[3];

					Calendar fechaCheckIn = Validador.stringToCalendar(fechaCheckInTxt, "yyyyMMdd");

					double totalConsumido = Double.parseDouble(cuponesArray[4]);
					double descuentoCalculado = Double.parseDouble(cuponesArray[5]);
					String fechaVencimientoTxt = cuponesArray[6];

					Calendar fechaVencimiento = Validador.stringToCalendar(fechaCheckInTxt, "yyyyMMdd");

					boolean esUtilizado = Boolean.parseBoolean(cuponesArray[7]);

					cliente.generarCupon(numeroDocumento, nombre, apellido, fechaCheckIn, fechaVencimiento, totalConsumido, descuentoCalculado, esUtilizado);

					cuponesCargados = true;
				}
			}
			cuponesScanner.close();
		}catch(InputMismatchException e){

			System.out.println("Se ha encontrado un tipo de dato insesperado.");

		}catch (FileNotFoundException e) {

			System.out.println("No se ha encontrado el archivo.");
		}

		return cuponesCargados;
	}

	public void grabarCupon(Cupones cupon) throws IOException {

		FileWriter archivoCupon = new FileWriter("./archivos/CUPONES.txt",true);
		PrintWriter cuponOut = new PrintWriter(archivoCupon);

		cuponOut.println(cupon.getNumeroDocumento() + ";" + cupon.getNombre().trim() + ";" + cupon.getApellido().trim() + ";" + 
				Validador.calendarToString(cupon.getFechaCheckIn(), "yyyyMMdd") + ";" + cupon.getTotalConsumido() + ";" +
				cupon.getDescuentoCalculado() + ";" +Validador.calendarToString(cupon.getFechaVencimiento(), "yyyyMMdd") +
				";" + String.valueOf(false));


		cuponOut.close();
		archivoCupon.close();		
	}

	public void actualizarCupones() throws IOException {

		FileWriter cuponesFile = new FileWriter("./archivos/CUPONES.txt");
		PrintWriter cuponesOut = new PrintWriter(cuponesFile);

		ClientesHabitacionDao clientesHabitacionDao = ClientesHabitacionDao.getInstance();

		for (int j = 0; j < clientesHabitacionDao.getClientesHabitacion().size(); j++){ 

			ArrayList<Clientes> clientesIterados = clientesHabitacionDao.getClientesHabitacion().get(j).getClientes();

			for(int i=0; i < clientesIterados.size(); i++)
			{
				ArrayList<Cupones> cupones = clientesIterados.get(i).getCupones();
				
				if(cupones != null){
				for (int k = 0; k < cupones.size(); k++){


					if(clientesIterados.get(i).getCupones().get(k) != null){

						Cupones cupon = clientesIterados.get(i).getCupones().get(k);

						String numeroDocumento = String.valueOf(cupon.getNumeroDocumento());		
						String nombre = cupon.getNombre().trim();				
						String apellido = cupon.getApellido().trim();
						String fechaCheckInTxt = Validador.calendarToString(cupon.getFechaCheckIn(), "yyyyMMdd");
						String totalConsumido = String.valueOf(cupon.getTotalConsumido());
						String descuentoCalculado = String.valueOf(cupon.getDescuentoCalculado());
						String fechaVencimientoTxt = Validador.calendarToString(cupon.getFechaVencimiento(), "yyyyMMdd");
						String esUtilizado = String.valueOf(cupon.isEsUtilizado());

						cuponesOut.println(numeroDocumento + ";" + nombre + ";" + apellido + ";" + 
								fechaCheckInTxt + ";" + totalConsumido + ";" +
								descuentoCalculado + ";" + fechaVencimientoTxt + ";" + esUtilizado);
					}
				}
			}
			}
		}
		cuponesOut.close();
		cuponesFile.close();
	}


}
