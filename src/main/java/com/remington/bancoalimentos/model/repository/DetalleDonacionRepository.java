package com.remington.bancoalimentos.model.repository;

import com.remington.bancoalimentos.model.entity.DetalleDonacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleDonacionRepository extends JpaRepository<DetalleDonacion, Long> {
    boolean existsByDonacion_IdDonacionAndProducto_IdProducto(Long idDonacion, Long idProducto);
}
