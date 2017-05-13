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

import ar.edu.usal.hotel.exception.ClienteNoRegistradoException;
import ar.edu.usal.hotel.model.dto.Clientes;
import ar.edu.usal.hotel.model.dto.Cupones;
import ar.edu.usal.hotel.utils.Validador;

public class ClientesDao {

	private static ClientesDao clientesDaoInstance = null;
	
	private ArrayList<Clientes> clientes;
	
	private ClientesDao(){
		
		this.clientes = new ArrayList<Clientes>();
		this.loadClientes();
	}
	
	public static ClientesDao getInstance(){
		
		if(clientesDaoInstance==null){
			
			clientesDaoInstance  = new ClientesDao();
		}
		
		return clientesDaoInstance;
	}
	
	private void loadClientes(){
		
		// nombre 			char(20) 
		// apellido			char(20)
		// numeroDocumento	int(8)
		// fechaNacimiento 	char(8)
		File clientesFile = new File("./archivos/CLIENTES.txt");
		
		Scanner clientesScanner;
		
		try {
			
			try {
				clientesFile.createNewFile();
			
			} catch (IOException e) {

				System.out.println("Se ha verificado un error al cargar el archivo de clientes.");
			}
			
			clientesScanner = new Scanner(clientesFile);
				
			while(clientesScanner.hasNextLine()){
				
				String clienteTxt = clientesScanner.nextLine();
				
				int numeroDocumento = Integer.parseInt(clienteTxt.substring(0, 8));
				String nombre = clienteTxt.substring(8, 28);
				String apellido = clienteTxt.substring(28, 48);
				
				String fechaNacimientoTxt = clienteTxt.substring(48, 56);
				
				Calendar fechaNacimiento = Validador.stringToCalendar(fechaNacimientoTxt, "yyyyMMdd");
				
				Clientes cliente = new Clientes();
				cliente.setNumeroDocumento(numeroDocumento);
				cliente.setNombre(nombre);
				cliente.setApellido(apellido);
				cliente.setFechaNacimiento(fechaNacimiento);
				
				this.clientes.add(cliente);
				
				CuponesDao cuponesDao = new CuponesDao();
				cuponesDao.loadCuponesCliente(cliente);
			}
			
			clientesScanner.close();
			
		}catch(InputMismatchException e){
			
			System.out.println("Se ha encontrado un tipo de dato insesperado.");
			
		}catch (FileNotFoundException e) {
			
			System.out.println("No se ha encontrado el archivo.");
		}catch(StringIndexOutOfBoundsException e){
			
			System.out.println("Se ha verificado un error en el parseo del archivo de cupones.");
				
		}catch(Exception e){
			
			System.out.println("Se ha verificado un error inesperado.");
		}		
	}

	public void agregarClienteAlArchivo(Clientes cliente) throws IOException {
		
		FileWriter clientesFile = new FileWriter("./archivos/CLIENTES.txt", true);
		PrintWriter clientesOut = new PrintWriter(clientesFile);
		
		// numeroDocumento	int(8)
		// nombre 			char(20) 
		// apellido			char(20)
		// fechaNacimiento 	char(8)
		
		String numeroDocumento = Validador.fillString(String.valueOf(cliente.getNumeroDocumento()).trim(), 8,"0", true);
		String nombre = Validador.fillString(cliente.getNombre().trim(), 20," ", false);
		String apellido = Validador.fillString(cliente.getApellido().trim(), 20," ", false);
		String fechaNacimiento = Validador.calendarToString(cliente.getFechaNacimiento(), "yyyyMMdd").trim();
		
		clientesOut.println(numeroDocumento + nombre + apellido + fechaNacimiento);
		
		clientesOut.close();
		
		this.clientes.add(cliente);
	}
	
	public ArrayList<Clientes> getClientes() {
		return clientes;
	}

	public Clientes loadClientePorNumeroDocumento(int numeroDocumento) throws ClienteNoRegistradoException {
		
		for (int i = 0; i < this.getClientes().size(); i++) {

			Clientes cliente = this.getClientes().get(i);

			if(cliente.getNumeroDocumento() == numeroDocumento){
				
				return cliente;		
			}
		}

		throw new ClienteNoRegistradoException(numeroDocumento);
	}
}
