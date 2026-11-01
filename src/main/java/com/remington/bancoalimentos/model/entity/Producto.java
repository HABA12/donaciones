package com.remington.bancoalimentos.model.entity;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Entidad PRODUCTO (HU-17 a HU-20 | RF-17 a RF-20 | RN-10, RN-14)
 */
@Entity
@Table(name = "PRODUCTO")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "nombre_producto", nullable = false, unique = true, length = 100)
    private String nombreProducto;

    @Column(name = "categoria", nullable = false, length = 50)
    private String categoria; // Granos, Aceites, Harinas, Enlatados, etc.

    @Column(name = "unidad_medida", nullable = false, length = 20)
    private String unidadMedida; // Kg, Litros, Unidades, etc.

    @Column(name = "estado", nullable = false, length = 15)
    private String estado = "ACTIVO";

    public Producto() {}

    public Producto(String nombreProducto, String categoria, String unidadMedida) {
        this.nombreProducto = nombreProducto;
        this.categoria = categoria;
        this.unidadMedida = unidadMedida;
        this.estado = "ACTIVO";
    }

    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
