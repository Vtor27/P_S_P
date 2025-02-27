package es.florida.controller;

import java.util.List;

public class Sala {
	String nombre;
	int capacidad;
	List<String> reservas;
	
	public Sala(String nombre, int capacidad, List<String> reservas) {
		super();
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.reservas = reservas;
	}
	
	public Sala() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public List<String> getReservas() {
		return reservas;
	}

	public void setReservas(List<String> reservas) {
		this.reservas = reservas;
	}

	
	@Override
	public String toString() {
		return "Sala [nombre=" + nombre + ", capacidad=" + capacidad + ", reservas=" + reservas + "]";
	}
}
