package com.remington.bancoalimentos.model.entity;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Entidad BENEFICIARIO (HU-13 a HU-16 | RF-13 a RF-16 | RN-09, RN-12)
 * Regla RN-12: Exclusivo estratos 1, 2 y 3.
 */
@Entity
@Table(name = "BENEFICIARIO")
public class Beneficiario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_beneficiario")
    private Long idBeneficiario;

    @Column(name = "nombre_beneficiario", nullable = false, unique = true, length = 120)
    private String nombreBeneficiario;

    @Column(name = "tipo_beneficiario", nullable = false, length = 40)
    private String tipoBeneficiario; // Fundacion, Comedor Comunitario, Familia Vulnerable

    @Column(name = "estrato_socioeconomico", nullable = false)
    private Integer estratoSocioeconomico; // 1, 2, 3

    @Column(name = "ubicacion_comuna", nullable = false, length = 150)
    private String ubicacionComuna;

    @Column(name = "telefono_contacto", nullable = false, length = 30)
    private String telefonoContacto;

    @Column(name = "tipo_ayuda_solicitada", nullable = false, length = 100)
    private String tipoAyudaSolicitada;

    @Column(name = "estado", nullable = false, length = 15)
    private String estado = "ACTIVO";

    public Beneficiario() {}

    public Long getIdBeneficiario() { return idBeneficiario; }
    public void setIdBeneficiario(Long idBeneficiario) { this.idBeneficiario = idBeneficiario; }

    public String getNombreBeneficiario() { return nombreBeneficiario; }
    public void setNombreBeneficiario(String nombreBeneficiario) { this.nombreBeneficiario = nombreBeneficiario; }

    public String getTipoBeneficiario() { return tipoBeneficiario; }
    public void setTipoBeneficiario(String tipoBeneficiario) { this.tipoBeneficiario = tipoBeneficiario; }

    public Integer getEstratoSocioeconomico() { return estratoSocioeconomico; }
    public void setEstratoSocioeconomico(Integer estratoSocioeconomico) { this.estratoSocioeconomico = estratoSocioeconomico; }

    public String getUbicacionComuna() { return ubicacionComuna; }
    public void setUbicacionComuna(String ubicacionComuna) { this.ubicacionComuna = ubicacionComuna; }

    public String getTelefonoContacto() { return telefonoContacto; }
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }

    public String getTipoAyudaSolicitada() { return tipoAyudaSolicitada; }
    public void setTipoAyudaSolicitada(String tipoAyudaSolicitada) { this.tipoAyudaSolicitada = tipoAyudaSolicitada; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
