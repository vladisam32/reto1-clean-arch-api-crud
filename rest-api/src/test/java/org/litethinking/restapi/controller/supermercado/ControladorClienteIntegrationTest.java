package org.litethinking.restapi.controller.supermercado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.litethinking.shareddto.supermercado.ClienteDto;
import org.litethinking.application.service.supermercado.ServicioCliente;
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

public class ControladorClienteIntegrationTest {

    private MockMvc mockMvc;

    @Mock
    private ServicioCliente servicioCliente;

    @InjectMocks
    private ControladorCliente controladorCliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controladorCliente).build();
    }

    @Test
    public void testObtenerTodosLosClientes() throws Exception {
        // Given
        ClienteDto cliente1 = new ClienteDto(
                1L,
                "Ana García",
                "ana.garcia@example.com",
                "555-123-4567",
                "Calle Principal 123"
        );
        ClienteDto cliente2 = new ClienteDto(
                2L,
                "Pedro Martínez",
                "pedro.martinez@example.com",
                "555-987-6543",
                "Avenida Central 456"
        );
        List<ClienteDto> clientes = Arrays.asList(cliente1, cliente2);

        when(servicioCliente.obtenerTodosLosClientes()).thenReturn(clientes);

        // When & Then
        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Ana García")))
                .andExpect(jsonPath("$[0].email", is("ana.garcia@example.com")))
                .andExpect(jsonPath("$[0].telefono", is("555-123-4567")))
                .andExpect(jsonPath("$[0].direccion", is("Calle Principal 123")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nombre", is("Pedro Martínez")))
                .andExpect(jsonPath("$[1].email", is("pedro.martinez@example.com")))
                .andExpect(jsonPath("$[1].telefono", is("555-987-6543")))
                .andExpect(jsonPath("$[1].direccion", is("Avenida Central 456")));
    }

    @Test
    public void testObtenerClientePorId() throws Exception {
        // Given
        ClienteDto cliente = new ClienteDto(
                1L,
                "Ana García",
                "ana.garcia@example.com",
                "555-123-4567",
                "Calle Principal 123"
        );

        when(servicioCliente.obtenerClientePorId(1L)).thenReturn(Optional.of(cliente));
        when(servicioCliente.obtenerClientePorId(2L)).thenReturn(Optional.empty());

        // When & Then - Existing cliente
        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Ana García")))
                .andExpect(jsonPath("$.email", is("ana.garcia@example.com")))
                .andExpect(jsonPath("$.telefono", is("555-123-4567")))
                .andExpect(jsonPath("$.direccion", is("Calle Principal 123")));

        // When & Then - Non-existing cliente
        mockMvc.perform(get("/api/clientes/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCrearCliente() throws Exception {
        // Given
        ClienteDto cliente = new ClienteDto(
                1L,
                "Ana García",
                "ana.garcia@example.com",
                "555-123-4567",
                "Calle Principal 123"
        );

        when(servicioCliente.crearCliente(any(ClienteDto.class))).thenReturn(cliente);

        // When & Then
        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Ana García\",\"email\":\"ana.garcia@example.com\",\"telefono\":\"555-123-4567\",\"direccion\":\"Calle Principal 123\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Ana García")))
                .andExpect(jsonPath("$.email", is("ana.garcia@example.com")))
                .andExpect(jsonPath("$.telefono", is("555-123-4567")))
                .andExpect(jsonPath("$.direccion", is("Calle Principal 123")));
    }

    @Test
    public void testActualizarCliente() throws Exception {
        // Given
        ClienteDto cliente = new ClienteDto(
                1L,
                "Ana García Actualizado",
                "ana.garcia@example.com",
                "555-123-4567",
                "Calle Principal 123 Actualizado"
        );

        when(servicioCliente.actualizarCliente(anyLong(), any(ClienteDto.class))).thenReturn(cliente);

        // When & Then
        mockMvc.perform(put("/api/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Ana García Actualizado\",\"email\":\"ana.garcia@example.com\",\"telefono\":\"555-123-4567\",\"direccion\":\"Calle Principal 123 Actualizado\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Ana García Actualizado")))
                .andExpect(jsonPath("$.email", is("ana.garcia@example.com")))
                .andExpect(jsonPath("$.telefono", is("555-123-4567")))
                .andExpect(jsonPath("$.direccion", is("Calle Principal 123 Actualizado")));
    }

    @Test
    public void testEliminarCliente() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testObtenerClientePorEmail() throws Exception {
        // Given
        ClienteDto cliente = new ClienteDto(
                1L,
                "Ana García",
                "ana.garcia@example.com",
                "555-123-4567",
                "Calle Principal 123"
        );

        when(servicioCliente.obtenerClientePorEmail("ana.garcia@example.com")).thenReturn(Optional.of(cliente));
        when(servicioCliente.obtenerClientePorEmail("noexiste@example.com")).thenReturn(Optional.empty());

        // When & Then - Existing cliente
        mockMvc.perform(get("/api/clientes/email/ana.garcia@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Ana García")))
                .andExpect(jsonPath("$.email", is("ana.garcia@example.com")))
                .andExpect(jsonPath("$.telefono", is("555-123-4567")))
                .andExpect(jsonPath("$.direccion", is("Calle Principal 123")));

        // When & Then - Non-existing cliente
        mockMvc.perform(get("/api/clientes/email/noexiste@example.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testObtenerClientesPorNombreContaining() throws Exception {
        // Given
        ClienteDto cliente1 = new ClienteDto(
                1L,
                "Ana García",
                "ana.garcia@example.com",
                "555-123-4567",
                "Calle Principal 123"
        );
        ClienteDto cliente2 = new ClienteDto(
                3L,
                "Ana Rodríguez",
                "ana.rodriguez@example.com",
                "555-555-5555",
                "Plaza Mayor 789"
        );
        List<ClienteDto> clientesAna = Arrays.asList(cliente1, cliente2);

        when(servicioCliente.obtenerClientesPorNombreContaining("Ana")).thenReturn(clientesAna);
        when(servicioCliente.obtenerClientesPorNombreContaining("Xyz")).thenReturn(Arrays.asList());

        // When & Then - Existing nombre with clientes
        mockMvc.perform(get("/api/clientes/nombre/Ana"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Ana García")))
                .andExpect(jsonPath("$[0].email", is("ana.garcia@example.com")))
                .andExpect(jsonPath("$[0].telefono", is("555-123-4567")))
                .andExpect(jsonPath("$[0].direccion", is("Calle Principal 123")))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].nombre", is("Ana Rodríguez")))
                .andExpect(jsonPath("$[1].email", is("ana.rodriguez@example.com")))
                .andExpect(jsonPath("$[1].telefono", is("555-555-5555")))
                .andExpect(jsonPath("$[1].direccion", is("Plaza Mayor 789")));

        // When & Then - Existing nombre without clientes
        mockMvc.perform(get("/api/clientes/nombre/Xyz"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
