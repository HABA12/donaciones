package com.remington.bancoalimentos.model.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad ENTREGA (HU-33 a HU-36 | RF-33 a RF-36 | RN-09)
 */
@Entity
@Table(name = "ENTREGA")
public class Entrega implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrega")
    private Long idEntrega;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_beneficiario", nullable = false)
    private Beneficiario beneficiario;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_usuario_responsable", nullable = false)
    private Usuario usuarioResponsable;

    @Column(name = "fecha_entrega", nullable = false)
    private LocalDate fechaEntrega;

    @Column(name = "ruta_entrega", nullable = false, length = 150)
    private String rutaEntrega;

    @Column(name = "observaciones_evidencia", length = 250)
    private String observacionesEvidencia;

    @Column(name = "estado", nullable = false, length = 15)
    private String estado = "ACTIVO";

    @OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetalleEntrega> detalles = new ArrayList<>();

    public Entrega() {}

    public Long getIdEntrega() { return idEntrega; }
    public void setIdEntrega(Long idEntrega) { this.idEntrega = idEntrega; }

    public Beneficiario getBeneficiario() { return beneficiario; }
    public void setBeneficiario(Beneficiario beneficiario) { this.beneficiario = beneficiario; }

    public Usuario getUsuarioResponsable() { return usuarioResponsable; }
    public void setUsuarioResponsable(Usuario usuarioResponsable) { this.usuarioResponsable = usuarioResponsable; }

    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public String getRutaEntrega() { return rutaEntrega; }
    public void setRutaEntrega(String rutaEntrega) { this.rutaEntrega = rutaEntrega; }

    public String getObservacionesEvidencia() { return observacionesEvidencia; }
    public void setObservacionesEvidencia(String observacionesEvidencia) { this.observacionesEvidencia = observacionesEvidencia; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<DetalleEntrega> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleEntrega> detalles) { this.detalles = detalles; }

    public void addDetalle(DetalleEntrega detalle) {
        detalles.add(detalle);
        detalle.setEntrega(this);
    }
}
