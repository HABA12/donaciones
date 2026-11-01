package com.remington.bancoalimentos.model.repository;

import com.remington.bancoalimentos.model.entity.Donacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonacionRepository extends JpaRepository<Donacion, Long> {
    long countByDonante_IdDonanteAndEstado(Long idDonante, String estado);
}
