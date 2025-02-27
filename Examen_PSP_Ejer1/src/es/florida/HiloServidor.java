package es.florida;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Scanner;

public class HiloServidor implements Runnable {

	private Socket cliente;
	private String nombreUsuario;
	private String pass;
	private Scanner scanner;
	private InputStreamReader isr;
	private BufferedReader br;
	private PrintWriter pw;

	public HiloServidor(Socket cliente) {
		super();
		this.cliente = cliente;
	}

	public String getNombreUsuario() {
		return this.nombreUsuario;
	}

	public String getPass() {
		return this.pass;
	}

	public Socket getCliente() {
		return cliente;
	}

	

	@Override
	public void run() {
		try {
			// Se inicializan los streams de entrada y salida para comunicarse con el
			// cliente.
			isr = new InputStreamReader(cliente.getInputStream());
			br = new BufferedReader(isr);
			pw = new PrintWriter(cliente.getOutputStream());
			
			String mensajeUser = br.readLine();
			String usuario = br.readLine();
			System.err.println("Mensaje de coincidencia-> " + mensajeUser);
			
			if(mensajeUser.equals("OK")) {
				String horaConexion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMDD_HHmmss"));
				String nombreFichero = horaConexion + "_log_" + usuario + ".dat";
				
				File fichero = new File(nombreFichero);
				FileWriter fw = new FileWriter(fichero);
				fw.write(usuario);
				fw.close();
				br.readLine();
				br.readLine();
				

				System.out.println("Servidor recibe las líneas del server.");
				String cantDeLineasStr = br.readLine();
				int cantDeLineas = Integer.parseInt(cantDeLineasStr);
				
				System.err.println("lee lo del Cliente");
				String lineaRecibida = br.readLine();
				String linea = "";
				for (int i = 0; i < cantDeLineas; i++) {
					System.out.println(linea);
				}
			}
			
		} catch (IOException e) {
			System.err.println("HILO >>> Error en la comunicación con el cliente.");
			e.printStackTrace();
		} 
	}
}
