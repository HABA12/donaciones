package com.remington.bancoalimentos.model.repository;

import com.remington.bancoalimentos.model.entity.Donante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DonanteRepository extends JpaRepository<Donante, Long> {
    Optional<Donante> findByNombreRazonSocial(String nombreRazonSocial);
    boolean existsByNombreRazonSocial(String nombreRazonSocial);
}
