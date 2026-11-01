package com.remington.bancoalimentos.model.repository;

import com.remington.bancoalimentos.model.entity.DetalleEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleEntregaRepository extends JpaRepository<DetalleEntrega, Long> {
    boolean existsByEntrega_IdEntregaAndProducto_IdProducto(Long idEntrega, Long idProducto);
}
