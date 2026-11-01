package com.remington.bancoalimentos.controller;

import com.remington.bancoalimentos.model.repository.DonanteRepository;
import com.remington.bancoalimentos.model.repository.ProductoRepository;
import com.remington.bancoalimentos.model.repository.UsuarioRepository;
import com.remington.bancoalimentos.service.BusinessRuleException;
import com.remington.bancoalimentos.service.DonacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/donaciones")
public class DonacionController {

    private final DonacionService donacionService;
    private final DonanteRepository donanteRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public DonacionController(DonacionService donacionService, DonanteRepository donanteRepository,
                              ProductoRepository productoRepository, UsuarioRepository usuarioRepository) {
        this.donacionService = donacionService;
        this.donanteRepository = donanteRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("donaciones", donacionService.listarTodas());
        model.addAttribute("donantes", donanteRepository.findAll());
        model.addAttribute("productos", productoRepository.findAll());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "donaciones";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam Long idDonante,
                          @RequestParam Long idUsuario,
                          @RequestParam(required = false) String fecha,
                          @RequestParam Long idProducto,
                          @RequestParam BigDecimal cantidad,
                          @RequestParam(defaultValue = "APTO") String estadoAlimento,
                          @RequestParam(required = false) String observaciones,
                          RedirectAttributes flash) {
        try {
            LocalDate f = (fecha != null && !fecha.isBlank()) ? LocalDate.parse(fecha) : LocalDate.now();
            donacionService.registrarDonacionConDetalle(idDonante, idUsuario, f, idProducto, cantidad, estadoAlimento, observaciones);
            flash.addFlashAttribute("success", "¡Donación registrada con éxito y stock actualizado en bodega!");
        } catch (BusinessRuleException e) {
            flash.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error inesperado al registrar donación: " + e.getMessage());
        }
        return "redirect:/donaciones";
    }
}
