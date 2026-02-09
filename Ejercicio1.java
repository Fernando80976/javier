package goya.daw2.dwes.ud3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class Ejercicio1 {
	static final String[] USUARIOS = { "Bob", "Calamardo", "Arenita" };

	@GetMapping("/Ejercicio1")
	public String listaChats(Model modelo) {
		modelo.addAttribute("usuarios", USUARIOS);
		return "chats";
	}

	@GetMapping("/chat")
	public String chat(@RequestParam(name = "usuario") String usuario, Model modelo, HttpSession session) {
		LocalDateTime hora = LocalDateTime.now();
		modelo.addAttribute("hora",hora.format(DateTimeFormatter.ofPattern("dd-MM-uu HH:mm")));
		modelo.addAttribute("usuario", usuario);
		
		session.setAttribute("usuario", usuario);
		if(session.getAttribute("mensajesInvertido_"+usuario) != null) {
			modelo.addAttribute("mensajes", session.getAttribute("mensajesInvertido_"+usuario));
		}
		
		return "chat";
	}
	
	@PostMapping("/Ejercicio1")
	public String ProcesarChat(@RequestParam(name = "mensaje", required = false) String mensaje,
							   @RequestParam(name = "enviar", required = false) String enviar,
							   @RequestParam(name = "borrar", required = false) String borrar, 
							   Model modelo, HttpSession session) {
		
		//NOTA: si al añadir en el arrayList lo hago en el 0 no haria falta el invertir
		
		LocalDateTime hora = LocalDateTime.now();
		ArrayList<String> mensajes= new ArrayList<String>();
		ArrayList<String> mensajesInvertidos= new ArrayList<String>();
		
		String usuario = (String) session.getAttribute("usuario");
		
		
		if(session.getAttribute("mensajes_"+usuario) != null){
			mensajes=(ArrayList<String>) session.getAttribute("mensajes_"+usuario);
		}
		
		if(enviar != null && enviar.equals("Enviar")) {
			mensajes.add(hora.format(DateTimeFormatter.ofPattern("dd-MM-uu HH:mm"))+":"+mensaje);
		
			session.setAttribute("mensajes_"+usuario, mensajes);
			
			for (int i = mensajes.size()-1; i >= 0; i--) {
				mensajesInvertidos.add(mensajes.get(i));
				
			}
			session.setAttribute("mensajesInvertido_"+usuario, mensajesInvertidos);
		}
		
		if(borrar != null && borrar.equals("Borrar")) {
			session.setAttribute("mensajesInvertido_"+usuario, null);
			session.setAttribute("mensajes_"+usuario, null);
		}
		
		modelo.addAttribute("mensajes", session.getAttribute("mensajesInvertido_"+usuario));
		
		
		modelo.addAttribute("hora",hora.format(DateTimeFormatter.ofPattern("dd-MM-uu HH:mm")));
		modelo.addAttribute("usuario", session.getAttribute("usuario"));
		return "chat";
	}

}
