package es.florida;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.BufferedReader;

public class GestorDescargasAll {

	public void descargarArchivoAll( String url_descargar,	String nombreArchivo){
		System.out.println("Descargando: " + url_descargar);
			
		try {
			
			URL laUrl=new URL(url_descargar);
			InputStream is = laUrl.openStream();
			
			FileOutputStream fos = new FileOutputStream(nombreArchivo);
			
			byte[] buffer = new byte[8192];
            int bytesRead;
            
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
			
            fos.close();
			is.close();
			
			System.out.println("Archivo " + nombreArchivo + " copiado con éxito!");
			
			} catch (MalformedURLException e) {
				System.out.println("URL mal escrita!");
			} catch (IOException e) {
				System.out.println("Fallo en la lectura del fichero");
			}
	}
	
	public static void main(String[] args) {	
		GestorDescargasAll gd=new GestorDescargasAll();
		String url ="http://localhost:80"+"/PSPEjerciciosSockets/gatito1.png";
		String fichero = "gatito.png";
		gd.descargarArchivoAll(url,fichero);
	}
}