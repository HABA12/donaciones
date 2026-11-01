package com.remington.bancoalimentos.service;

import com.remington.bancoalimentos.model.entity.Entrega;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface EntregaService {
    Entrega registrarEntregaConDetalle(Long idBeneficiario, Long idUsuario, LocalDate fecha, String ruta, Long idProducto, BigDecimal cantidad, String observaciones);
    List<Entrega> listarTodas();
}
