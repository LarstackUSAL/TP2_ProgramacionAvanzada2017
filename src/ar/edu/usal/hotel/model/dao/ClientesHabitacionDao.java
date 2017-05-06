package ar.edu.usal.hotel.model.dao;

import java.util.ArrayList;

import ar.edu.usal.hotel.model.dto.ClientesHabitacion;

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
	
	public Calendar calcularFechaEgreso(Calendar fechaIngreso, int diasPermanencia){
		
		return ingreso + diasPermanencia
	}
	
	
}
