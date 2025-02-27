package es.florida;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		System.err.println("SERVIDOR ::> Arranca el servidor, espera peticion");
		ServerSocket socketEscucha = null;
		try {
			socketEscucha = new ServerSocket(9988);

		} catch (IOException e) {
			System.err.println("SERVIDOR ::> Error");
			return;
		}

		while (true) {
			Socket conexion = socketEscucha.accept();
			System.err.println("SERVIDOR >>> Conexion recibida --> Lanza hilo clase Peticion");
			SvrHiloPeticion p = new SvrHiloPeticion(conexion);
			Thread hilo = new Thread(p);
			hilo.start();
		}
	}

}
