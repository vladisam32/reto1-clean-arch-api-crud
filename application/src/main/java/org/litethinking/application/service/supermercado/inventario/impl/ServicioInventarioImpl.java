package org.litethinking.application.service.supermercado.inventario.impl;

import org.litethinking.application.service.supermercado.inventario.ServicioInventario;
import org.litethinking.domain.model.supermercado.Producto;
import org.litethinking.domain.model.supermercado.inventario.Inventario;
import org.litethinking.domain.repository.supermercado.inventario.RepositorioInventario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of ServicioInventario.
 */
@Service
public class ServicioInventarioImpl implements ServicioInventario {

    private final RepositorioInventario repositorioInventario;

    public ServicioInventarioImpl(RepositorioInventario repositorioInventario) {
        this.repositorioInventario = repositorioInventario;
    }

    @Override
    public Inventario crearInventario(Inventario inventario) {
        return repositorioInventario.save(inventario);
    }

    @Override
    public Inventario actualizarInventario(Long id, Inventario inventario) {
        Optional<Inventario> inventarioExistente = repositorioInventario.findById(id);
        if (inventarioExistente.isPresent()) {
            inventario.setId(id);
            return repositorioInventario.save(inventario);
        } else {
            throw new IllegalArgumentException("Inventario no encontrado con ID: " + id);
        }
    }

    @Override
    public Optional<Inventario> obtenerInventarioPorId(Long id) {
        return repositorioInventario.findById(id);
    }

    @Override
    public List<Inventario> obtenerTodoElInventario() {
        return repositorioInventario.findAll();
    }

    @Override
    public void eliminarInventario(Long id) {
        repositorioInventario.deleteById(id);
    }

    @Override
    public Optional<Inventario> obtenerInventarioPorProducto(Producto producto) {
        return repositorioInventario.findByProducto(producto);
    }

    @Override
    public List<Inventario> obtenerInventarioConBajoStock() {
        return repositorioInventario.findBajoStock();
    }

    @Override
    public List<Inventario> obtenerInventarioPorUbicacion(String ubicacion) {
        return repositorioInventario.findByUbicacion(ubicacion);
    }

    @Override
    public Inventario actualizarCantidadInventario(Long id, Integer cantidad) {
        Optional<Inventario> inventarioExistente = repositorioInventario.findById(id);
        if (inventarioExistente.isPresent()) {
            Inventario inventario = inventarioExistente.get();
            inventario.setCantidad(cantidad);
            return repositorioInventario.save(inventario);
        } else {
            throw new IllegalArgumentException("Inventario no encontrado con ID: " + id);
        }
    }
}
