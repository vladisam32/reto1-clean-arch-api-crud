package org.litethinking.restapi.controller.supermercado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.litethinking.domain.model.supermercado.Cajero;
import org.litethinking.shareddto.supermercado.CajeroDto;
import org.litethinking.application.service.supermercado.ServicioCajero;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ControladorCajeroIntegrationTest {

    private MockMvc mockMvc;

    @Mock
    private ServicioCajero servicioCajero;

    @InjectMocks
    private ControladorCajero controladorCajero;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controladorCajero).build();
    }

    @Test
    public void testObtenerTodosLosCajeros() throws Exception {
        // Given
        CajeroDto cajero1 = CajeroDto.builder()
                .id(1L)
                .nombre("Juan Pérez")
                .codigo("CAJ001")
                .turno("Mañana")
                .build();
        CajeroDto cajero2 = CajeroDto.builder()
                .id(2L)
                .nombre("María López")
                .codigo("CAJ002")
                .turno("Tarde")
                .build();
        List<CajeroDto> cajeros = Arrays.asList(cajero1, cajero2);

        when(servicioCajero.obtenerTodosLosCajeros()).thenReturn(cajeros);

        // When & Then
        mockMvc.perform(get("/api/cajeros"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Juan Pérez")))
                .andExpect(jsonPath("$[0].codigo", is("CAJ001")))
                .andExpect(jsonPath("$[0].turno", is("Mañana")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nombre", is("María López")))
                .andExpect(jsonPath("$[1].codigo", is("CAJ002")))
                .andExpect(jsonPath("$[1].turno", is("Tarde")));
    }

    @Test
    public void testObtenerCajeroPorId() throws Exception {
        // Given
        CajeroDto cajero = CajeroDto.builder()
                .id(1L)
                .nombre("Juan Pérez")
                .codigo("CAJ001")
                .turno("Mañana")
                .build();

        when(servicioCajero.obtenerCajeroPorId(1L)).thenReturn(Optional.of(cajero));
        when(servicioCajero.obtenerCajeroPorId(2L)).thenReturn(Optional.empty());

        // When & Then - Existing cajero
        mockMvc.perform(get("/api/cajeros/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Juan Pérez")))
                .andExpect(jsonPath("$.codigo", is("CAJ001")))
                .andExpect(jsonPath("$.turno", is("Mañana")));

        // When & Then - Non-existing cajero
        mockMvc.perform(get("/api/cajeros/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCrearCajero() throws Exception {
        // Given
        CajeroDto cajero = CajeroDto.builder()
                .id(1L)
                .nombre("Juan Pérez")
                .codigo("CAJ001")
                .turno("Mañana")
                .build();

        when(servicioCajero.crearCajero(any(CajeroDto.class))).thenReturn(cajero);

        // When & Then
        mockMvc.perform(post("/api/cajeros")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Juan Pérez\",\"codigo\":\"CAJ001\",\"turno\":\"Mañana\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Juan Pérez")))
                .andExpect(jsonPath("$.codigo", is("CAJ001")))
                .andExpect(jsonPath("$.turno", is("Mañana")));
    }

    @Test
    public void testActualizarCajero() throws Exception {
        // Given
        CajeroDto cajero = CajeroDto.builder()
                .id(1L)
                .nombre("Juan Pérez Actualizado")
                .codigo("CAJ001")
                .turno("Tarde")
                .build();

        when(servicioCajero.actualizarCajero(anyLong(), any(CajeroDto.class))).thenReturn(cajero);

        // When & Then
        mockMvc.perform(put("/api/cajeros/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Juan Pérez Actualizado\",\"codigo\":\"CAJ001\",\"turno\":\"Tarde\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Juan Pérez Actualizado")))
                .andExpect(jsonPath("$.codigo", is("CAJ001")))
                .andExpect(jsonPath("$.turno", is("Tarde")));
    }

    @Test
    public void testEliminarCajero() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/cajeros/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testObtenerCajeroPorCodigo() throws Exception {
        // Given
        CajeroDto cajero = CajeroDto.builder()
                .id(1L)
                .nombre("Juan Pérez")
                .codigo("CAJ001")
                .turno("Mañana")
                .build();

        when(servicioCajero.obtenerCajeroPorCodigo("CAJ001")).thenReturn(Optional.of(cajero));
        when(servicioCajero.obtenerCajeroPorCodigo("CAJ999")).thenReturn(Optional.empty());

        // When & Then - Existing cajero
        mockMvc.perform(get("/api/cajeros/codigo/CAJ001"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Juan Pérez")))
                .andExpect(jsonPath("$.codigo", is("CAJ001")))
                .andExpect(jsonPath("$.turno", is("Mañana")));

        // When & Then - Non-existing cajero
        mockMvc.perform(get("/api/cajeros/codigo/CAJ999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testObtenerCajerosPorTurno() throws Exception {
        // Given
        CajeroDto cajero1 = CajeroDto.builder()
                .id(1L)
                .nombre("Juan Pérez")
                .codigo("CAJ001")
                .turno("Mañana")
                .build();
        CajeroDto cajero2 = CajeroDto.builder()
                .id(3L)
                .nombre("Carlos Rodríguez")
                .codigo("CAJ003")
                .turno("Mañana")
                .build();
        List<CajeroDto> cajerosMañana = Arrays.asList(cajero1, cajero2);

        when(servicioCajero.obtenerCajerosPorTurno("Mañana")).thenReturn(cajerosMañana);
        when(servicioCajero.obtenerCajerosPorTurno("Noche")).thenReturn(Arrays.asList());

        // When & Then - Existing turno with cajeros
        mockMvc.perform(get("/api/cajeros/turno/Mañana"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Juan Pérez")))
                .andExpect(jsonPath("$[0].codigo", is("CAJ001")))
                .andExpect(jsonPath("$[0].turno", is("Mañana")))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].nombre", is("Carlos Rodríguez")))
                .andExpect(jsonPath("$[1].codigo", is("CAJ003")))
                .andExpect(jsonPath("$[1].turno", is("Mañana")));

        // When & Then - Existing turno without cajeros
        mockMvc.perform(get("/api/cajeros/turno/Noche"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
