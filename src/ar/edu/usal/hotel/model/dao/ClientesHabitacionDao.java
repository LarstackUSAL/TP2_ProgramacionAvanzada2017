package ar.edu.usal.hotel.model.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ar.edu.usal.hotel.model.dto.ClientesHabitacion;

public class ClientesHabitacionDao {

	private static ClientesHabitacionDao clientesHabitacionInstance = null;
	
	private ArrayList<ClientesHabitacion> clientesHabitacion;
	
	private ClientesHabitacionDao(){
		
		this.setClientesHabitacion(new ArrayList());
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

	public void setClientesHabitacion(ArrayList<ClientesHabitacion> clientesHabitacion) {
		this.clientesHabitacion = clientesHabitacion;
	}
	
	
}
