package ar.edu.usal.hotel.model.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import ar.edu.usal.hotel.exception.HabitacionSinConsumoException;
import ar.edu.usal.hotel.exception.ProductoInexistenteException;
import ar.edu.usal.hotel.model.dto.ClientesHabitacion;
import ar.edu.usal.hotel.model.dto.Consumos;
import ar.edu.usal.hotel.model.dto.Productos;
import ar.edu.usal.hotel.model.interfaces.ICalculoImportes;
import ar.edu.usal.hotel.utils.Validador;

public class ConsumosDao implements ICalculoImportes{

	private static ConsumosDao consumosDaoInstance = null;
	private HashMap<Integer,ArrayList<Consumos>> consumosHabitaciones;
	
	private ConsumosDao(int numeroHabitacion){

		this.consumosHabitaciones = new HashMap();
		this.loadConsumos(numeroHabitacion);
	}

	public static ConsumosDao getInstance(int numeroHabitacion) {
		
		if(consumosDaoInstance == null){
			
			consumosDaoInstance = new ConsumosDao(numeroHabitacion);
		}else{
			
			boolean consumosHabitacionLevantados = false;
			for (int i = 0; i < consumosDaoInstance.getConsumosHabitaciones().size(); i++) {
				
				if(consumosDaoInstance.getConsumosHabitaciones().get(numeroHabitacion) != null){
					
					consumosHabitacionLevantados = true;
					break;
				}
			}
			
			if(!consumosHabitacionLevantados){
				
				consumosDaoInstance.loadConsumos(numeroHabitacion);
			}
		}
		
		return consumosDaoInstance;
	}
	
	private void loadConsumos(int numeroHabitacion) {
		
		String numero = Validador.fillString(String.valueOf(numeroHabitacion).trim(), 3, "0", true);
		
		File consumosFile = new File("./archivos/Cons"+ numero +".txt");
		Scanner consumosScanner;
		
		ArrayList<Consumos> consumosHabitacion = new ArrayList();
		
		try {
			
			ProductosDao productosDao = ProductosDao.getInstance();
			
			try {
				consumosFile.createNewFile();
			
			} catch (IOException e) {

				System.out.println("Se ha verificado un error al cargar el archivo de consumos.");
			}
			
			consumosScanner = new Scanner(consumosFile);
			
			while(consumosScanner.hasNextLine()){

				if(consumosScanner.hasNext()){
					
					String fechaTxt = consumosScanner.next().trim();
					Calendar fecha = Validador.stringToCalendar(fechaTxt, "yyyyMMdd");

					String codigoProducto = consumosScanner.next().trim();

					int cantidad = consumosScanner.nextInt();

					Productos productoConsumido = productosDao.loadProductoPorCodigo(codigoProducto);

					Consumos consumo = new Consumos(fecha, productoConsumido, cantidad);

					consumosHabitacion.add(consumo);
				}else{
					
					break;
				}
			}

			this.consumosHabitaciones.put(numeroHabitacion, consumosHabitacion);
			
			consumosScanner.close();
			
		}catch(InputMismatchException e){

			System.out.println("Se ha encontrado un tipo de dato insesperado.");

		}catch(FileNotFoundException e) {

 			System.out.println("No se ha encontrado el archivo.");
			
		}catch(ProductoInexistenteException e){
			
			e.printStackTrace();
		}
	}
	
	public static void crearArchivoConsumos(int numero) {
		
		String numeroHabitacion = Validador.fillString(String.valueOf(numero).trim(), 3, "0", true);
		
		File archivoConsumo = new File("./archivos/Cons"+ numeroHabitacion +".txt");
		
		try {
			archivoConsumo.createNewFile();
		
		} catch (IOException e) {

			System.out.println("Se ha verificado un error al crear el archivo de consumos.");
		}
	}

	public ArrayList<Consumos> getConsumosHabitacionPorNumero(int numeroHabitacion) throws HabitacionSinConsumoException{
		
		ArrayList<Consumos> consumosHabitacionPorNumero = this.consumosHabitaciones.get(numeroHabitacion);
		
		if(consumosHabitacionPorNumero!=null) return consumosHabitacionPorNumero;
		
		throw new HabitacionSinConsumoException(numeroHabitacion);
	}
	
	public HashMap<Integer, ArrayList<Consumos>> getConsumosHabitaciones() {
		return consumosHabitaciones;
	}

	public void grabarConsumo(Consumos consumo, int numeroHabitacion) throws IOException {
		
		String numero = Validador.fillString(String.valueOf(numeroHabitacion).trim(), 3, "0", true);
		FileWriter consumosFile = new FileWriter("./archivos/Cons"+ numero +".txt", true);
		PrintWriter consumosOut = new PrintWriter(consumosFile);
		
		String fecha = Validador.calendarToString(consumo.getFecha(), "yyyyMMdd");
		String codigoProducto = consumo.getProducto().getCodigo().trim();
		String cantidad = String.valueOf(consumo.getCantidad());
		
		consumosOut.println(fecha + "\t" + codigoProducto + "\t" + cantidad);
		
		consumosOut.close();
		
		ArrayList<Consumos> consumosHabitacion = this.consumosHabitaciones.get(numeroHabitacion);
		
		consumosHabitacion.add(consumo);
	}

	@Override
	public double calcularImporte(ClientesHabitacion clientesHabitacion) {
		
		int numeroHabitacion = clientesHabitacion.getHabitacion().getNumero();
		ArrayList<Consumos> consumos = null; 
		
		try {
		
			consumos = this.getConsumosHabitacionPorNumero(numeroHabitacion);
		
		} catch (HabitacionSinConsumoException e) {
			
			e.printStackTrace();
		}
		
		double importeConsumosTotal = 0.0; 
		
		if(consumos != null && !consumos.isEmpty()){
			
			for (int i = 0; i < consumos.size(); i++) {
				
				Consumos consumo = consumos.get(i);
				
				double importeIndividual = consumo.getProducto().getPrecio();
				
				importeIndividual = importeIndividual * consumo.getCantidad();
				
				importeConsumosTotal += importeIndividual;
			}
		}
		
		return importeConsumosTotal;
		
	}

}
