package ar.edu.usal.hotel.model.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

import ar.edu.usal.hotel.model.dto.ClientesHabitacion;
import ar.edu.usal.hotel.model.dto.Cupones;
import ar.edu.usal.hotel.model.dto.Habitaciones;
import ar.edu.usal.hotel.model.dto.Precios;
import ar.edu.usal.hotel.model.interfaces.ICalculoImportes;
import ar.edu.usal.hotel.utils.Validador;

public class HabitacionesDao implements ICalculoImportes{
	
	public static final int MAX_HABITACIONES = 150;
	
	private static HabitacionesDao habitacionesDaoInstance = null;
	
	public static final char[] CATEGORIAS_VALIDAS = {'A', 'B', 'C'};
	
	private Habitaciones[] habitaciones;
	
	private HabitacionesDao(){
		
		this.habitaciones = new Habitaciones[MAX_HABITACIONES];
		this.loadHabitaciones();
	}
	
	public static HabitacionesDao getInstance(){
		
		if(habitacionesDaoInstance==null){
			
			habitacionesDaoInstance = new HabitacionesDao();
		}
		
		return habitacionesDaoInstance;
	}
	
	private void loadHabitaciones(){
		
		File habitacionesTxt = new File("./archivos/HABITACIONES.txt");
		Scanner habitacionesScanner;
		
		PreciosDao precios = PreciosDao.getInstance();
		
		try {
			
			habitacionesScanner = new Scanner(habitacionesTxt);
			
			while(habitacionesScanner.hasNextLine()){
				
				String linea = habitacionesScanner.nextLine();
				String[] habitacionArray = linea.split(";");
				
				int numero = Integer.parseInt(habitacionArray[0]);
				int capacidad = Integer.parseInt(habitacionArray[1]);
				char categoria = habitacionArray[2].charAt(0);
				boolean tieneBalcon = Boolean.parseBoolean(habitacionArray[3]);
				String comentario = habitacionArray[4];
				
				Precios precio = precios.cargarPrecioHabitacion(categoria, capacidad);
				Habitaciones habitacion = new Habitaciones(numero, capacidad, categoria, tieneBalcon, comentario, precio);
				
				if(!this.agregarHabitacion(habitacion)){
					System.out.println("ERROR! Ya se han cargado las 150 habitaciones.");
				}
			}
			
			habitacionesScanner.close();
			
		}catch(InputMismatchException e){
			
			System.out.println("Se ha encontrado un tipo de dato insesperado.");
			
		}catch (FileNotFoundException e) {
			
			System.out.println("No se ha encontrado el archivo.");
		}
	}

	public boolean agregarHabitacion(Habitaciones habitacion){
		
		for (int i = 0; i < this.habitaciones.length; i++) {
			
			if(habitaciones[i] == null){
				
				habitaciones[i] = habitacion;
				return true;
			}
		}
		
		return false;
	}
	
	public Habitaciones[] getHabitaciones() {
		return habitaciones;
	}

	public Habitaciones loadHabitacionPorNumero(int numeroHabitacion){
		
		for (int i = 0; i < this.habitaciones.length; i++) {
			
			Habitaciones habitacion = this.habitaciones[i];
			
			if(habitacion.getNumero() == numeroHabitacion){
				
				return habitacion;
			}
		}
		
		return null;
	}
	
	@Override
	public double calcularImporte(ClientesHabitacion clientesHabitacion) {
		
		Habitaciones habitacion = clientesHabitacion.getHabitacion();
		
		double importeHabitacion = habitacion.getPrecio().getPrecio();
		
		return importeHabitacion * clientesHabitacion.getDiasPermanencia();		
	}

	public void actualizarArchivo() throws IOException {
		
		FileWriter habitacionesFile = new FileWriter("./archivos/HABITACIONES.txt");
		PrintWriter habitacionesOut = new PrintWriter(habitacionesFile);

		for(int i=0; i < this.habitaciones.length; i++)
		{
			Habitaciones habitacion = this.habitaciones[i];
			
			String numero = String.valueOf(habitacion.getNumero());
			String capacidad = String.valueOf(habitacion.getCapacidad());
			String categoria = String.valueOf(habitacion.getCategoria());
			String tieneBalcon = String.valueOf(habitacion.isTieneBalcon());
			String comentario = habitacion.getComentario();
			String disponible = String.valueOf(habitacion.isDisponible());
			
			habitacionesOut.println(numero + ";" + capacidad + ";" + categoria + ";" + 
					tieneBalcon + ";" + comentario + ";" + disponible);
		}

		habitacionesOut.close();
		habitacionesFile.close();
	}
	
}
