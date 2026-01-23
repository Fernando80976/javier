package com.example.demo;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entities.Jugador;
import com.example.entities.Partida;
import com.example.repositories.JugadorRepository;
import com.example.repositories.PartidaRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class FormsController {
	private final JugadorRepository jugadorRepo;
    private final PartidaRepository partidaRepo;
    static final String[] SIGNOS = { "", "Aries", "Tauro", "Géminis", "Cáncer", "Leo", "Virgo", "Libra", "Escorpio",
            "Sagitario", "Capricornio", "Acuario", "Piscis" };
    static final String[] AFICCIONES = { "Deportes", "Juerga", "Lectura", "Relaciones sociales" };
    public FormsController(JugadorRepository jugadorRepo, PartidaRepository partidaRepo) {
        this.jugadorRepo = jugadorRepo;
        this.partidaRepo = partidaRepo;
    }
    @GetMapping("/")
    String etapa1() {
        return "etapa1";
    }

    @PostMapping("/")
    String procesaEtapaX(
            @RequestParam(name = "numEtapa") Integer numEtapa,
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "signo", required = false) String signo,
            @RequestParam(name = "aficciones", required = false) String aficciones,
            HttpSession session,
            Model modelo) {

        // nombre
        if (nombre != null) {
            session.setAttribute("nombre", nombre);
        } else if (session.getAttribute("nombre") != null) {
            nombre = (String) session.getAttribute("nombre");
        }

        // signo
        if (signo != null) {
            session.setAttribute("signo", signo);
        } else if (session.getAttribute("signo") != null) {
            signo = (String) session.getAttribute("signo");
        }

        // aficciones
        if (aficciones != null) {
            session.setAttribute("aficciones", aficciones);
        } else if (session.getAttribute("aficciones") != null) {
            aficciones = (String) session.getAttribute("aficciones");
        }

        String errores = "";

        if (numEtapa == 1 && (nombre == null || nombre.isBlank())) {
            errores = "Debes poner un nombre no vacío";
        } else if (numEtapa == 1 && (nombre.length() < 3 || nombre.length() > 10)) {
            errores = "La longitud del nombre debe estar entre 3 y 10";
        }

        if (numEtapa == 2 && (signo == null || signo.equals("0"))) {
            errores = "Debes seleccionar un signo";
        }

        if (numEtapa == 3 && (aficciones == null || aficciones.isBlank())) {
            errores = "Debes elegir al menos una aficción, no seas soso/a";
        }

        modelo.addAttribute("signos", SIGNOS);
        modelo.addAttribute("aficciones", AFICCIONES);

        if (!errores.isBlank()) {
            modelo.addAttribute("errores", errores);
            modelo.addAttribute("numEtapa", numEtapa);
            return "etapa" + numEtapa;
        }

        numEtapa++;
        modelo.addAttribute("numEtapa", numEtapa);

        if (numEtapa == 4) {
            //  Calcular puntuacion
            int puntuacion = 0;
            if (nombre != null && !nombre.isBlank()) puntuacion++;
            if (signo != null && !signo.isBlank()) puntuacion++;
            if (aficciones != null && !aficciones.isBlank()) puntuacion++;

            // buscamos jugadores por nombre o  sino crear uno nuevo
            Jugador jugador = jugadorRepo.findByNombre(nombre).orElse(null);
            if (jugador == null) {
                jugador = new Jugador();
                jugador.setNombre(nombre);
                jugador = jugadorRepo.save(jugador);
            }

            // Creamos  la partida
            Partida partida = new Partida();
            partida.setJugador(jugador);
            partida.setPuntuacion(puntuacion);
            partida.setFecha(java.time.LocalDateTime.now());
            partida.setSigno(signo);
            partida.setAficciones(aficciones);
            partida.setCategoria(calcularCategoria(puntuacion)); 

            // Guardar la partida
            partidaRepo.save(partida);

           
            ArrayList<String> respuestas = new ArrayList<>();
            respuestas.add("Nombre: " + nombre);
            respuestas.add("Signo: " + signo);
            respuestas.add("Aficciones: " + aficciones);
            respuestas.add("Puntuación: " + puntuacion);
            respuestas.add("Categoría: " + partida.getCategoria());
            modelo.addAttribute("respuestas", respuestas);

            session.invalidate();
        }



        return "etapa" + numEtapa;
    }
    @GetMapping("/reporte")
    public String Reporte(Model modelo) {
    	modelo.addAttribute("partidas", partidaRepo.findByOrderBySignoDesc());
    	return "reporte";
    }
    private String calcularCategoria(int puntuacion) {
        if (puntuacion <= 1) {
            return "MALA";
        } else if (puntuacion == 2) {
            return "REGULAR";
        } else if (puntuacion == 3) {
            return "BUENA";
        } else { // puntuacion >= 4
            return "EXCELENTE";
        }
    }
}
