package org.litethinking.restapi.controller.supermercado.venta;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.litethinking.application.service.supermercado.venta.ServicioVenta;
import org.litethinking.shareddto.supermercado.venta.ItemVentaDto;
import org.litethinking.shareddto.supermercado.venta.VentaDto;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ControladorVentaIntegrationTest {

    @Mock
    private ServicioVenta servicioVenta;

    @InjectMocks
    private ControladorVenta controladorVenta;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controladorVenta).build();
    }

    @Test
    public void testObtenerTodasLasVentas() throws Exception {
        // Given
        LocalDateTime fechaVenta1 = LocalDateTime.now();
        LocalDateTime fechaVenta2 = LocalDateTime.now().minusDays(1);
        
        VentaDto venta1 = new VentaDto(1L, fechaVenta1, "Juan Pérez", new ArrayList<>(), new BigDecimal("100.00"), "Efectivo");
        VentaDto venta2 = new VentaDto(2L, fechaVenta2, "María López", new ArrayList<>(), new BigDecimal("200.00"), "Tarjeta");
        List<VentaDto> ventas = Arrays.asList(venta1, venta2);

        when(servicioVenta.obtenerTodasLasVentas()).thenReturn(ventas);

        // When & Then
        mockMvc.perform(get("/api/ventas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombreCliente", is("Juan Pérez")))
                .andExpect(jsonPath("$[0].montoTotal", is(100.00)))
                .andExpect(jsonPath("$[0].metodoPago", is("Efectivo")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nombreCliente", is("María López")))
                .andExpect(jsonPath("$[1].montoTotal", is(200.00)))
                .andExpect(jsonPath("$[1].metodoPago", is("Tarjeta")));
    }

    @Test
    public void testObtenerVentaPorId() throws Exception {
        // Given
        LocalDateTime fechaVenta = LocalDateTime.now();
        VentaDto venta = new VentaDto(1L, fechaVenta, "Juan Pérez", new ArrayList<>(), new BigDecimal("100.00"), "Efectivo");

        when(servicioVenta.obtenerVentaPorId(1L)).thenReturn(Optional.of(venta));
        when(servicioVenta.obtenerVentaPorId(2L)).thenReturn(Optional.empty());

        // When & Then - Existing venta
        mockMvc.perform(get("/api/ventas/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombreCliente", is("Juan Pérez")))
                .andExpect(jsonPath("$.montoTotal", is(100.00)))
                .andExpect(jsonPath("$.metodoPago", is("Efectivo")));

        // When & Then - Non-existing venta
        mockMvc.perform(get("/api/ventas/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCrearVenta() throws Exception {
        // Given
        LocalDateTime fechaVenta = LocalDateTime.now();
        VentaDto venta = new VentaDto(1L, fechaVenta, "Juan Pérez", new ArrayList<>(), new BigDecimal("100.00"), "Efectivo");

        when(servicioVenta.crearVenta(any(VentaDto.class))).thenReturn(venta);

        // When & Then
        mockMvc.perform(post("/api/ventas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fechaVenta\":\"" + fechaVenta.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + 
                        "\",\"nombreCliente\":\"Juan Pérez\",\"items\":[],\"montoTotal\":100.00,\"metodoPago\":\"Efectivo\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombreCliente", is("Juan Pérez")))
                .andExpect(jsonPath("$.montoTotal", is(100.00)))
                .andExpect(jsonPath("$.metodoPago", is("Efectivo")));
    }

    @Test
    public void testActualizarVenta() throws Exception {
        // Given
        LocalDateTime fechaVenta = LocalDateTime.now();
        VentaDto venta = new VentaDto(1L, fechaVenta, "Juan Pérez Actualizado", new ArrayList<>(), new BigDecimal("150.00"), "Tarjeta");

        when(servicioVenta.actualizarVenta(anyLong(), any(VentaDto.class))).thenReturn(venta);

        // When & Then
        mockMvc.perform(put("/api/ventas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fechaVenta\":\"" + fechaVenta.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + 
                        "\",\"nombreCliente\":\"Juan Pérez Actualizado\",\"items\":[],\"montoTotal\":150.00,\"metodoPago\":\"Tarjeta\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombreCliente", is("Juan Pérez Actualizado")))
                .andExpect(jsonPath("$.montoTotal", is(150.00)))
                .andExpect(jsonPath("$.metodoPago", is("Tarjeta")));
    }

    @Test
    public void testEliminarVenta() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/ventas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testObtenerVentasPorNombreCliente() throws Exception {
        // Given
        LocalDateTime fechaVenta1 = LocalDateTime.now();
        LocalDateTime fechaVenta2 = LocalDateTime.now().minusDays(1);
        
        VentaDto venta1 = new VentaDto(1L, fechaVenta1, "Juan Pérez", new ArrayList<>(), new BigDecimal("100.00"), "Efectivo");
        VentaDto venta2 = new VentaDto(3L, fechaVenta2, "Juan Pérez", new ArrayList<>(), new BigDecimal("300.00"), "Transferencia");
        List<VentaDto> ventas = Arrays.asList(venta1, venta2);

        when(servicioVenta.obtenerVentasPorNombreCliente("Juan Pérez")).thenReturn(ventas);

        // When & Then
        mockMvc.perform(get("/api/ventas/cliente/Juan Pérez"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombreCliente", is("Juan Pérez")))
                .andExpect(jsonPath("$[0].montoTotal", is(100.00)))
                .andExpect(jsonPath("$[0].metodoPago", is("Efectivo")))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].nombreCliente", is("Juan Pérez")))
                .andExpect(jsonPath("$[1].montoTotal", is(300.00)))
                .andExpect(jsonPath("$[1].metodoPago", is("Transferencia")));
    }

    @Test
    public void testObtenerVentasPorMetodoPago() throws Exception {
        // Given
        LocalDateTime fechaVenta1 = LocalDateTime.now();
        LocalDateTime fechaVenta2 = LocalDateTime.now().minusDays(1);
        
        VentaDto venta1 = new VentaDto(1L, fechaVenta1, "Juan Pérez", new ArrayList<>(), new BigDecimal("100.00"), "Efectivo");
        VentaDto venta2 = new VentaDto(4L, fechaVenta2, "Ana Gómez", new ArrayList<>(), new BigDecimal("400.00"), "Efectivo");
        List<VentaDto> ventas = Arrays.asList(venta1, venta2);

        when(servicioVenta.obtenerVentasPorMetodoPago("Efectivo")).thenReturn(ventas);

        // When & Then
        mockMvc.perform(get("/api/ventas/metodo-pago/Efectivo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombreCliente", is("Juan Pérez")))
                .andExpect(jsonPath("$[0].montoTotal", is(100.00)))
                .andExpect(jsonPath("$[0].metodoPago", is("Efectivo")))
                .andExpect(jsonPath("$[1].id", is(4)))
                .andExpect(jsonPath("$[1].nombreCliente", is("Ana Gómez")))
                .andExpect(jsonPath("$[1].montoTotal", is(400.00)))
                .andExpect(jsonPath("$[1].metodoPago", is("Efectivo")));
    }

    @Test
    public void testObtenerVentasEntreFechas() throws Exception {
        // Given
        LocalDateTime fechaInicio = LocalDateTime.now().minusDays(2);
        LocalDateTime fechaFin = LocalDateTime.now();
        
        LocalDateTime fechaVenta1 = LocalDateTime.now().minusDays(1);
        LocalDateTime fechaVenta2 = LocalDateTime.now().minusDays(2);
        
        VentaDto venta1 = new VentaDto(1L, fechaVenta1, "Juan Pérez", new ArrayList<>(), new BigDecimal("100.00"), "Efectivo");
        VentaDto venta2 = new VentaDto(2L, fechaVenta2, "María López", new ArrayList<>(), new BigDecimal("200.00"), "Tarjeta");
        List<VentaDto> ventas = Arrays.asList(venta1, venta2);

        when(servicioVenta.obtenerVentasEntreFechas(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(ventas);

        // When & Then
        mockMvc.perform(get("/api/ventas/fechas")
                .param("fechaInicio", fechaInicio.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .param("fechaFin", fechaFin.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombreCliente", is("Juan Pérez")))
                .andExpect(jsonPath("$[0].montoTotal", is(100.00)))
                .andExpect(jsonPath("$[0].metodoPago", is("Efectivo")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nombreCliente", is("María López")))
                .andExpect(jsonPath("$[1].montoTotal", is(200.00)))
                .andExpect(jsonPath("$[1].metodoPago", is("Tarjeta")));
    }
}