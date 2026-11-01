package com.remington.bancoalimentos.service.impl;

import com.remington.bancoalimentos.model.entity.Inventario;
import com.remington.bancoalimentos.model.entity.Producto;
import com.remington.bancoalimentos.model.repository.InventarioRepository;
import com.remington.bancoalimentos.model.repository.ProductoRepository;
import com.remington.bancoalimentos.service.BusinessRuleException;
import com.remington.bancoalimentos.service.InventarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventarioServiceImpl implements InventarioService {

    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;

    public InventarioServiceImpl(InventarioRepository inventarioRepository, ProductoRepository productoRepository) {
        this.inventarioRepository = inventarioRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Inventario> listarTodo() {
        return inventarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal consultarStock(Long idProducto) {
        return inventarioRepository.findByProducto_IdProducto(idProducto)
                .map(Inventario::getCantidadDisponible)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    @Transactional
    public void incrementarStock(Long idProducto, BigDecimal cantidad) {
        if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleException("La cantidad a incrementar debe ser mayor a cero.");
        }
        Inventario inv = inventarioRepository.findByProducto_IdProducto(idProducto)
                .orElseGet(() -> {
                    Producto p = productoRepository.findById(idProducto)
                            .orElseThrow(() -> new BusinessRuleException("Producto no encontrado: " + idProducto));
                    return new Inventario(p, BigDecimal.ZERO);
                });
        inv.setCantidadDisponible(inv.getCantidadDisponible().add(cantidad));
        inv.setFechaActualizacion(LocalDateTime.now());
        inventarioRepository.save(inv);
    }

    @Override
    @Transactional
    public void descontarStock(Long idProducto, BigDecimal cantidad) {
        if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleException("La cantidad a descontar debe ser mayor a cero.");
        }
        Inventario inv = inventarioRepository.findByProducto_IdProducto(idProducto)
                .orElseThrow(() -> new BusinessRuleException("No existe registro de inventario para el producto: " + idProducto));

        if (inv.getCantidadDisponible().compareTo(cantidad) < 0) {
            throw new BusinessRuleException("RN-16: Stock insuficiente en inventario (" + inv.getCantidadDisponible() +
                    ") para la cantidad solicitada (" + cantidad + ").");
        }

        inv.setCantidadDisponible(inv.getCantidadDisponible().subtract(cantidad));
        inv.setFechaActualizacion(LocalDateTime.now());
        inventarioRepository.save(inv);
    }
}
