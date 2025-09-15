package org.litethinking.application.service.supermercado.venta.impl;

import org.litethinking.application.mapper.VentaMapper;
import org.litethinking.application.service.supermercado.venta.ServicioVenta;
import org.litethinking.domain.model.supermercado.venta.Venta;
import org.litethinking.domain.repository.supermercado.venta.RepositorioVenta;
import org.litethinking.shareddto.supermercado.venta.VentaDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the ServicioVenta interface.
 */
@Service
public class ServicioVentaImpl implements ServicioVenta {

    private final RepositorioVenta repositorioVenta;

    public ServicioVentaImpl(RepositorioVenta repositorioVenta) {
        this.repositorioVenta = repositorioVenta;
    }

    @Override
    public VentaDto crearVenta(VentaDto ventaDto) {
        Venta venta = VentaMapper.toDomain(ventaDto);
        Venta ventaCreada = repositorioVenta.save(venta);
        return VentaMapper.toDto(ventaCreada);
    }

    @Override
    public VentaDto actualizarVenta(Long id, VentaDto ventaDto) {
        Venta venta = VentaMapper.toDomain(ventaDto);
        venta.setId(id);
        Venta ventaActualizada = repositorioVenta.save(venta);
        return VentaMapper.toDto(ventaActualizada);
    }

    @Override
    public Optional<VentaDto> obtenerVentaPorId(Long id) {
        return repositorioVenta.findById(id)
                .map(VentaMapper::toDto);
    }

    @Override
    public List<VentaDto> obtenerTodasLasVentas() {
        return repositorioVenta.findAll()
                .stream()
                .map(VentaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarVenta(Long id) {
        repositorioVenta.deleteById(id);
    }

    @Override
    public List<VentaDto> obtenerVentasPorNombreCliente(String nombreCliente) {
        return repositorioVenta.findByNombreCliente(nombreCliente)
                .stream()
                .map(VentaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VentaDto> obtenerVentasEntreFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return repositorioVenta.findByFechaVentaBetween(fechaInicio, fechaFin)
                .stream()
                .map(VentaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VentaDto> obtenerVentasPorMontoTotalMayorQue(BigDecimal montoMinimo) {
        return repositorioVenta.findByMontoTotalGreaterThan(montoMinimo)
                .stream()
                .map(VentaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VentaDto> obtenerVentasPorMetodoPago(String metodoPago) {
        return repositorioVenta.findByMetodoPago(metodoPago)
                .stream()
                .map(VentaMapper::toDto)
                .collect(Collectors.toList());
    }
}
