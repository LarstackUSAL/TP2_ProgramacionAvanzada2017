package ar.edu.usal.hotel.model.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import ar.edu.usal.hotel.model.dto.Precios;
import ar.edu.usal.hotel.utils.Validador;

public class PreciosDao {

	private ArrayList<Precios> precios;
	private static PreciosDao preciosDaoInstance = null;
	
	private PreciosDao(){
		
		this.precios = new ArrayList<Precios>();
		this.loadPrecios();
	}
	
	public static PreciosDao getInstance(){
		
		if(preciosDaoInstance==null){
			
			preciosDaoInstance  = new PreciosDao();
		}
		
		return preciosDaoInstance;
	}
	
	private void loadPrecios(){
		
		File preciosFile = new File("./archivos/PRECIOS.txt");
		Scanner preciosScanner;
		
		try {
			
			preciosScanner = new Scanner(preciosFile);
			
			while(preciosScanner.hasNextLine()){
				
				String linea = preciosScanner.nextLine();
				String[] preciosArray = linea.split(";");
				
				char categoria = preciosArray[0].replace("\""," ").trim().charAt(0) ;
				int capacidad = Integer.parseInt(preciosArray[1].replace("\""," ").trim());				
				double precioTxt = Double.parseDouble(preciosArray[2].replace("\""," ").trim());
				
				Precios precio = new Precios(categoria, capacidad, precioTxt);
				
				precios.add(precio);
			}
			
			preciosScanner.close();
			
		}catch(InputMismatchException e){
			
			System.out.println("Se ha encontrado un tipo de dato insesperado.");
			
		}catch (FileNotFoundException e) {
			
			System.out.println("No se ha encontrado el archivo.");
		
		}catch(StringIndexOutOfBoundsException e){
			
			System.out.println("Se ha verificado un error en el parseo del archivo de precios.");
				
		}catch(Exception e){
			
			System.out.println("Se ha verificado un error inesperado.");
		}		

	}
	
	public Precios cargarPrecioHabitacion(char categoria, int capacidad){
		
		for (int i = 0; i < this.precios.size(); i++) {
			
			Precios precio = this.precios.get(i);
			
			if(Validador.compararCaracteresIgnoreCase(precio.getCategoria() , categoria)){
				
				if(precio.getCapacidad() == capacidad){
					
					return precio;
				}
			}
		}
		
		return null;
	}
}
