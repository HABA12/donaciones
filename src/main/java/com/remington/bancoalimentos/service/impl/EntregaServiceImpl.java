package com.remington.bancoalimentos.service.impl;

import com.remington.bancoalimentos.model.entity.*;
import com.remington.bancoalimentos.model.repository.*;
import com.remington.bancoalimentos.service.BusinessRuleException;
import com.remington.bancoalimentos.service.EntregaService;
import com.remington.bancoalimentos.service.InventarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class EntregaServiceImpl implements EntregaService {

    private final EntregaRepository entregaRepository;
    private final BeneficiarioRepository beneficiarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final InventarioService inventarioService;

    public EntregaServiceImpl(EntregaRepository entregaRepository, BeneficiarioRepository beneficiarioRepository,
                              UsuarioRepository usuarioRepository, ProductoRepository productoRepository,
                              InventarioService inventarioService) {
        this.entregaRepository = entregaRepository;
        this.beneficiarioRepository = beneficiarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.inventarioService = inventarioService;
    }

    @Override
    @Transactional
    public Entrega registrarEntregaConDetalle(Long idBeneficiario, Long idUsuario, LocalDate fecha,
                                            String ruta, Long idProducto, BigDecimal cantidad, String observaciones) {
        Beneficiario ben = beneficiarioRepository.findById(idBeneficiario)
                .orElseThrow(() -> new BusinessRuleException("Beneficiario no encontrado: " + idBeneficiario));

        // RN-12: Exclusivo Estratos 1 a 3
        if (ben.getEstratoSocioeconomico() == null || ben.getEstratoSocioeconomico() < 1 || ben.getEstratoSocioeconomico() > 3) {
            throw new BusinessRuleException("RN-12: El beneficiario debe pertenecer exclusivamente a estratos 1, 2 o 3.");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new BusinessRuleException("Usuario responsable no encontrado: " + idUsuario));

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new BusinessRuleException("Producto no encontrado: " + idProducto));

        // RN-16: Validar y descontar stock antes de persistir la entrega
        inventarioService.descontarStock(idProducto, cantidad);

        Entrega entrega = new Entrega();
        entrega.setBeneficiario(ben);
        entrega.setUsuarioResponsable(usuario);
        entrega.setFechaEntrega(fecha != null ? fecha : LocalDate.now());
        entrega.setRutaEntrega(ruta != null ? ruta : "Ruta General Medellín");
        entrega.setObservacionesEvidencia(observaciones);
        entrega.setEstado("ACTIVO");

        DetalleEntrega det = new DetalleEntrega();
        det.setProducto(producto);
        det.setCantidad(cantidad);
        det.setEstado("ACTIVO");

        entrega.addDetalle(det);
        return entregaRepository.save(entrega);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entrega> listarTodas() {
        return entregaRepository.findAll();
    }
}
