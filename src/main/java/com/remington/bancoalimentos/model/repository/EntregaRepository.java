package com.remington.bancoalimentos.model.repository;

import com.remington.bancoalimentos.model.entity.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    long countByBeneficiario_IdBeneficiarioAndEstado(Long idBeneficiario, String estado);
}
