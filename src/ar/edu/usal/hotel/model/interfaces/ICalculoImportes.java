package ar.edu.usal.hotel.model.interfaces;

import ar.edu.usal.hotel.model.dto.ClientesHabitacion;

public interface ICalculoImportes {

	public static final double PORCENTAJE_DESCUENTO = 0.055;
	public static final double PORCENTAJE_DESCUENTO_SUPERA_SIETE_DIAS = 0.08;
	public static final int DIAS_PARA_DESCUENTO = 7;
	
	public double calcularImporte(ClientesHabitacion clientesHabitacion);
}
