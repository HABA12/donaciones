package com.remington.bancoalimentos.model.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entidad DETALLE_ENTREGA (HU-37 a HU-40 | RF-37 a RF-40 | RN-15, RN-16)
 */
@Entity
@Table(name = "DETALLE_ENTREGA", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_entrega", "id_producto"})
})
public class DetalleEntrega implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_entrega")
    private Long idDetalleEntrega;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_entrega", nullable = false)
    private Entrega entrega;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "estado", nullable = false, length = 15)
    private String estado = "ACTIVO";

    public DetalleEntrega() {}

    public Long getIdDetalleEntrega() { return idDetalleEntrega; }
    public void setIdDetalleEntrega(Long idDetalleEntrega) { this.idDetalleEntrega = idDetalleEntrega; }

    public Entrega getEntrega() { return entrega; }
    public void setEntrega(Entrega entrega) { this.entrega = entrega; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
