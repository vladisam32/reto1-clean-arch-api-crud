package org.litethinking.application.service.supermercado.impl;

import org.litethinking.application.mapper.ProductoMapper;
import org.litethinking.application.service.supermercado.ServicioProducto;
import org.litethinking.domain.model.supermercado.Producto;
import org.litethinking.domain.repository.supermercado.RepositorioProducto;
import org.litethinking.shareddto.supermercado.ProductoDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of ServicioProducto.
 */
@Service
public class ServicioProductoImpl implements ServicioProducto {

    private final RepositorioProducto repositorioProducto;

    public ServicioProductoImpl(RepositorioProducto repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }

    @Override
    public ProductoDto crearProducto(ProductoDto productoDto) {
        Producto producto = ProductoMapper.toDomain(productoDto);
        Producto productoGuardado = repositorioProducto.save(producto);
        return ProductoMapper.toDto(productoGuardado);
    }

    @Override
    public ProductoDto actualizarProducto(Long id, ProductoDto productoDto) {
        Optional<Producto> productoExistente = repositorioProducto.findById(id);
        if (productoExistente.isPresent()) {
            Producto producto = ProductoMapper.toDomain(productoDto);
            producto.setId(id);
            Producto productoActualizado = repositorioProducto.save(producto);
            return ProductoMapper.toDto(productoActualizado);
        } else {
            throw new IllegalArgumentException("Producto no encontrado con ID: " + id);
        }
    }

    @Override
    public Optional<ProductoDto> obtenerProductoPorId(Long id) {
        return repositorioProducto.findById(id)
                .map(ProductoMapper::toDto);
    }

    @Override
    public List<ProductoDto> obtenerTodosLosProductos() {
        return repositorioProducto.findAll().stream()
                .map(ProductoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarProducto(Long id) {
        repositorioProducto.deleteById(id);
    }

    @Override
    public List<ProductoDto> obtenerProductosPorCategoria(String categoria) {
        return repositorioProducto.findByCategoria(categoria).stream()
                .map(ProductoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoDto> obtenerProductosPorRangoDePrecio(BigDecimal precioMinimo, BigDecimal precioMaximo) {
        return repositorioProducto.findByPrecioBetween(precioMinimo, precioMaximo).stream()
                .map(ProductoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductoDto> obtenerProductoPorCodigoBarras(String codigoBarras) {
        return repositorioProducto.findByCodigoBarras(codigoBarras)
                .map(ProductoMapper::toDto);
    }
}
