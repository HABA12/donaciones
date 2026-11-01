package com.remington.bancoalimentos.model.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad DONACION (HU-21 a HU-24 | RF-21 a RF-24 | RN-04, RN-11, RN-13)
 */
@Entity
@Table(name = "DONACION")
public class Donacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_donacion")
    private Long idDonacion;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_donante", nullable = false)
    private Donante donante;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_usuario_registra", nullable = false)
    private Usuario usuarioRegistra;

    @Column(name = "fecha_donacion", nullable = false)
    private LocalDate fechaDonacion;

    @Column(name = "observaciones", length = 250)
    private String observaciones;

    @Column(name = "estado", nullable = false, length = 15)
    private String estado = "ACTIVO";

    @OneToMany(mappedBy = "donacion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetalleDonacion> detalles = new ArrayList<>();

    public Donacion() {}

    public Long getIdDonacion() { return idDonacion; }
    public void setIdDonacion(Long idDonacion) { this.idDonacion = idDonacion; }

    public Donante getDonante() { return donante; }
    public void setDonante(Donante donante) { this.donante = donante; }

    public Usuario getUsuarioRegistra() { return usuarioRegistra; }
    public void setUsuarioRegistra(Usuario usuarioRegistra) { this.usuarioRegistra = usuarioRegistra; }

    public LocalDate getFechaDonacion() { return fechaDonacion; }
    public void setFechaDonacion(LocalDate fechaDonacion) { this.fechaDonacion = fechaDonacion; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<DetalleDonacion> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleDonacion> detalles) { this.detalles = detalles; }

    public void addDetalle(DetalleDonacion detalle) {
        detalles.add(detalle);
        detalle.setDonacion(this);
    }
}
