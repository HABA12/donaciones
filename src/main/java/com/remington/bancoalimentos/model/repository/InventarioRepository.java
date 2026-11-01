package com.remington.bancoalimentos.model.repository;

import com.remington.bancoalimentos.model.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findByProducto_IdProducto(Long idProducto);
}
