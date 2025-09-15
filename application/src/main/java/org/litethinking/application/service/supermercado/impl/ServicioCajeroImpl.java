package org.litethinking.application.service.supermercado.impl;

import org.litethinking.application.mapper.CajeroMapper;
import org.litethinking.application.service.supermercado.ServicioCajero;
import org.litethinking.domain.model.supermercado.Cajero;
import org.litethinking.domain.repository.supermercado.RepositorioCajero;
import org.litethinking.shareddto.supermercado.CajeroDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of ServicioCajero.
 */
@Service
public class ServicioCajeroImpl implements ServicioCajero {

    private final RepositorioCajero repositorioCajero;

    public ServicioCajeroImpl(RepositorioCajero repositorioCajero) {
        this.repositorioCajero = repositorioCajero;
    }

    @Override
    public CajeroDto crearCajero(CajeroDto cajeroDto) {
        Cajero cajero = CajeroMapper.toDomain(cajeroDto);
        Cajero cajeroGuardado = repositorioCajero.save(cajero);
        return CajeroMapper.toDto(cajeroGuardado);
    }

    @Override
    public CajeroDto actualizarCajero(Long id, CajeroDto cajeroDto) {
        Optional<Cajero> cajeroExistente = repositorioCajero.findById(id);
        if (cajeroExistente.isPresent()) {
            Cajero cajero = CajeroMapper.toDomain(cajeroDto);
            cajero.setId(id);
            Cajero cajeroActualizado = repositorioCajero.save(cajero);
            return CajeroMapper.toDto(cajeroActualizado);
        } else {
            throw new IllegalArgumentException("Cajero no encontrado con ID: " + id);
        }
    }

    @Override
    public Optional<CajeroDto> obtenerCajeroPorId(Long id) {
        return repositorioCajero.findById(id)
                .map(CajeroMapper::toDto);
    }

    @Override
    public List<CajeroDto> obtenerTodosLosCajeros() {
        return repositorioCajero.findAll().stream()
                .map(CajeroMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarCajero(Long id) {
        repositorioCajero.deleteById(id);
    }

    @Override
    public Optional<CajeroDto> obtenerCajeroPorCodigo(String codigo) {
        return repositorioCajero.findByCodigo(codigo)
                .map(CajeroMapper::toDto);
    }

    @Override
    public List<CajeroDto> obtenerCajerosPorTurno(String turno) {
        return repositorioCajero.findByTurno(turno).stream()
                .map(CajeroMapper::toDto)
                .collect(Collectors.toList());
    }
}
