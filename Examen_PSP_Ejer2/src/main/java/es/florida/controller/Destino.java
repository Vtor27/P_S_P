package es.florida.controller;

public class Destino {
	String destino, region;
	int precio;
	
	public Destino(String destino, String region, int precio) {
		super();
		this.destino = destino;
		this.region = region;
		this.precio = precio;
	}
	
	public Destino() {
		super();
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}
	
	
}
