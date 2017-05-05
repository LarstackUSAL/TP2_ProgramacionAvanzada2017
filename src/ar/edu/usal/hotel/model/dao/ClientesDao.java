package ar.edu.usal.hotel.model.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

import ar.edu.usal.hotel.model.dto.ClientesDto;
import ar.edu.usal.hotel.model.dto.CuponesDto;
import ar.edu.usal.hotel.utils.Validador;

public class ClientesDao {

	private static ClientesDao clientesDaoInstance = null;
	
	private ArrayList<ClientesDto> clientes;
	private ArrayList<ClientesDto> nuevosClientes;
	
	private ClientesDao(){
		
		this.clientes = new ArrayList<ClientesDto>();
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
			
			clientesScanner = new Scanner(clientesFile);
			
			CuponesDao cuponesDao = CuponesDao.getInstance();
			
			
			while(clientesScanner.hasNextLine()){
				
				String clienteTxt = clientesScanner.nextLine();
				
				String nombre = clienteTxt.substring(0, 20);
				String apellido = clienteTxt.substring(20, 40);
				int numeroDocumento = Integer.parseInt(clienteTxt.substring(40, 49));
				String fechaNacimientoTxt = clienteTxt.substring(49, 56);
				
				Calendar fechaNacimiento = Validador.stringToCalendar(fechaNacimientoTxt);
				
				CuponesDto cuponCliente = null;
				
				for (int i = 0; i < cuponesDao.getCupones().size(); i++) {
					
					CuponesDto cupon = cuponesDao.getCupones().get(i);
					
					if(cupon.getNumeroDocumento() == numeroDocumento){
						
						cuponCliente = cupon;
						
						break;
					}
				}
				
				ClientesDto cliente = new ClientesDto(numeroDocumento, nombre, apellido, fechaNacimiento, cuponCliente);
				this.clientes.add(cliente);			
			}
			
		}catch(InputMismatchException e){
			
			System.out.println("Se ha encontrado un tipo de dato insesperado.");
			
		}catch (FileNotFoundException e) {
			
			System.out.println("No se ha encontrado el archivo.");
		}catch(Exception e){
			
			System.out.println("Se ha verificado un error inesperado.");
		}

	}

	public ArrayList<ClientesDto> getClientes() {
		return clientes;
	}
}
