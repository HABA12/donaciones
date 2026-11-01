package com.remington.bancoalimentos.controller;

import com.remington.bancoalimentos.model.repository.BeneficiarioRepository;
import com.remington.bancoalimentos.model.repository.ProductoRepository;
import com.remington.bancoalimentos.model.repository.UsuarioRepository;
import com.remington.bancoalimentos.service.BusinessRuleException;
import com.remington.bancoalimentos.service.EntregaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/entregas")
public class EntregaController {

    private final EntregaService entregaService;
    private final BeneficiarioRepository beneficiarioRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public EntregaController(EntregaService entregaService, BeneficiarioRepository beneficiarioRepository,
                             ProductoRepository productoRepository, UsuarioRepository usuarioRepository) {
        this.entregaService = entregaService;
        this.beneficiarioRepository = beneficiarioRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("entregas", entregaService.listarTodas());
        model.addAttribute("beneficiarios", beneficiarioRepository.findByEstado("ACTIVO"));
        model.addAttribute("productos", productoRepository.findAll());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "entregas";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam Long idBeneficiario,
                          @RequestParam Long idUsuario,
                          @RequestParam(required = false) String fecha,
                          @RequestParam String ruta,
                          @RequestParam Long idProducto,
                          @RequestParam BigDecimal cantidad,
                          @RequestParam(required = false) String observaciones,
                          RedirectAttributes flash) {
        try {
            LocalDate f = (fecha != null && !fecha.isBlank()) ? LocalDate.parse(fecha) : LocalDate.now();
            entregaService.registrarEntregaConDetalle(idBeneficiario, idUsuario, f, ruta, idProducto, cantidad, observaciones);
            flash.addFlashAttribute("success", "¡Despacho de entrega formalizado con éxito y stock descontado!");
        } catch (BusinessRuleException e) {
            flash.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error inesperado al registrar entrega: " + e.getMessage());
        }
        return "redirect:/entregas";
    }
}
