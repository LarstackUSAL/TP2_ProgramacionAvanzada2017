package ar.edu.usal.hotel.model.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import ar.edu.usal.hotel.model.dto.ClientesDto;
import ar.edu.usal.hotel.model.dto.PreciosDto;

public class ClientesDao {

	private ArrayList<ClientesDto> clientes;
	
	public ClientesDao(){
		
		this.clientes = new ArrayList<ClientesDto>();
	}
	
	public void loadClientes(){
		
		File clientesFile = new File("./archivos/CLIENTES.txt");
		Scanner clientesScanner;
		
		try {
			
			clientesScanner = new Scanner(clientesFile);
			
			while(clientesScanner.hasNextLine()){
				
				String clienteTxt = clientesScanner.nextLine();
				
				//agarrar del archivo los siguientes atributos de cliente y cargar los objetos.	
				
				nombre; 10
				apellido; 10
				fechaNacimiento; 8
				cupones; //los cupones hay que cargarlos en CuponesDao
			}
			
		}catch(InputMismatchException e){
			
			System.out.println("Se ha encontrado un tipo de dato insesperado.");
			
		}catch (FileNotFoundException e) {
			
			System.out.println("No se ha encontrado el archivo.");
		}

	}
}
