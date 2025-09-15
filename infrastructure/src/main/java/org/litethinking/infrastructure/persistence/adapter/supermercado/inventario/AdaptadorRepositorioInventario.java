package org.litethinking.infrastructure.persistence.adapter.supermercado.inventario;

import org.litethinking.domain.model.supermercado.Producto;
import org.litethinking.domain.model.supermercado.inventario.Inventario;
import org.litethinking.domain.repository.supermercado.inventario.RepositorioInventario;
import org.litethinking.infrastructure.persistence.entity.supermercado.EntidadJpaProducto;
import org.litethinking.infrastructure.persistence.entity.supermercado.inventario.EntidadJpaInventario;
import org.litethinking.infrastructure.persistence.repository.supermercado.inventario.RepositorioJpaInventario;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter implementation for RepositorioInventario.
 */
@Repository
public class AdaptadorRepositorioInventario implements RepositorioInventario {

    private final RepositorioJpaInventario repositorioJpaInventario;

    public AdaptadorRepositorioInventario(RepositorioJpaInventario repositorioJpaInventario) {
        this.repositorioJpaInventario = repositorioJpaInventario;
    }

    @Override
    public Inventario save(Inventario inventario) {
        EntidadJpaInventario entidadJpaInventario = mapToEntity(inventario);
        EntidadJpaInventario savedEntity = repositorioJpaInventario.save(entidadJpaInventario);
        return mapToDomain(savedEntity);
    }

    @Override
    public Optional<Inventario> findById(Long id) {
        return repositorioJpaInventario.findById(id).map(this::mapToDomain);
    }

    @Override
    public List<Inventario> findAll() {
        return repositorioJpaInventario.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repositorioJpaInventario.deleteById(id);
    }

    @Override
    public Optional<Inventario> findByProducto(Producto producto) {
        EntidadJpaProducto entidadJpaProducto = mapProductoToEntity(producto);
        return repositorioJpaInventario.findByProducto(entidadJpaProducto).map(this::mapToDomain);
    }

    @Override
    public List<Inventario> findBajoStock() {
        return repositorioJpaInventario.findBajoStock().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Inventario> findByUbicacion(String ubicacion) {
        return repositorioJpaInventario.findByUbicacion(ubicacion).stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    private Inventario mapToDomain(EntidadJpaInventario entidadJpaInventario) {
        return Inventario.builder()
                .id(entidadJpaInventario.getId())
                .producto(mapProductoToDomain(entidadJpaInventario.getProducto()))
                .cantidad(entidadJpaInventario.getCantidad())
                .stockMinimo(entidadJpaInventario.getStockMinimo())
                .stockMaximo(entidadJpaInventario.getStockMaximo())
                .fechaUltimaReposicion(entidadJpaInventario.getFechaUltimaReposicion())
                .ubicacion(entidadJpaInventario.getUbicacion())
                .build();
    }

    private EntidadJpaInventario mapToEntity(Inventario inventario) {
        return EntidadJpaInventario.builder()
                .id(inventario.getId())
                .producto(mapProductoToEntity(inventario.getProducto()))
                .cantidad(inventario.getCantidad())
                .stockMinimo(inventario.getStockMinimo())
                .stockMaximo(inventario.getStockMaximo())
                .fechaUltimaReposicion(inventario.getFechaUltimaReposicion())
                .ubicacion(inventario.getUbicacion())
                .build();
    }

    private Producto mapProductoToDomain(EntidadJpaProducto entidadJpaProducto) {
        return Producto.builder()
                .id(entidadJpaProducto.getId())
                .nombre(entidadJpaProducto.getNombre())
                .descripcion(entidadJpaProducto.getDescripcion())
                .precio(entidadJpaProducto.getPrecio())
                .categoria(entidadJpaProducto.getCategoria())
                .codigoBarras(entidadJpaProducto.getCodigoBarras())
                .build();
    }

    private EntidadJpaProducto mapProductoToEntity(Producto producto) {
        return EntidadJpaProducto.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .categoria(producto.getCategoria())
                .codigoBarras(producto.getCodigoBarras())
                .build();
    }
}