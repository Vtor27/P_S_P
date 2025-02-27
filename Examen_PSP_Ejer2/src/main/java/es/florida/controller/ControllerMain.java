package es.florida.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
@RequestMapping("/verne")
public class ControllerMain {
//	http://localhost:8080/verne/
	public static String rutaUser = "./usuarios";

	public JSONArray leerJson(String rutaJson) throws IOException {
		StringBuilder jsonContent = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(rutaJson))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				jsonContent.append(linea);
			}
		}
		return new JSONArray(jsonContent.toString());
	}
	
	@GetMapping("/logout")
	ResponseEntity<Object> getLgout(@RequestParam(value = "usuario") String nombreUsuario) throws IOException {
		JSONArray usuarioJsonA = leerJson(rutaUser + "/" + nombreUsuario + ".json");
		
		JSONObject user = usuarioJsonA.getJSONObject(0);
		System.out.println(user);
		
		String online = user.getString("online");
		if(online.equals("true")) {
			online = "false";
			return ResponseEntity.noContent().header("Content-Length", "0").build();
		}
		return ResponseEntity.noContent().header("Content-Length", "0").build();
	}
	
	
	@PostMapping("/login")
	ResponseEntity<Object> postBodyLogin(@RequestBody String cuerpoPeticion) throws IOException {
		JSONObject nuevaRegistro = new JSONObject(cuerpoPeticion);

		String user = nuevaRegistro.getString("user");
		String password = nuevaRegistro.getString("password");

		JSONObject registro = new JSONObject();
		registro.put("user", user);
		registro.put("password", password);

		File carpetaUsers = new File(rutaUser);
		File[] nombreUsuarios = carpetaUsers.listFiles();

		for (File usuario : nombreUsuarios) { // Recorre tantos txt de la carpeta pelis como haya.
			if (usuario.equals(user)) {
				try (BufferedReader br = new BufferedReader(new FileReader(usuario))) {
					br.readLine(); 
					String passJson = br.readLine();
					if(passJson.equals(password)) {
						registro.put("online", "true");
					}
					
	
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return ResponseEntity.noContent().header("Content-Length", "0").build();
		}
		
		return ResponseEntity.noContent().header("Content-Length", "0").build();
	}

	@PostMapping("/registro")
	ResponseEntity<Object> postBodyRegistro(@RequestBody String cuerpoPeticion) throws IOException {
		JSONObject nuevaRegistro = new JSONObject(cuerpoPeticion);

		String user = nuevaRegistro.getString("user");
		String password = nuevaRegistro.getString("password");

		JSONObject registro = new JSONObject();
		registro.put("user", user);
		registro.put("password", password);
		registro.put("online", "false");

		System.out.println("Nuevo usuario creado;");
		try(FileWriter file = new FileWriter(user + ".json")){
			file.write(registro.toString(4));
		}

		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

}
