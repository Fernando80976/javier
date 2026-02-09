package goya.daw2.dwes.ud3;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class Ejercicio2   {
	static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	static Map<String,String> userPasswords = new HashMap<String,String>();

	// Constructor:
	public Ejercicio2() {
		// crea un mapa con claves user1, user2 ... y sus valores los hashes correlativos
		// del siguiente array	
		final String[] HASHES = {
				"$2a$10$nR7E7eQbxXYbi/g9gPPr3.zkbh3dgdyMFHrLpJ58G3VcoEnyi2wWS",
				"$2a$10$IWn/W1pLUgHDxeKNIAuJqO5oS4adcN0fatFPudLst5yktYtyxpBzG",
				"$2a$10$w1SvJkFeE00t44a9vC9g1.OTu8m0zO9UOBeC94hI1HlrtuP5J9GQK",
				"$2a$10$wUrSYhItqPup8j75V7b20O1EU/NcS3k9X4vwCWBCmb2ffvmjbcCW.",
				"$2a$10$uqmjFIuDVuogWm5XvUXdGu3vHzefOmrnBCV9NXK.wURe5w.5Iu4xi",
				"$2a$10$iyOvUqRWzjrqyWzVTJgWkO/YOSynBmGfgMtp1rgq1r.8KbAiKA/Si"                      
		};
		int contador = 1;
		for (String hash : HASHES) {
			userPasswords.put("user" + contador, hash);
			contador++;
		}
	}
	
	@GetMapping("/Ejercicio2")
	public String inicio(HttpSession sessions, Model modelo) {
		if(sessions.getAttribute("nombre") != null && sessions.getAttribute("contraseña") != null) {
			modelo.addAttribute("nombre", sessions.getAttribute("nombre"));
		}
		return "login";
	}
	
	@GetMapping("/secreto")
	public String secreto(HttpSession sessions) {
		if(sessions.getAttribute("nombre") != null && sessions.getAttribute("contraseña") != null) {
			return "secreto";
		}
		return "redirect:/Ejercicio2";
	}
	
	@GetMapping("/registro")
	public String registro() {
		return "registro";
	}
	
	@PostMapping("/registro")
	public String ProcesarRegistro(@RequestParam(name="nombre", required = false) String nombre,
			 					   @RequestParam(name="contraseña", required = false) String contraseña,
			 					   Model modelo) {
		userPasswords.put(nombre, encoder.encode(contraseña));
		if(userPasswords.containsKey(nombre)) {
			modelo.addAttribute("nombre", nombre);
		}
		return "registro";
	}
	
	@PostMapping("/cerrar")
	public String cerrarSesion(HttpSession sessions) {
		sessions.setAttribute("nombre", null);
		sessions.setAttribute("contraseña", null);
		return "redirect:/Ejercicio2";
	}
	
	@PostMapping("/Ejercicio2")
	public String procesarInicio(@RequestParam(name="nombre", required = false) String nombre,
								 @RequestParam(name="contraseña", required = false) String contraseña,
								 Model modelo, HttpSession sessions) {
		
		String[] usuarios=userPasswords.keySet().toArray(new String[0]);
		
		String error="";
		
		if(nombre == null || nombre.isBlank()) {
			error="El nombre no puede estar en blanco";
			modelo.addAttribute("errores", error);
		}
		
		if(contraseña == null || contraseña.isBlank()) {
			error="La contraseña no puede estar en blanco";
			modelo.addAttribute("errores", error);
		}
		
		String hash = userPasswords.get(nombre);
		
		//OtraForma de hacerlo (no es la que hice en el examen)
		if(hash != null) {
			if(encoder.matches(contraseña, userPasswords.get(nombre))){
				sessions.setAttribute("nombre", nombre);
				sessions.setAttribute("contraseña", userPasswords.get(nombre));
				modelo.addAttribute("nombre", nombre);
			}
		}
		
		//Como lo hice en el examen
		/*
		for (String user : usuarios) {
			if (nombre.equals(user)) {
				if(encoder.matches(contraseña, userPasswords.get(user))) {
					sessions.setAttribute("nombre", user);
					sessions.setAttribute("contraseña", userPasswords.get(user));
					modelo.addAttribute("nombre", user);
				}
			}
		}
		*/
		
		if(sessions.getAttribute("nombre") == null && sessions.getAttribute("contraseña") == null && error=="") {
			modelo.addAttribute("errores", "usuario y/o contraseña incorrectos");
		}
		
		return "login";
	}
	
	
}
