package com.remington.bancoalimentos.service;

import com.remington.bancoalimentos.model.entity.Inventario;
import java.math.BigDecimal;
import java.util.List;

public interface InventarioService {
    List<Inventario> listarTodo();
    BigDecimal consultarStock(Long idProducto);
    void incrementarStock(Long idProducto, BigDecimal cantidad);
    void descontarStock(Long idProducto, BigDecimal cantidad);
}
