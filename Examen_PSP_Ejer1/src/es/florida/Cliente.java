package es.florida;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	private static final String host = "localhost";
	private static final int puerto = 5001;
	public static Scanner scanner;
	public static String rutaAutorizados = "./target/autorizados/Usuarios_autorizados.dat";

	public static String preguntarUser() {
		scanner = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		System.out.println("Nombre de usuario -> ");
		String usuario = scanner.nextLine();

		System.out.println("Contraseña -> ");
		String contraseña = scanner.nextLine();

		sb.append(usuario).append(":").append(contraseña);
		System.out.println("Usuario y contraseña que se envia: " + sb);
		return sb.toString();
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		try {
			System.out.println("CLIENTE >>> Conectando al servidor... \n");
			Socket cliente = new Socket(host, puerto);
			System.out.println("CLIENTE >>> Conexión establecida. \n");
			Thread.sleep(1000);
			InputStreamReader isr = new InputStreamReader(cliente.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			PrintWriter pw = new PrintWriter(cliente.getOutputStream(), true);

			System.out.println("Introduce el user completo.");

			String usuarioCompleto = preguntarUser();
			String nombreUser = usuarioCompleto.split(":")[0].trim();


			String file = rutaAutorizados;
			try (FileReader fr = new FileReader(file); BufferedReader brf = new BufferedReader(fr)) {
				String lineaFichero;
				while ((lineaFichero = brf.readLine()) != null) {
					if (lineaFichero.equals(usuarioCompleto)) {
						pw.println("OK");
						pw.println(nombreUser);
						pw.flush();
					} else {
						pw.println("ERROR");
						pw.println("Se cerró la conexión con el cliente.");
						pw.flush();
						cliente.close();
					}
				}
			}

			System.out.println("Cuantas lineas de texto quieres enviar? ");
			String cantLineasStr = scanner.nextLine();
			int cantLineas = Integer.parseInt(cantLineasStr);
			pw.println(cantLineas);
			pw.flush();

			System.err.println("Se van a enviar " + cantLineas);
			for (int i = 0; i < cantLineas; i++) {
				System.out.println("Escribe la línea " + (i + 1));
				String lineaEviar = scanner.nextLine();
				pw.println(lineaEviar);
				pw.flush();
			}

		} catch (IOException | InterruptedException e) {
			System.out.println("CLIENTE >>> Error de comunicación con el servidor...");
			e.printStackTrace();
		}
	}
}
