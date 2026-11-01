package com.remington.bancoalimentos.model.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entidad DETALLE_DONACION (HU-25 a HU-28 | RF-25 a RF-28 | RN-05, RN-06, RN-14)
 */
@Entity
@Table(name = "DETALLE_DONACION", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_donacion", "id_producto"})
})
public class DetalleDonacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_donacion")
    private Long idDetalleDonacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_donacion", nullable = false)
    private Donacion donacion;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "estado_alimento", nullable = false, length = 30)
    private String estadoAlimento = "APTO"; // APTO, RECHAZADO

    @Column(name = "estado", nullable = false, length = 15)
    private String estado = "ACTIVO";

    public DetalleDonacion() {}

    public Long getIdDetalleDonacion() { return idDetalleDonacion; }
    public void setIdDetalleDonacion(Long idDetalleDonacion) { this.idDetalleDonacion = idDetalleDonacion; }

    public Donacion getDonacion() { return donacion; }
    public void setDonacion(Donacion donacion) { this.donacion = donacion; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }

    public String getEstadoAlimento() { return estadoAlimento; }
    public void setEstadoAlimento(String estadoAlimento) { this.estadoAlimento = estadoAlimento; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
