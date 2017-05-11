package ar.edu.usal.hotel.model.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ar.edu.usal.hotel.model.dto.Clientes;
import ar.edu.usal.hotel.model.dto.ClientesHabitacion;
import ar.edu.usal.hotel.model.dto.Cupones;
import ar.edu.usal.hotel.model.dto.Habitaciones;
import ar.edu.usal.hotel.utils.Validador;

public class ClientesHabitacionDao {

	private static ClientesHabitacionDao clientesHabitacionInstance = null;
	
	private ArrayList<ClientesHabitacion> clientesHabitacion;
	
	private ClientesHabitacionDao(){
		
		this.clientesHabitacion = new ArrayList();
	}
	
	public static ClientesHabitacionDao getInstance(){
		
		if(clientesHabitacionInstance==null){
			
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
			
			for (int j = 0; j < clientesHabitacion.getClientes().size(); j++) {
				
				Clientes clienteIterado = clientesHabitacion.getClientes().get(j);
				
				if(clienteIterado.getNumeroDocumento() == cliente.getNumeroDocumento()){
					
					habitacion = clientesHabitacion.getHabitacion();
					break clientesHabitacionTag;
				}
			}
		}
		
		return habitacion;
	}
	
	public ArrayList<Clientes> loadClientesDeLaHabitacion(int numeroHabitacion){
		
		for (int i = 0; i < this.clientesHabitacion.size(); i++) {
			
			ClientesHabitacion clientesHabitacion = this.clientesHabitacion.get(i);
			
			if(clientesHabitacion.getHabitacion().getNumero() == numeroHabitacion){
				
				return clientesHabitacion.getClientes();
			}
			
		}
		
		return null;
	}
	
	public ClientesHabitacion loadClientesHabitacionPorNumero(int numeroHabitacion){
		
		for (int i = 0; i < this.clientesHabitacion.size(); i++) {
			
			ClientesHabitacion clientesHabitacion = this.clientesHabitacion.get(i);
			
			if(clientesHabitacion.getHabitacion().getNumero() == numeroHabitacion){
				
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
			
			String numeroHabitacion = String.valueOf(estadia.getHabitacion().getNumero());
			String fechaCheckIn = Validador.calendarToString(estadia.getFechaIngreso(), "yyyyMMdd");
			String fechaCheckOut = Validador.calendarToString(estadia.getFechaEgreso(), "yyyyMMdd");
			String diasPermanencia = String.valueOf(estadia.getDiasPermanencia()); 
			
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
					
					if(j == (estadia.getClientes().size() - 1)){
						
						listaDocumentos += ";";
					}
				}
			}
			
			estadiasOut.println(
					numeroHabitacion + ";" +
					fechaCheckIn + ";" +
					fechaCheckOut + ";" +
					diasPermanencia + ";" +
					listaDocumentos
					);
		}

		estadiasOut.close();
		estadiasFile.close();
	}	
}