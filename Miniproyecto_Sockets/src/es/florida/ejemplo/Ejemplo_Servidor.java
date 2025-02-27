package es.florida.ejemplo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Ejemplo_Servidor {
	
		public static void main(String[] args) {
			try (ServerSocket servidor = new ServerSocket(9090)) {
				System.out.println("SERVIDOR >>> Esperando conexión...");

				while (true) {
					Socket cliente = servidor.accept();
					System.out.println("SERVIDOR >>> Cliente conectado.");

					// Streams para texto
					BufferedReader br = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
					PrintWriter pw = new PrintWriter(cliente.getOutputStream(), true);

					// Streams para objetos
					ObjectOutputStream objOut = new ObjectOutputStream(cliente.getOutputStream());
					ObjectInputStream objIn = new ObjectInputStream(cliente.getInputStream());

					// Enviar un mensaje de bienvenida al cliente
					pw.println("Bienvenido al servidor!");

					// Enviar un objeto al cliente
					Ejemplo_Contrasenya obj = new Ejemplo_Contrasenya("clave123");
					objOut.writeObject(obj);
					objOut.flush();
					System.out.println("SERVIDOR >>> Objeto enviado al cliente.");

					// Recibir texto del cliente
					String mensajeCliente = br.readLine();
					System.out.println("SERVIDOR <<< Texto recibido: " + mensajeCliente);

					// Recibir objeto del cliente
					Ejemplo_Contrasenya objRecibido = (Ejemplo_Contrasenya) objIn.readObject();
					System.out.println("SERVIDOR <<< Objeto recibido: " + objRecibido.getPassPlain());

					// Cerrar conexión
					br.close();
					pw.close();
					objOut.close();
					objIn.close();
					cliente.close();
					System.out.println("SERVIDOR >>> Cliente desconectado.");
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

