package org.litethinking.domain.model.supermercado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Entidad Producto que representa un producto en el sistema de inventario, ¡con to'!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String categoria;
    private String codigoBarras;

    // Manual implementation of builder pattern
    public static ProductoBuilder builder() {
        return new ProductoBuilder();
    }

    public static class ProductoBuilder {
        private Long id;
        private String nombre;
        private String descripcion;
        private BigDecimal precio;
        private String categoria;
        private String codigoBarras;

        ProductoBuilder() {
        }

        public ProductoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProductoBuilder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public ProductoBuilder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public ProductoBuilder precio(BigDecimal precio) {
            this.precio = precio;
            return this;
        }

        public ProductoBuilder categoria(String categoria) {
            this.categoria = categoria;
            return this;
        }

        public ProductoBuilder codigoBarras(String codigoBarras) {
            this.codigoBarras = codigoBarras;
            return this;
        }

        public Producto build() {
            return new Producto(id, nombre, descripcion, precio, categoria, codigoBarras);
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    // equals, hashCode, and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(id, producto.id) &&
               Objects.equals(nombre, producto.nombre) &&
               Objects.equals(descripcion, producto.descripcion) &&
               Objects.equals(precio, producto.precio) &&
               Objects.equals(categoria, producto.categoria) &&
               Objects.equals(codigoBarras, producto.codigoBarras);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, descripcion, precio, categoria, codigoBarras);
    }

    @Override
    public String toString() {
        return "Producto(id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + 
               ", precio=" + precio + ", categoria=" + categoria + ", codigoBarras=" + codigoBarras + ")";
    }
}
