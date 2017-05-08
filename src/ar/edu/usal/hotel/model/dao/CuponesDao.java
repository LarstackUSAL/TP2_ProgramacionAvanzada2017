package ar.edu.usal.hotel.model.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

import ar.edu.usal.hotel.model.dto.Cupones;
import ar.edu.usal.hotel.model.interfaces.ICalculoImportes;
import ar.edu.usal.hotel.utils.Validador;

public class CuponesDao implements ICalculoImportes{

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

			cuponesScanner = new Scanner(cuponesFile);

			while(cuponesScanner.hasNextLine()){

				String linea = cuponesScanner.nextLine();
				String[] cuponesArray = linea.split(";");

				for (int i = 0; i < cuponesArray.length; i++) {

					int numeroDocumento = Integer.parseInt(cuponesArray[0]);		
					String nombre = cuponesArray[1];				
					String apellido = cuponesArray[2];
					String fechaCheckInTxt = cuponesArray[3];

					Calendar fechaCheckIn = Validador.stringToCalendar(fechaCheckInTxt, "yyyyMMdd");

					double totalConsumido = Double.parseDouble(cuponesArray[4]);
					double descuentoCalculado = Double.parseDouble(cuponesArray[5]);
					String fechaVencimientoTxt = cuponesArray[6];

					Calendar fechaVencimiento = Validador.stringToCalendar(fechaCheckInTxt, "yyyyMMdd");

					Cupones cuponDto = new Cupones(numeroDocumento, nombre, apellido, fechaCheckIn, totalConsumido, descuentoCalculado, fechaVencimiento);

					cupones.add(cuponDto);
				}
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
}
