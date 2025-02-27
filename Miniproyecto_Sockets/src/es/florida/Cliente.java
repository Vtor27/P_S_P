package es.florida;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		String host = "localhost";
		int puerto = 9988;
		System.out.println("CLIENTE ::> Arranca cliente");
		Socket cliente = new Socket(host,puerto);
		
		ObjectInputStream objInput = new ObjectInputStream(cliente.getInputStream());
		Contrasenya passOb = (Contrasenya) objInput.readObject();
		System.out.println("CLIENTE << Recibo de servidor el objeto contraseña");
		
		String passplain;
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Ingresa tu contraseña: ");
			passplain = scanner.nextLine();
		}
		
		passOb.setPassPlain(passplain);

		ObjectOutputStream objOutput = new ObjectOutputStream(cliente.getOutputStream());
		objOutput.writeObject(passOb);
		System.out.println("CLIENTE >>  Envio a servidor el objeto contraseña con el texto plano relleno");
		
		passOb = (Contrasenya) objInput.readObject();
		System.out.println("CLIENTE << Recibo de servidor el objeto contraseña completo");
		System.out.println("CLIENTE ::> Contraseña en texto plano de la contraseñya: " + passOb.getPassPlain());
		System.out.println("CLIENTE ::> Contraseña hasheada de la contraseñya: " + passOb.getPassCript());
				
		cliente.close();
	}

}
