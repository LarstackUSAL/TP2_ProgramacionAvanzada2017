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

	private ArrayList<Cupones> cupones;

	private static CuponesDao cuponesDaoInstance = null;

	private CuponesDao(){

		this.cupones = new ArrayList<Cupones>();
		this.loadCupones();
	}

	public static CuponesDao getInstance() {

		if(cuponesDaoInstance == null){

			cuponesDaoInstance = new CuponesDao();
		}

		return cuponesDaoInstance;
	}

	private void loadCupones(){

		File cuponesFile = new File("./archivos/CUPONES.txt");
		Scanner cuponesScanner;

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
				String nombre = cuponesArray[1];				
				String apellido = cuponesArray[2];
				String fechaCheckInTxt = cuponesArray[3];

				Calendar fechaCheckIn = Validador.stringToCalendar(fechaCheckInTxt, "yyyyMMdd");

				double totalConsumido = Double.parseDouble(cuponesArray[4]);
				double descuentoCalculado = Double.parseDouble(cuponesArray[5]);
				String fechaVencimientoTxt = cuponesArray[6];

				Calendar fechaVencimiento = Validador.stringToCalendar(fechaCheckInTxt, "yyyyMMdd");

				boolean esUtilizado = Boolean.parseBoolean(cuponesArray[7]);

				Cupones cuponDto = new Cupones(numeroDocumento, nombre, apellido, fechaCheckIn, totalConsumido, descuentoCalculado, fechaVencimiento, esUtilizado);

				cupones.add(cuponDto);
			}

			cuponesScanner.close();

		}catch(InputMismatchException e){

			System.out.println("Se ha encontrado un tipo de dato insesperado.");

		}catch (FileNotFoundException e) {

			System.out.println("No se ha encontrado el archivo.");
		}

	}

	public ArrayList<Cupones> getCupones() {
		return cupones;
	}

	public Cupones loadCuponCliente(Clientes cliente) {

		for (int i = 0; i < cupones.size(); i++) {

			Cupones cupon = cupones.get(i);

			if(cupon.getNumeroDocumento() == cliente.getNumeroDocumento()){

				return cupon;
			}

		}

		return null;
	}

	public void grabarCupon(int numeroDocumento, String nombre, String apellido, Calendar fechaCheckIn, double totalConsumido, double descuentoCalculado, Calendar fechaVencimiento) throws IOException {

		Cupones cuponNuevo = new Cupones(numeroDocumento, nombre, apellido, fechaCheckIn, totalConsumido, descuentoCalculado, fechaVencimiento, false);

		this.cupones.add(cuponNuevo);

		FileWriter archivoCupon = new FileWriter("./archivos/CUPONES.txt",true);
		PrintWriter cuponOut = new PrintWriter(archivoCupon);

		cuponOut.println(numeroDocumento + ";" + nombre + ";" + apellido + ";" + 
				Validador.calendarToString(fechaCheckIn, "yyyyMMdd") + ";" + totalConsumido + ";" +
				descuentoCalculado + ";" +Validador.calendarToString(fechaVencimiento, "yyyyMMdd") +
				";" + String.valueOf(false));


		cuponOut.close();
		archivoCupon.close();		
	}

	public void actualizarCupones() throws IOException {

		FileWriter cuponesFile = new FileWriter("./archivos/CUPONES.txt");
		PrintWriter cuponesOut = new PrintWriter(cuponesFile);

		for(int i=0; i < this.cupones.size(); i++)
		{
			Cupones cupon = this.cupones.get(i);

			String numeroDocumento = String.valueOf(cupon.getNumeroDocumento());		
			String nombre = cupon.getNombre();				
			String apellido = cupon.getApellido();
			String fechaCheckInTxt = Validador.calendarToString(cupon.getFechaCheckIn(), "yyyyMMdd");
			String totalConsumido = String.valueOf(cupon.getTotalConsumido());
			String descuentoCalculado = String.valueOf(cupon.getDescuentoCalculado());
			String fechaVencimientoTxt = Validador.calendarToString(cupon.getFechaVencimiento(), "yyyyMMdd");
			String esUtilizado = String.valueOf(cupon.isEsUtilizado());

			cuponesOut.println(numeroDocumento + ";" + nombre + ";" + apellido + ";" + 
					fechaCheckInTxt + ";" + totalConsumido + ";" +
					descuentoCalculado + ";" + fechaVencimientoTxt + ";" + esUtilizado);
		}

		cuponesOut.close();
		cuponesFile.close();
	}


}
