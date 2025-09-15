package org.litethinking.application.service.supermercado.impl;

import org.litethinking.application.mapper.ClienteMapper;
import org.litethinking.application.service.supermercado.ServicioCliente;
import org.litethinking.domain.model.supermercado.Cliente;
import org.litethinking.domain.repository.supermercado.RepositorioCliente;
import org.litethinking.shareddto.supermercado.ClienteDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación de ServicioCliente, ¡pa' que funcione to'!
 */
@Service
public class ServicioClienteImpl implements ServicioCliente {

    private final RepositorioCliente repositorioCliente;

    public ServicioClienteImpl(RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

    @Override
    public ClienteDto crearCliente(ClienteDto clienteDto) {
        Cliente cliente = ClienteMapper.toDomain(clienteDto);
        Cliente clienteGuardado = repositorioCliente.save(cliente);
        return ClienteMapper.toDto(clienteGuardado);
    }

    @Override
    public ClienteDto actualizarCliente(Long id, ClienteDto clienteDto) {
        Optional<Cliente> clienteExistente = repositorioCliente.findById(id);
        if (clienteExistente.isPresent()) {
            Cliente cliente = ClienteMapper.toDomain(clienteDto);
            cliente.setId(id);
            Cliente clienteActualizado = repositorioCliente.save(cliente);
            return ClienteMapper.toDto(clienteActualizado);
        } else {
            throw new IllegalArgumentException("Cliente no encontrado con ID: " + id);
        }
    }

    @Override
    public Optional<ClienteDto> obtenerClientePorId(Long id) {
        return repositorioCliente.findById(id)
                .map(ClienteMapper::toDto);
    }

    @Override
    public List<ClienteDto> obtenerTodosLosClientes() {
        return repositorioCliente.findAll().stream()
                .map(ClienteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarCliente(Long id) {
        repositorioCliente.deleteById(id);
    }

    @Override
    public Optional<ClienteDto> obtenerClientePorEmail(String email) {
        return repositorioCliente.findByEmail(email)
                .map(ClienteMapper::toDto);
    }

    @Override
    public List<ClienteDto> obtenerClientesPorNombreContaining(String nombre) {
        return repositorioCliente.findByNombreContaining(nombre).stream()
                .map(ClienteMapper::toDto)
                .collect(Collectors.toList());
    }
}
