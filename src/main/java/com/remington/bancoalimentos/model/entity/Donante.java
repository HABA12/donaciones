package com.remington.bancoalimentos.model.entity;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Entidad DONANTE (HU-09 a HU-12 | RF-09 a RF-12 | RN-08)
 */
@Entity
@Table(name = "DONANTE")
public class Donante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_donante")
    private Long idDonante;

    @Column(name = "nombre_razon_social", nullable = false, unique = true, length = 120)
    private String nombreRazonSocial;

    @Column(name = "tipo_donante", nullable = false, length = 40)
    private String tipoDonante; // Supermercado, Comercio, Organizacion, Empresa, Particular

    @Column(name = "ubicacion", nullable = false, length = 150)
    private String ubicacion;

    @Column(name = "telefono_contacto", nullable = false, length = 30)
    private String telefonoContacto;

    @Column(name = "correo_contacto", length = 100)
    private String correoContacto;

    @Column(name = "estado", nullable = false, length = 15)
    private String estado = "ACTIVO";

    public Donante() {}

    public Long getIdDonante() { return idDonante; }
    public void setIdDonante(Long idDonante) { this.idDonante = idDonante; }

    public String getNombreRazonSocial() { return nombreRazonSocial; }
    public void setNombreRazonSocial(String nombreRazonSocial) { this.nombreRazonSocial = nombreRazonSocial; }

    public String getTipoDonante() { return tipoDonante; }
    public void setTipoDonante(String tipoDonante) { this.tipoDonante = tipoDonante; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getTelefonoContacto() { return telefonoContacto; }
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }

    public String getCorreoContacto() { return correoContacto; }
    public void setCorreoContacto(String correoContacto) { this.correoContacto = correoContacto; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
