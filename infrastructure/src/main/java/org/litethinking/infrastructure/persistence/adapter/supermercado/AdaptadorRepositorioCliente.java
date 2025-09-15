package org.litethinking.infrastructure.persistence.adapter.supermercado;

import org.litethinking.domain.model.supermercado.Cliente;
import org.litethinking.domain.repository.supermercado.RepositorioCliente;
import org.litethinking.infrastructure.persistence.entity.supermercado.EntidadJpaCliente;
import org.litethinking.infrastructure.persistence.repository.supermercado.RepositorioJpaCliente;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter implementation for RepositorioCliente.
 */
@Repository
public class AdaptadorRepositorioCliente implements RepositorioCliente {

    private final RepositorioJpaCliente repositorioJpaCliente;

    public AdaptadorRepositorioCliente(RepositorioJpaCliente repositorioJpaCliente) {
        this.repositorioJpaCliente = repositorioJpaCliente;
    }

    @Override
    public Cliente save(Cliente cliente) {
        EntidadJpaCliente entidadJpaCliente = mapToEntity(cliente);
        EntidadJpaCliente savedEntity = repositorioJpaCliente.save(entidadJpaCliente);
        return mapToDomain(savedEntity);
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return repositorioJpaCliente.findById(id).map(this::mapToDomain);
    }

    @Override
    public List<Cliente> findAll() {
        return repositorioJpaCliente.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repositorioJpaCliente.deleteById(id);
    }

    @Override
    public Optional<Cliente> findByEmail(String email) {
        return repositorioJpaCliente.findByEmail(email).map(this::mapToDomain);
    }

    @Override
    public List<Cliente> findByNombreContaining(String nombre) {
        return repositorioJpaCliente.findByNombreContaining(nombre).stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    private Cliente mapToDomain(EntidadJpaCliente entidadJpaCliente) {
        return Cliente.builder()
                .id(entidadJpaCliente.getId())
                .nombre(entidadJpaCliente.getNombre())
                .email(entidadJpaCliente.getEmail())
                .telefono(entidadJpaCliente.getTelefono())
                .direccion(entidadJpaCliente.getDireccion())
                .build();
    }

    private EntidadJpaCliente mapToEntity(Cliente cliente) {
        return EntidadJpaCliente.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .email(cliente.getEmail())
                .telefono(cliente.getTelefono())
                .direccion(cliente.getDireccion())
                .build();
    }
}