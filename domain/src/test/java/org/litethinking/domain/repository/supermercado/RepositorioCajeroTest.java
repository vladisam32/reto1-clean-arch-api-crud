package org.litethinking.domain.repository.supermercado;

import org.junit.jupiter.api.Test;
import org.litethinking.domain.model.supermercado.Cajero;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Interface defined for testing purposes
 */
interface RepositorioCajero {
    Cajero save(Cajero cajero);
    Optional<Cajero> findById(Long id);
    List<Cajero> findAll();
    void deleteById(Long id);
    Optional<Cajero> findByCodigo(String codigo);
    List<Cajero> findByTurno(String turno);
}

class RepositorioCajeroTest {

    @Test
    void testSave() {
        // Given
        RepositorioCajero repositorioCajero = Mockito.mock(RepositorioCajero.class);
        Cajero cajero = Cajero.builder()
                .id(1L)
                .nombre("Juan Pérez")
                .codigo("CAJ001")
                .turno("Mañana")
                .build();

        when(repositorioCajero.save(cajero)).thenReturn(cajero);

        // When
        Cajero savedCajero = repositorioCajero.save(cajero);

        // Then
        assertEquals(cajero, savedCajero);
        verify(repositorioCajero).save(cajero);
    }

    @Test
    void testFindById() {
        // Given
        RepositorioCajero repositorioCajero = Mockito.mock(RepositorioCajero.class);
        Cajero cajero = Cajero.builder()
                .id(1L)
                .nombre("Juan Pérez")
                .codigo("CAJ001")
                .turno("Mañana")
                .build();

        when(repositorioCajero.findById(1L)).thenReturn(Optional.of(cajero));
        when(repositorioCajero.findById(2L)).thenReturn(Optional.empty());

        // When
        Optional<Cajero> foundCajero = repositorioCajero.findById(1L);
        Optional<Cajero> notFoundCajero = repositorioCajero.findById(2L);

        // Then
        assertTrue(foundCajero.isPresent());
        assertEquals(cajero, foundCajero.get());
        assertFalse(notFoundCajero.isPresent());
        verify(repositorioCajero).findById(1L);
        verify(repositorioCajero).findById(2L);
    }

    @Test
    void testFindAll() {
        // Given
        RepositorioCajero repositorioCajero = Mockito.mock(RepositorioCajero.class);
        Cajero cajero1 = Cajero.builder()
                .id(1L)
                .nombre("Juan Pérez")
                .codigo("CAJ001")
                .turno("Mañana")
                .build();
        Cajero cajero2 = Cajero.builder()
                .id(2L)
                .nombre("María López")
                .codigo("CAJ002")
                .turno("Tarde")
                .build();
        List<Cajero> cajeros = Arrays.asList(cajero1, cajero2);

        when(repositorioCajero.findAll()).thenReturn(cajeros);

        // When
        List<Cajero> foundCajeros = repositorioCajero.findAll();

        // Then
        assertEquals(2, foundCajeros.size());
        assertEquals(cajeros, foundCajeros);
        verify(repositorioCajero).findAll();
    }

    @Test
    void testDeleteById() {
        // Given
        RepositorioCajero repositorioCajero = Mockito.mock(RepositorioCajero.class);
        doNothing().when(repositorioCajero).deleteById(1L);

        // When
        repositorioCajero.deleteById(1L);

        // Then
        verify(repositorioCajero).deleteById(1L);
    }

    @Test
    void testFindByCodigo() {
        // Given
        RepositorioCajero repositorioCajero = Mockito.mock(RepositorioCajero.class);
        Cajero cajero = Cajero.builder()
                .id(1L)
                .nombre("Juan Pérez")
                .codigo("CAJ001")
                .turno("Mañana")
                .build();

        when(repositorioCajero.findByCodigo("CAJ001")).thenReturn(Optional.of(cajero));
        when(repositorioCajero.findByCodigo("CAJ999")).thenReturn(Optional.empty());

        // When
        Optional<Cajero> foundCajero = repositorioCajero.findByCodigo("CAJ001");
        Optional<Cajero> notFoundCajero = repositorioCajero.findByCodigo("CAJ999");

        // Then
        assertTrue(foundCajero.isPresent());
        assertEquals(cajero, foundCajero.get());
        assertFalse(notFoundCajero.isPresent());
        verify(repositorioCajero).findByCodigo("CAJ001");
        verify(repositorioCajero).findByCodigo("CAJ999");
    }

    @Test
    void testFindByTurno() {
        // Given
        RepositorioCajero repositorioCajero = Mockito.mock(RepositorioCajero.class);
        Cajero cajero1 = Cajero.builder()
                .id(1L)
                .nombre("Juan Pérez")
                .codigo("CAJ001")
                .turno("Mañana")
                .build();
        Cajero cajero2 = Cajero.builder()
                .id(3L)
                .nombre("Carlos Rodríguez")
                .codigo("CAJ003")
                .turno("Mañana")
                .build();
        List<Cajero> cajerosMañana = Arrays.asList(cajero1, cajero2);

        when(repositorioCajero.findByTurno("Mañana")).thenReturn(cajerosMañana);
        when(repositorioCajero.findByTurno("Noche")).thenReturn(Arrays.asList());

        // When
        List<Cajero> foundCajerosMañana = repositorioCajero.findByTurno("Mañana");
        List<Cajero> foundCajerosNoche = repositorioCajero.findByTurno("Noche");

        // Then
        assertEquals(2, foundCajerosMañana.size());
        assertEquals(cajerosMañana, foundCajerosMañana);
        assertEquals(0, foundCajerosNoche.size());
        verify(repositorioCajero).findByTurno("Mañana");
        verify(repositorioCajero).findByTurno("Noche");
    }
}
