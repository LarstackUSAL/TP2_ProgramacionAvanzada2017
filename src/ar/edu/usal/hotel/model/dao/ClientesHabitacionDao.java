package ar.edu.usal.hotel.model.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import ar.edu.usal.hotel.model.dto.Clientes;
import ar.edu.usal.hotel.model.dto.ClientesHabitacion;
import ar.edu.usal.hotel.model.dto.Habitaciones;
import ar.edu.usal.hotel.utils.Validador;

public class ClientesHabitacionDao {

	private static int nextIdEstadia = 1;
	
	private static ClientesHabitacionDao clientesHabitacionInstance = null;

	private ArrayList<ClientesHabitacion> clientesHabitacion;

	private ClientesHabitacionDao(){

		this.clientesHabitacion = new ArrayList();
		this.loadHabitacionClientes();
	}

	public static ClientesHabitacionDao getInstance(){

		if(clientesHabitacionInstance==null){

			loadNextId();
			clientesHabitacionInstance = new ClientesHabitacionDao();
		}
		
		return clientesHabitacionInstance;
	}

	public Calendar calcularFechaEgreso(int diasPermanencia){

		Calendar fechaActual = Calendar.getInstance();

		fechaActual.setTime(new Date()) ;
		fechaActual.add(Calendar.DAY_OF_YEAR, diasPermanencia);

		return fechaActual;	    
	}

	public ArrayList<ClientesHabitacion> getClientesHabitacion() {
		return clientesHabitacion;
	}

	public Habitaciones loadHabitacionDelCliente(Clientes cliente){

		Habitaciones habitacion = null;

		clientesHabitacionTag:
			for (int i = 0; i < this.clientesHabitacion.size(); i++) {

				ClientesHabitacion clientesHabitacion = this.clientesHabitacion.get(i);
				if(clientesHabitacion.isEstadiaEnCurso()){
					for (int j = 0; j < clientesHabitacion.getClientes().size(); j++) {

						Clientes clienteIterado = clientesHabitacion.getClientes().get(j);

						if(clienteIterado.getNumeroDocumento() == cliente.getNumeroDocumento()){

							habitacion = clientesHabitacion.getHabitacion();
							break clientesHabitacionTag;
						}
					}
				}
			}

		return habitacion;
	}

	public ClientesHabitacion loadClientesHabitacionActualDeLaHabitacion(int numeroHabitacion){

		for (int i = 0; i < this.clientesHabitacion.size(); i++) {

			ClientesHabitacion clientesHabitacion = this.clientesHabitacion.get(i);

			if(clientesHabitacion.getHabitacion().getNumero() == numeroHabitacion
					&& clientesHabitacion.isEstadiaEnCurso()){

				return clientesHabitacion;
			}

		}

		return null;
	}

	public void actualizarArchivo() throws IOException {

		FileWriter estadiasFile = new FileWriter("./archivos/ESTADIAS.txt");
		PrintWriter estadiasOut = new PrintWriter(estadiasFile);

		for(int i=0; i < this.clientesHabitacion.size(); i++)
		{
			ClientesHabitacion estadia = this.clientesHabitacion.get(i);

			String idEstadia = String.valueOf(estadia.getIdEstadia());
			String numeroHabitacion = String.valueOf(estadia.getHabitacion().getNumero());
			String fechaCheckIn = Validador.calendarToString(estadia.getFechaIngreso(), "yyyyMMdd");
			String fechaCheckOut = Validador.calendarToString(estadia.getFechaEgreso(), "yyyyMMdd");
			String diasPermanencia = String.valueOf(estadia.getDiasPermanencia());
			String estadiaEnCurso = String.valueOf(estadia.isEstadiaEnCurso());

			int numeroDocumentoResponsable = estadia.getClienteResponsable().getNumeroDocumento();

			String listaDocumentos = Validador.fillString(
					String.valueOf(numeroDocumentoResponsable), 
					8, "0", true) + ";";

			for (int j = 0; j < estadia.getClientes().size(); j++) {

				Clientes cliente = estadia.getClientes().get(j);
				if(cliente.getNumeroDocumento() != numeroDocumentoResponsable){

					listaDocumentos += Validador.fillString(
							String.valueOf(cliente.getNumeroDocumento()), 
							8, "0", true);

					if(j < (estadia.getClientes().size() - 1)){

						listaDocumentos += ";";
					}
				}
			}

			estadiasOut.println(
							idEstadia + ";" +
							numeroHabitacion + ";" +
							fechaCheckIn + ";" +
							fechaCheckOut + ";" +
							diasPermanencia + ";" +
							estadiaEnCurso + ";" +
							listaDocumentos
					);
		}

		estadiasOut.close();
		estadiasFile.close();
		
		//Se actualiza el archivo de id.
		FileWriter idEstadiaFile = new FileWriter("./archivos/ID_ESTADIA.txt");
		PrintWriter idEstadiaOut = new PrintWriter(idEstadiaFile);
		
		idEstadiaOut.println(nextIdEstadia);
		idEstadiaOut.close();
		idEstadiaFile.close();
	}

