package es.florida.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/APIreservas")
public class ControllerMain {

	public static String rutaSalas = "./target/salas";
	public static JSONArray arraySalas = new JSONArray();
	public static JSONObject jsonAMostrar = new JSONObject();

	public static String leerSalas(String rutaPelis) throws IOException {
		File carpetaSalas = new File(rutaPelis);
		File[] salas = carpetaSalas.listFiles();

		for (File sala : salas) { // Recorre tantos txt de la carpeta pelis como haya.

			try (BufferedReader br = new BufferedReader(new FileReader(sala))) {

				String nombre = br.readLine();
				String[] nombrePartido = nombre.split(": ");
				String nombreSala = nombrePartido[1].trim();

				String capacidadStr = br.readLine();
				String[] capacidadPartida = capacidadStr.split(": ");
				int capacidad = Integer.parseInt(capacidadPartida[1].trim());

				br.readLine();

				JSONObject jsonSalas = new JSONObject();
				jsonSalas.put("nombre", nombreSala);
				jsonSalas.put("capacidad", capacidad);
				arraySalas.put(jsonSalas);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		jsonAMostrar.put("salas", arraySalas);

		return jsonAMostrar.toString();
	}
	
	public static String convertirNombre(String nombreSala) {
		if(nombreSala.equals("Sala Azul")) {
			return nombreSala = "sala1";
		} else if(nombreSala.equals("Sala Verde")){
			return nombreSala = "sala2";
		} else if(nombreSala.equals("Sala Roja")) {
			return nombreSala = "sala3";
		}
		return ""; 
	}

	public static ResponseEntity<Object> buscarNombre(String nombreSala) throws IOException { // .getJSONObject(x) recojes de un
																				// JSONArray el objeto
		// que esté en la posición de la x(indice)
		JSONObject jsonSalas = new JSONObject(leerSalas(rutaSalas));
		JSONArray arraySalas = jsonSalas.getJSONArray("salas");

		for (int i = 0; i < arraySalas.length(); i++) {
			JSONObject salaSeleccionada = arraySalas.getJSONObject(i);
			if (salaSeleccionada.getString("nombre").equalsIgnoreCase(nombreSala)) {
				String nombreConvert = convertirNombre(nombreSala);
						
				String infoSala = rutaSalas + "/" + nombreConvert + ".txt";

				try (BufferedReader br = new BufferedReader(new FileReader(infoSala))) {
					
					String nombre = br.readLine().split(": ")[1].trim();
					System.out.println("Sala buscada: " + nombre);

					String capacidadStr = br.readLine().split(": ")[1].trim();
					int capacidad = Integer.parseInt(capacidadStr);
					System.out.println("Capacidad de la sala buscada: " + String.valueOf(capacidad));

					br.readLine();
					JSONArray reservas = new JSONArray();
					 String linea;
	                    while ((linea = br.readLine()) != null) {
	                        reservas.put(linea.trim());
	                    }

	                    // Crear el JSON final con la información de la sala
	                    JSONObject jsonSala = new JSONObject();
	                    jsonSala.put("nombre", nombre);
	                    jsonSala.put("capacidad", capacidad);
	                    jsonSala.put("reservas", reservas);

	                    return ResponseEntity.ok().body(jsonSala.toString());
					
					//ESTO FORMATEA COMO LA PARTE DE ABAJO.
//					String linea;
//					JSONArray reservas = new JSONArray();
//					while ((linea = br.readLine()) != null) {
//						String[] partesReservas = linea.split(" - ");
//						if (partesReservas.length == 3) {
//							JSONObject reserva = new JSONObject();
//							reserva.put("fecha", partesReservas[0]);
//							reserva.put("horario", partesReservas[1]);
//							reserva.put("usuario", partesReservas[2]);
//							reservas.put(reserva);
//						}
//					}
					
//					{
//						  "reservas": [
//						    {
//						      "fecha": "2025-02-20 10:00",
//						      "horario": "12:00",
//						      "usuario": "Juan Pérez"
//						    },
//						    {
//						      "fecha": "2025-02-21 15:00",
//						      "horario": "17:00",
//						      "usuario": "María López"
//						    }
//						  ],
//						  "nombre": "Sala Azul",
//						  "capacidad": 10
//						}
				}
			}
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@GetMapping("/salas")
	String mostrarTodas() throws IOException {
		return leerSalas(rutaSalas);
	}

	@GetMapping("/reservas")
	ResponseEntity<Object> mostrarTodas(@RequestParam(value = "sala") String valor) throws IOException { // El sala del values es el
																							// nombre que va antes del
																							// =.
		try {
			String sala = valor;
			return buscarNombre(sala);
		} catch (NumberFormatException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

	}
	
	@PostMapping("/nuevaReserva")
	ResponseEntity<Object> postBodyNuevaReserva(@RequestBody String cuerpoPeticion) throws IOException {
		JSONObject nuevaReservaJson = new JSONObject(cuerpoPeticion);

		String usuario = nuevaReservaJson.getString("usuario");
		String sala = nuevaReservaJson.getString("sala");
		String fecha = nuevaReservaJson.getString("fecha");
		String horaInicio = nuevaReservaJson.getString("horaInicio");
		String horaFin = nuevaReservaJson.getString("horaFin");

	

			return ResponseEntity.noContent().header("Content-Length", "0").build();
		
	}

}
