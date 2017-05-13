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

import ar.edu.usal.hotel.exception.ProductoInexistenteException;
import ar.edu.usal.hotel.model.dto.ClientesHabitacion;
import ar.edu.usal.hotel.model.dto.Consumos;
import ar.edu.usal.hotel.model.dto.Productos;
import ar.edu.usal.hotel.model.interfaces.ICalculoImportes;
import ar.edu.usal.hotel.utils.Validador;

public class ConsumosDao implements ICalculoImportes{

	public ConsumosDao(){}

	public void loadConsumos(ClientesHabitacion clientesHabitacion) {

		int numeroHabitacion = clientesHabitacion.getHabitacion().getNumero();
		String numero = Validador.fillString(String.valueOf(numeroHabitacion).trim(), 3, "0", true);

		File consumosFile = new File("./archivos/Cons"+ numero +".txt");
		Scanner consumosScanner;

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

					int idEstadia = consumosScanner.nextInt();
					int numeroDocumento = consumosScanner.nextInt();

					if(numeroDocumento == clientesHabitacion.getClienteResponsable().getNumeroDocumento()
							&& idEstadia == clientesHabitacion.getIdEstadia()){
						
						String fechaTxt = consumosScanner.next().trim();
						Calendar fecha = Validador.stringToCalendar(fechaTxt, "yyyyMMdd");

						String codigoProducto = consumosScanner.next().trim();

						int cantidad = consumosScanner.nextInt();

						Productos productoConsumido = productosDao.loadProductoPorCodigo(codigoProducto);

						clientesHabitacion.createConsumo(fecha, productoConsumido, cantidad);
					}
				}else{

					break;
				}
			}

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

	public void grabarConsumo(Consumos consumo, int numeroHabitacion, int numeroDocumento, int idEstadia) throws IOException {

		String numero = Validador.fillString(String.valueOf(numeroHabitacion).trim(), 3, "0", true);
		FileWriter consumosFile = new FileWriter("./archivos/Cons"+ numero +".txt", true);
		PrintWriter consumosOut = new PrintWriter(consumosFile);

		String numeroDocumentoString = Validador.fillString(String.valueOf(numeroDocumento),8, "0", true);
		String fecha = Validador.calendarToString(consumo.getFecha(), "yyyyMMdd");
		String codigoProducto = consumo.getProducto().getCodigo().trim();
		String cantidad = String.valueOf(consumo.getCantidad());
		String idEstadiaString = String.valueOf(idEstadia);

		consumosOut.println(idEstadiaString + "\t" +numeroDocumentoString + "\t" + fecha + "\t" + codigoProducto + "\t" + cantidad);

		consumosOut.close();
	}

	@Override
	public double calcularImporte(ClientesHabitacion clientesHabitacion) {

		int numeroHabitacion = clientesHabitacion.getHabitacion().getNumero();
		ArrayList<Consumos> consumos = null; 

		consumos = clientesHabitacion.getConsumos();

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
