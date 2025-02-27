package es.florida;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SvrHiloPeticion implements Runnable {

	Socket conexion;
	BufferedReader bfr;
	PrintWriter pw;

	public static String hassMD5(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			byte[] passwordBytes = password.getBytes();

			byte[] hashBytes = md.digest(passwordBytes);

			StringBuilder sb = new StringBuilder();
			for (byte b : hashBytes) {
				sb.append(String.format("%02x", b));
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error al encriptar la contraseña: " + e.getMessage());
		}
	}

	public SvrHiloPeticion(Socket conexion) {
		super();
		this.conexion = conexion;
	}

	public BufferedReader getBfr() {
		return bfr;
	}

	public void setBfr(BufferedReader bfr) {
		this.bfr = bfr;
	}

	public PrintWriter getPw() {
		return pw;
	}

	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}

	@Override
	public void run() {

		try {
			Contrasenya passOb = new Contrasenya();
			ObjectOutputStream objOutput = new ObjectOutputStream(conexion.getOutputStream());
			objOutput.writeObject(passOb);
			System.err.println("SERVIDOR >> Envio el objeto contraseña vacio al cliente");

			ObjectInputStream objInput = new ObjectInputStream(conexion.getInputStream());
			passOb = (Contrasenya) objInput.readObject();
			System.err.println("SERVIDOR << Recibo de cliente el objeto a modificar: " + passOb.getPassPlain());

			String hash = hassMD5(passOb.getPassPlain());
			passOb.setPassCript(hash);
			objOutput.writeObject(passOb);
			System.err.println("SERVIDOR >> Envio el objeto contraseña completado al cliente");

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("SERVIDOR ::> Error.");
		}

	}

}
