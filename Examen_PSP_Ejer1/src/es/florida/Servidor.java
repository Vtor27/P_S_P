package es.florida;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	public static void main(String[] args) {
		System.err.println("SERVIDOR >>> Arranca el servidor, espera de petición... \n");
		ServerSocket socketEscucha = null;

		try {
			socketEscucha = new ServerSocket(5001);
		} catch (IOException e) {
			System.err.println("SERVIDOR >>> Error");
			e.printStackTrace();
			return;
		}

		while (true) {
			Socket conexion;
			try {
				conexion = socketEscucha.accept(); 
				System.err.println("SERVIDOR >>> Conexión recibida --> Lanza un HiloServer. \n");
				HiloServidor hs = new HiloServidor(conexion);
				Thread hilo = new Thread(hs);
				hilo.start();
			} catch (IOException e) {
				System.err.println("SERVIDOR >>> ERROR");
				e.printStackTrace();
			}
		}
	}

}
