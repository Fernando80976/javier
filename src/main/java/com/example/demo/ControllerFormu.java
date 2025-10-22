package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControllerFormu {

    // Página inicial
    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("datos", new ArrayList<String>());
        model.addAttribute("error", null);
        return "datos_personales";
    }

    // Formulario 1 - Datos personales
    @PostMapping("/datos_personales")
    public String datosPer(
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "edad", required = false) String edadStr,
            @RequestParam(value = "datos", required = false) List<String> datos,
            Model model) {

        List<String> errores = new ArrayList<>();

        // Validación simple
        if (nombre == null || nombre.trim().isEmpty()) errores.add("El nombre no puede estar vacío.");
        if (email == null || !email.contains("@")) errores.add("El email no es válido.");

        int edad = -1;
        try {
            edad = Integer.parseInt(edadStr);
            if (edad < 0) errores.add("La edad no puede ser negativa.");
        } catch (Exception e) {
            errores.add("La edad debe ser un número.");
        }

        // Si hay errores, vuelve al formulario
        if (!errores.isEmpty()) {
            model.addAttribute("errores", errores);
            model.addAttribute("datos", datos != null ? datos : new ArrayList<>());
            return "datos_personales";
        }

        // Si todo está bien, agregar a la lista
        if (datos == null) datos = new ArrayList<>();
        datos.add("Nombre: " + nombre);
        datos.add("Email: " + email);
        datos.add("Edad: " + edad);

        model.addAttribute("datos", datos);
        return "datosViaje";
    }

    // Formulario 2 - Datos del viaje
    @PostMapping("/datosViaje")
    public String datosVia(
            @RequestParam(value = "destino", required = false) String destino,
            @RequestParam(value = "tipoViaje", required = false) String tipoViaje,
            @RequestParam(value = "datos", required = false) List<String> datos,
            Model model) {

        List<String> errores = new ArrayList<>();

        if (destino == null || destino.isEmpty()) {
            errores.add("Debe seleccionar un destino.");
        }
        if (tipoViaje == null || tipoViaje.isEmpty()) {
            errores.add("Debe seleccionar un tipo de viaje.");
        }

        if (!errores.isEmpty()) {
            model.addAttribute("errores", errores);
            model.addAttribute("datos", datos != null ? datos : new ArrayList<>());
            return "datosViaje";
        }

        if (datos == null) datos = new ArrayList<>();
        datos.add("Destino: " + destino);
        datos.add("Tipo de viaje: " + tipoViaje);

        model.addAttribute("datos", datos);
        return "extras";
    }

    // Formulario 3 - Extras
    @PostMapping("/extras")
    public String extras(
            @RequestParam(value = "comidas", required = false) String[] comidas,
            @RequestParam(value = "seguro", required = false) String seguro,
            @RequestParam(value = "datos", required = false) List<String> datos,
            Model model) {

        List<String> errores = new ArrayList<>();

        if (seguro == null || seguro.isEmpty()) {
            errores.add("Debe seleccionar un tipo de seguro.");
        }

        if (!errores.isEmpty()) {
            model.addAttribute("errores", errores);
            model.addAttribute("datos", datos != null ? datos : new ArrayList<>());
            return "extras";
        }

        if (datos == null) datos = new ArrayList<>();
        datos.add("Comidas: " + (comidas != null ? String.join(", ", comidas) : "Ninguna"));
        datos.add("Seguro: " + seguro);

        model.addAttribute("datos", datos);
        return "preferencias";
    }
}
