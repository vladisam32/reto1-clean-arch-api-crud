package org.litethinking.domain.repository.supermercado;

import org.junit.jupiter.api.Test;
import org.litethinking.domain.model.supermercado.Cliente;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Interface defined for testing purposes
 */
interface RepositorioCliente {
    Cliente save(Cliente cliente);
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();
    void deleteById(Long id);
    Optional<Cliente> findByEmail(String email);
    List<Cliente> findByNombreContaining(String nombre);
}

class RepositorioClienteTest {

    @Test
    void testSave() {
        // Given
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Cliente cliente = Cliente.builder()
                .id(1L)
                .nombre("Ana García")
                .email("ana.garcia@example.com")
                .telefono("555-123-4567")
                .direccion("Calle Principal 123")
                .build();

        when(repositorioCliente.save(cliente)).thenReturn(cliente);

        // When
        Cliente savedCliente = repositorioCliente.save(cliente);

        // Then
        assertEquals(cliente, savedCliente);
        verify(repositorioCliente).save(cliente);
    }

    @Test
    void testFindById() {
        // Given
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Cliente cliente = Cliente.builder()
                .id(1L)
                .nombre("Ana García")
                .email("ana.garcia@example.com")
                .telefono("555-123-4567")
                .direccion("Calle Principal 123")
                .build();

        when(repositorioCliente.findById(1L)).thenReturn(Optional.of(cliente));
        when(repositorioCliente.findById(2L)).thenReturn(Optional.empty());

        // When
        Optional<Cliente> foundCliente = repositorioCliente.findById(1L);
        Optional<Cliente> notFoundCliente = repositorioCliente.findById(2L);

        // Then
        assertTrue(foundCliente.isPresent());
        assertEquals(cliente, foundCliente.get());
        assertFalse(notFoundCliente.isPresent());
        verify(repositorioCliente).findById(1L);
        verify(repositorioCliente).findById(2L);
    }

    @Test
    void testFindAll() {
        // Given
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Cliente cliente1 = Cliente.builder()
                .id(1L)
                .nombre("Ana García")
                .email("ana.garcia@example.com")
                .telefono("555-123-4567")
                .direccion("Calle Principal 123")
                .build();
        Cliente cliente2 = Cliente.builder()
                .id(2L)
                .nombre("Pedro Martínez")
                .email("pedro.martinez@example.com")
                .telefono("555-987-6543")
                .direccion("Avenida Central 456")
                .build();
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        when(repositorioCliente.findAll()).thenReturn(clientes);

        // When
        List<Cliente> foundClientes = repositorioCliente.findAll();

        // Then
        assertEquals(2, foundClientes.size());
        assertEquals(clientes, foundClientes);
        verify(repositorioCliente).findAll();
    }

    @Test
    void testDeleteById() {
        // Given
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        doNothing().when(repositorioCliente).deleteById(1L);

        // When
        repositorioCliente.deleteById(1L);

        // Then
        verify(repositorioCliente).deleteById(1L);
    }

    @Test
    void testFindByEmail() {
        // Given
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Cliente cliente = Cliente.builder()
                .id(1L)
                .nombre("Ana García")
                .email("ana.garcia@example.com")
                .telefono("555-123-4567")
                .direccion("Calle Principal 123")
                .build();

        when(repositorioCliente.findByEmail("ana.garcia@example.com")).thenReturn(Optional.of(cliente));
        when(repositorioCliente.findByEmail("noexiste@example.com")).thenReturn(Optional.empty());

        // When
        Optional<Cliente> foundCliente = repositorioCliente.findByEmail("ana.garcia@example.com");
        Optional<Cliente> notFoundCliente = repositorioCliente.findByEmail("noexiste@example.com");

        // Then
        assertTrue(foundCliente.isPresent());
        assertEquals(cliente, foundCliente.get());
        assertFalse(notFoundCliente.isPresent());
        verify(repositorioCliente).findByEmail("ana.garcia@example.com");
        verify(repositorioCliente).findByEmail("noexiste@example.com");
    }

    @Test
    void testFindByNombreContaining() {
        // Given
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Cliente cliente1 = Cliente.builder()
                .id(1L)
                .nombre("Ana García")
                .email("ana.garcia@example.com")
                .telefono("555-123-4567")
                .direccion("Calle Principal 123")
                .build();
        Cliente cliente2 = Cliente.builder()
                .id(3L)
                .nombre("Ana Rodríguez")
                .email("ana.rodriguez@example.com")
                .telefono("555-555-5555")
                .direccion("Plaza Mayor 789")
                .build();
        List<Cliente> clientesAna = Arrays.asList(cliente1, cliente2);

        when(repositorioCliente.findByNombreContaining("Ana")).thenReturn(clientesAna);
        when(repositorioCliente.findByNombreContaining("Xyz")).thenReturn(Arrays.asList());

        // When
        List<Cliente> foundClientesAna = repositorioCliente.findByNombreContaining("Ana");
        List<Cliente> foundClientesXyz = repositorioCliente.findByNombreContaining("Xyz");

        // Then
        assertEquals(2, foundClientesAna.size());
        assertEquals(clientesAna, foundClientesAna);
        assertEquals(0, foundClientesXyz.size());
        verify(repositorioCliente).findByNombreContaining("Ana");
        verify(repositorioCliente).findByNombreContaining("Xyz");
    }
}
