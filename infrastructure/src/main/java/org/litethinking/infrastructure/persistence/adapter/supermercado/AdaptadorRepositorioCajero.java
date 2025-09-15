package org.litethinking.infrastructure.persistence.adapter.supermercado;

import org.litethinking.domain.model.supermercado.Cajero;
import org.litethinking.domain.repository.supermercado.RepositorioCajero;
import org.litethinking.infrastructure.persistence.entity.supermercado.EntidadJpaCajero;
import org.litethinking.infrastructure.persistence.repository.supermercado.RepositorioJpaCajero;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter implementation for RepositorioCajero.
 */
@Repository
public class AdaptadorRepositorioCajero implements RepositorioCajero {

    private final RepositorioJpaCajero repositorioJpaCajero;

    public AdaptadorRepositorioCajero(RepositorioJpaCajero repositorioJpaCajero) {
        this.repositorioJpaCajero = repositorioJpaCajero;
    }

    @Override
    public Cajero save(Cajero cajero) {
        EntidadJpaCajero entidadJpaCajero = mapToEntity(cajero);
        EntidadJpaCajero savedEntity = repositorioJpaCajero.save(entidadJpaCajero);
        return mapToDomain(savedEntity);
    }

    @Override
    public Optional<Cajero> findById(Long id) {
        return repositorioJpaCajero.findById(id).map(this::mapToDomain);
    }

    @Override
    public List<Cajero> findAll() {
        return repositorioJpaCajero.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repositorioJpaCajero.deleteById(id);
    }

    @Override
    public Optional<Cajero> findByCodigo(String codigo) {
        return repositorioJpaCajero.findByCodigo(codigo).map(this::mapToDomain);
    }

    @Override
    public List<Cajero> findByTurno(String turno) {
        return repositorioJpaCajero.findByTurno(turno).stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    private Cajero mapToDomain(EntidadJpaCajero entidadJpaCajero) {
        return Cajero.builder()
                .id(entidadJpaCajero.getId())
                .nombre(entidadJpaCajero.getNombre())
                .codigo(entidadJpaCajero.getCodigo())
                .turno(entidadJpaCajero.getTurno())
                .build();
    }

    private EntidadJpaCajero mapToEntity(Cajero cajero) {
        return EntidadJpaCajero.builder()
                .id(cajero.getId())
                .nombre(cajero.getNombre())
                .codigo(cajero.getCodigo())
                .turno(cajero.getTurno())
                .build();
    }
}