	private void loadHabitacionClientes() {

		File estadiasFile = new File("./archivos/ESTADIAS.txt");
		Scanner estadiasScanner;

		try {

			try {
				estadiasFile.createNewFile();

			} catch (IOException e) {

				System.out.println("Se ha verificado un error al cargar el archivo de estadias.");
			}

			estadiasScanner = new Scanner(estadiasFile);

			while(estadiasScanner.hasNextLine()){

				String linea = estadiasScanner.nextLine();
				String[] estadiaArray = linea.split(";");

				ClientesHabitacion estadia = new ClientesHabitacion();

				estadia.setIdEstadia(Integer.valueOf(estadiaArray[0]));
				estadia.setHabitacion(HabitacionesDao.getInstance().loadHabitacionPorNumero(Integer.valueOf(estadiaArray[1])));
				estadia.setFechaIngreso(Validador.stringToCalendar(estadiaArray[2], "yyyyMMdd"));
				estadia.setFechaEgreso(Validador.stringToCalendar(estadiaArray[3], "yyyyMMdd"));
				estadia.setDiasPermanencia(Integer.valueOf(estadiaArray[4]));
				estadia.setEstadiaEnCurso(Boolean.valueOf(estadiaArray[5]));
				estadia.setClienteResponsable(ClientesDao.getInstance().loadClientePorNumeroDocumento(Integer.valueOf(estadiaArray[6])));

				ArrayList<Clientes> clientes = new ArrayList<Clientes>(); 

				for (int i = 6; i < estadiaArray.length ; i++) {

					clientes.add(ClientesDao.getInstance().loadClientePorNumeroDocumento(Integer.valueOf(estadiaArray[i])));
				}

				estadia.setClientes(clientes);

				ConsumosDao consumosDao = new ConsumosDao(); 
				consumosDao.loadConsumos(estadia);

				this.clientesHabitacion.add(estadia);
			}
			
			estadiasScanner.close();

		}catch(InputMismatchException e){

			System.out.println("Se ha encontrado un tipo de dato insesperado.");

		}catch(FileNotFoundException e) {

			System.out.println("No se ha encontrado el archivo.");

		}catch(Exception e){

			e.printStackTrace();
		}
	}

	public ClientesHabitacion loadClientesHabitacionPorCliente(Clientes cliente) {

		for (int i = 0; i < this.clientesHabitacion.size(); i++) {

			ClientesHabitacion clientesHabitacion = this.clientesHabitacion.get(i);

			for (int j = 0; j < clientesHabitacion.getClientes().size(); j++) {

				Clientes clienteIterado = clientesHabitacion.getClientes().get(j);

				if(clienteIterado.getNumeroDocumento() == cliente.getNumeroDocumento()
						&& clientesHabitacion.isEstadiaEnCurso()){

					return clientesHabitacion;
				}
			}
		}
		return null;
	}

	public ArrayList<ClientesHabitacion> getClientesHabitacionPorHabitacion(int numeroHabitacion) {

		ArrayList<ClientesHabitacion> clientesHabitacionList = new ArrayList<ClientesHabitacion>();

		for (int i = 0; i < this.clientesHabitacion.size(); i++) {

			ClientesHabitacion clientesHabitacion = this.clientesHabitacion.get(i);

			if(clientesHabitacion.getHabitacion().getNumero() == numeroHabitacion){

				clientesHabitacionList.add(clientesHabitacion);
			}

		}

		return clientesHabitacionList;
	}
	
	private static void loadNextId() {
		
		File idEstadiasFile = new File("./archivos/ID_ESTADIA.txt");
		Scanner estadiasScanner;

		try {

			try {
				idEstadiasFile.createNewFile();

			} catch (IOException e) {

				System.out.println("Se ha verificado un error al cargar el archivo de id.");
			}

			estadiasScanner = new Scanner(idEstadiasFile);

			nextIdEstadia = estadiasScanner.nextInt();
			
			estadiasScanner.close();

		}catch(InputMismatchException e){

			System.out.println("Se ha encontrado un tipo de dato insesperado.");

		}catch(FileNotFoundException e) {

			System.out.println("No se ha encontrado el archivo.");

		}catch(Exception e){

			e.printStackTrace();
		}

	}
	
	public static int getNextIdEstadia() {

		int idReturn = nextIdEstadia;
		nextIdEstadia++;

		return idReturn;
	}
	
}