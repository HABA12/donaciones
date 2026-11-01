package com.remington.bancoalimentos.controller;

import com.remington.bancoalimentos.service.InventarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final InventarioService inventarioService;

    public HomeController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("inventarios", inventarioService.listarTodo());
        return "index";
    }
}
