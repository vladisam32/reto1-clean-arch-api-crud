package org.litethinking.domain.model.supermercado;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void testClienteCreation() {
        // Given
        Long id = 1L;
        String nombre = "Ana García";
        String email = "ana.garcia@example.com";
        String telefono = "555-123-4567";
        String direccion = "Calle Principal 123";
        
        // When
        Cliente cliente = Cliente.builder()
                .id(id)
                .nombre(nombre)
                .email(email)
                .telefono(telefono)
                .direccion(direccion)
                .build();
        
        // Then
        assertEquals(id, cliente.getId());
        assertEquals(nombre, cliente.getNombre());
        assertEquals(email, cliente.getEmail());
        assertEquals(telefono, cliente.getTelefono());
        assertEquals(direccion, cliente.getDireccion());
    }
    
    @Test
    void testClienteEquality() {
        // Given
        Cliente cliente1 = Cliente.builder()
                .id(1L)
                .nombre("Ana García")
                .email("ana.garcia@example.com")
                .telefono("555-123-4567")
                .direccion("Calle Principal 123")
                .build();
        
        Cliente cliente2 = Cliente.builder()
                .id(1L)
                .nombre("Ana García")
                .email("ana.garcia@example.com")
                .telefono("555-123-4567")
                .direccion("Calle Principal 123")
                .build();
        
        // Then
        assertEquals(cliente1, cliente2);
        assertEquals(cliente1.hashCode(), cliente2.hashCode());
    }
    
    @Test
    void testClienteToString() {
        // Given
        Cliente cliente = Cliente.builder()
                .id(1L)
                .nombre("Ana García")
                .email("ana.garcia@example.com")
                .telefono("555-123-4567")
                .direccion("Calle Principal 123")
                .build();
        
        // When
        String clienteString = cliente.toString();
        
        // Then
        assertTrue(clienteString.contains("id=1"));
        assertTrue(clienteString.contains("nombre=Ana García"));
        assertTrue(clienteString.contains("email=ana.garcia@example.com"));
        assertTrue(clienteString.contains("telefono=555-123-4567"));
        assertTrue(clienteString.contains("direccion=Calle Principal 123"));
    }
}