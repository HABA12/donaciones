package com.remington.bancoalimentos.service.impl;

import com.remington.bancoalimentos.model.entity.*;
import com.remington.bancoalimentos.model.repository.*;
import com.remington.bancoalimentos.service.BusinessRuleException;
import com.remington.bancoalimentos.service.DonacionService;
import com.remington.bancoalimentos.service.InventarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DonacionServiceImpl implements DonacionService {

    private final DonacionRepository donacionRepository;
    private final DonanteRepository donanteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final DetalleDonacionRepository detalleDonacionRepository;
    private final InventarioService inventarioService;

    public DonacionServiceImpl(DonacionRepository donacionRepository, DonanteRepository donanteRepository,
                               UsuarioRepository usuarioRepository, ProductoRepository productoRepository,
                               DetalleDonacionRepository detalleDonacionRepository, InventarioService inventarioService) {
        this.donacionRepository = donacionRepository;
        this.donanteRepository = donanteRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.detalleDonacionRepository = detalleDonacionRepository;
        this.inventarioService = inventarioService;
    }

    @Override
    @Transactional
    public Donacion registrarDonacionConDetalle(Long idDonante, Long idUsuario, LocalDate fecha,
                                              Long idProducto, BigDecimal cantidad, String estadoAlimento, String observaciones) {
        // RN-04: Donante debe existir y estar activo
        Donante donante = donanteRepository.findById(idDonante)
                .orElseThrow(() -> new BusinessRuleException("Donante no encontrado: " + idDonante));
        if (!"ACTIVO".equalsIgnoreCase(donante.getEstado())) {
            throw new BusinessRuleException("RN-04: No se permite registrar donaciones de donantes inactivos.");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new BusinessRuleException("Usuario receptor no encontrado: " + idUsuario));

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new BusinessRuleException("Producto no encontrado: " + idProducto));

        if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleException("La cantidad donada debe ser mayor a cero.");
        }

        // RN-14: Evaluación de calidad del alimento
        if ("RECHAZADO".equalsIgnoreCase(estadoAlimento)) {
            throw new BusinessRuleException("RN-14: El alimento se encuentra averiado o no cumple condiciones para su recepcion.");
        }

        Donacion donacion = new Donacion();
        donacion.setDonante(donante);
        donacion.setUsuarioRegistra(usuario);
        donacion.setFechaDonacion(fecha != null ? fecha : LocalDate.now());
        donacion.setObservaciones(observaciones);
        donacion.setEstado("ACTIVO");

        DetalleDonacion detalle = new DetalleDonacion();
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setEstadoAlimento("APTO");
        detalle.setEstado("ACTIVO");

        donacion.addDetalle(detalle);
        Donacion saved = donacionRepository.save(donacion);

        // Actualizar Inventario (Suma de Stock)
        inventarioService.incrementarStock(idProducto, cantidad);

        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Donacion> listarTodas() {
        return donacionRepository.findAll();
    }

    @Override
    @Transactional
    public void inactivarDonacion(Long idDonacion) {
        Donacion d = donacionRepository.findById(idDonacion)
                .orElseThrow(() -> new BusinessRuleException("Donacion no encontrada: " + idDonacion));
        if (!d.getDetalles().isEmpty()) {
            throw new BusinessRuleException("RN-11: No se permite inactivar una donación que contenga detalles de alimentos.");
        }
        d.setEstado("INACTIVO");
        donacionRepository.save(d);
    }
}
