package com.remington.bancoalimentos.controller;

import com.remington.bancoalimentos.service.AnalisisIAService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ia")
public class IAController {

    private final AnalisisIAService analisisIAService;

    public IAController(AnalisisIAService analisisIAService) {
        this.analisisIAService = analisisIAService;
    }

    @GetMapping
    public String vistaIA(Model model) {
        model.addAttribute("resultado", null);
        model.addAttribute("pregunta", "");
        return "ia";
    }

    @PostMapping("/analisis-predictivo")
    public String generarAnalisisPredictivo(Model model) {
        String resultado = analisisIAService.generarAnalisisPredictivo();
        model.addAttribute("resultado", resultado);
        model.addAttribute("tipo", "ANÁLISIS PREDICTIVO DE DEMANDA Y EXISTENCIAS (GROQ CLOUD)");
        return "ia";
    }

    @PostMapping("/consultar")
    public String consultarIA(@RequestParam String pregunta, Model model) {
        if (pregunta == null || pregunta.isBlank()) {
            model.addAttribute("resultado", "Por favor ingresa una pregunta o consulta logística.");
        } else {
            String respuesta = analisisIAService.consultarIA(pregunta);
            model.addAttribute("resultado", respuesta);
            model.addAttribute("pregunta", pregunta);
            model.addAttribute("tipo", "CONSULTA ASISTENTE INTELIGENTE");
        }
        return "ia";
    }
}
