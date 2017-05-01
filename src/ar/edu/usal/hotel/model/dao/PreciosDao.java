package ar.edu.usal.hotel.model.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import ar.edu.usal.hotel.model.dto.PreciosDto;

public class PreciosDao {

	private ArrayList<PreciosDto> precios;
	
	public PreciosDao(){
		
		this.precios = new ArrayList<PreciosDto>();
	}
	
	public void loadPrecios(){
		
		File preciosFile = new File("./archivos/PRECIOS.txt");
		Scanner preciosScanner;
		
		try {
			
			preciosScanner = new Scanner(preciosFile);
			
			while(preciosScanner.hasNextLine()){
				
				String linea = preciosScanner.nextLine();
				String[] preciosArray = linea.split(";");
				
				char categoria = preciosArray[0].charAt(0);
				int capacidad = Integer.parseInt(preciosArray[1]);				
				double precioTxt = Double.parseDouble(preciosArray[2].substring(1, ((preciosArray[2].length())-1)));
				
				PreciosDto precio = new PreciosDto(categoria, capacidad, precioTxt);
				
				precios.add(precio);
			}
			
		}catch(InputMismatchException e){
			
			System.out.println("Se ha encontrado un tipo de dato insesperado.");
			
		}catch (FileNotFoundException e) {
			
			System.out.println("No se ha encontrado el archivo.");
		}

	}
	
	public PreciosDto cargarPrecioHabitacion(char categoria, int capacidad){
		
		for (int i = 0; i < this.precios.size(); i++) {
			
			PreciosDto precio = this.precios.get(i);
			
			if(precio.getCategoria() == categoria){
				
				if(precio.getCapacidad() == capacidad){
					
					return precio;
				}
			}
		}
		
		return null;
	}
}
