package org.litethinking.domain.model.supermercado;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CajeroTest {

    @Test
    void testCajeroCreation() {
        // Given
        Long id = 1L;
        String nombre = "Juan Pérez";
        String codigo = "CAJ001";
        String turno = "Mañana";
        
        // When
        Cajero cajero = Cajero.builder()
                .id(id)
                .nombre(nombre)
                .codigo(codigo)
                .turno(turno)
                .build();
        
        // Then
        assertEquals(id, cajero.getId());
        assertEquals(nombre, cajero.getNombre());
        assertEquals(codigo, cajero.getCodigo());
        assertEquals(turno, cajero.getTurno());
    }
    
    @Test
    void testCajeroEquality() {
        // Given
        Cajero cajero1 = Cajero.builder()
                .id(1L)
                .nombre("Juan Pérez")
                .codigo("CAJ001")
                .turno("Mañana")
                .build();
        
        Cajero cajero2 = Cajero.builder()
                .id(1L)
                .nombre("Juan Pérez")
                .codigo("CAJ001")
                .turno("Mañana")
                .build();
        
        // Then
        assertEquals(cajero1, cajero2);
        assertEquals(cajero1.hashCode(), cajero2.hashCode());
    }
    
    @Test
    void testCajeroToString() {
        // Given
        Cajero cajero = Cajero.builder()
                .id(1L)
                .nombre("Juan Pérez")
                .codigo("CAJ001")
                .turno("Mañana")
                .build();
        
        // When
        String cajeroString = cajero.toString();
        
        // Then
        assertTrue(cajeroString.contains("id=1"));
        assertTrue(cajeroString.contains("nombre=Juan Pérez"));
        assertTrue(cajeroString.contains("codigo=CAJ001"));
        assertTrue(cajeroString.contains("turno=Mañana"));
    }
}