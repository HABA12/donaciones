package com.remington.bancoalimentos.service;

import com.remington.bancoalimentos.model.entity.Donacion;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface DonacionService {
    Donacion registrarDonacionConDetalle(Long idDonante, Long idUsuario, LocalDate fecha, Long idProducto, BigDecimal cantidad, String estadoAlimento, String observaciones);
    List<Donacion> listarTodas();
    void inactivarDonacion(Long idDonacion);
}
