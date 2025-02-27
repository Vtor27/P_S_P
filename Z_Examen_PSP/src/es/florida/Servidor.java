package es.florida;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	
	//SERVIDOR LISTO PARA FUNCIONAR, FALTA ADECUARLO.
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		System.err.println("SERVIDOR ::> Arranca el servidor, espera peticion");
		ServerSocket socketEscucha = null;
		try {
			socketEscucha = new ServerSocket(8080);

		} catch (IOException e) {
			System.err.println("SERVIDOR ::> Error");
			return;
		}

		while (true) {
			System.out.println("SERVIDOR >>> El servidor esta a la escucha.");	//ESPERA LA CONEXIÓN CON EL CLIENTE.
			Socket conexion = socketEscucha.accept();
			System.err.println("SERVIDOR >>> Conexion recibida --> Lanza hilo clase Peticion");
			HiloServidor hs = new HiloServidor(conexion);
			Thread hilo = new Thread(hs);
			hilo.start();
		}
	}

}