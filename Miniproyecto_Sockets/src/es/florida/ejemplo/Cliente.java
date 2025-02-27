package es.florida.ejemplo;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	public static void main(String[] args) {
		try (Socket cliente = new Socket("localhost", 9090)) {
			System.out.println("CLIENTE >>> Conectado al servidor.");

			// Streams para texto
			BufferedReader br = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			PrintWriter pw = new PrintWriter(cliente.getOutputStream(), true);

			// Streams para objetos
			ObjectOutputStream objOut = new ObjectOutputStream(cliente.getOutputStream());
			ObjectInputStream objIn = new ObjectInputStream(cliente.getInputStream());

			// Recibir mensaje de bienvenida del servidor
			String mensajeServidor = br.readLine();
			System.out.println("CLIENTE <<< Mensaje del servidor: " + mensajeServidor);

			// Recibir un objeto del servidor
			Ejemplo_Contrasenya objRecibido = (Ejemplo_Contrasenya) objIn.readObject();
			System.out.println("CLIENTE <<< Objeto recibido: " + objRecibido.getPassPlain());

			// Enviar un mensaje al servidor
			Scanner scanner = new Scanner(System.in);
			System.out.print("CLIENTE >>> Escribe un mensaje para el servidor: ");
			String mensaje = scanner.nextLine();
			pw.println(mensaje);

			// Enviar un objeto al servidor
			Ejemplo_Contrasenya objEnvio = new Ejemplo_Contrasenya("miClaveSecreta");
			objOut.writeObject(objEnvio);
			objOut.flush();
			System.out.println("CLIENTE >>> Objeto enviado al servidor.");

			// Cerrar conexión
			br.close();
			pw.close();
			objOut.close();
			objIn.close();
			scanner.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
