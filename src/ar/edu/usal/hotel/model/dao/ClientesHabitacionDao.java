package ar.edu.usal.hotel.model.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ar.edu.usal.hotel.model.dto.Clientes;
import ar.edu.usal.hotel.model.dto.ClientesHabitacion;
import ar.edu.usal.hotel.model.dto.Habitaciones;
import ar.edu.usal.hotel.model.interfaces.ICalculoImportes;

public class ClientesHabitacionDao implements ICalculoImportes{

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
	
}
