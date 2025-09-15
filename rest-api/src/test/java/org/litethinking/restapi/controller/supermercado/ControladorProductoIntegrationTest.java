package org.litethinking.restapi.controller.supermercado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.litethinking.shareddto.supermercado.ProductoDto;
import org.litethinking.application.service.supermercado.ServicioProducto;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ControladorProductoIntegrationTest {

    @Mock
    private ServicioProducto servicioProducto;

    @InjectMocks
    private ControladorProducto controladorProducto;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controladorProducto).build();
    }

    @Test
    public void testObtenerTodosLosProductos() throws Exception {
        // Given
        ProductoDto producto1 = new ProductoDto(1L, "Producto 1", "Descripción 1", new BigDecimal("10.99"), "Categoría 1", "P001");
        ProductoDto producto2 = new ProductoDto(2L, "Producto 2", "Descripción 2", new BigDecimal("20.99"), "Categoría 2", "P002");
        List<ProductoDto> productos = Arrays.asList(producto1, producto2);

        when(servicioProducto.obtenerTodosLosProductos()).thenReturn(productos);

        // When & Then
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Producto 1")))
                .andExpect(jsonPath("$[0].descripcion", is("Descripción 1")))
                .andExpect(jsonPath("$[0].precio", is(10.99)))
                .andExpect(jsonPath("$[0].categoria", is("Categoría 1")))
                .andExpect(jsonPath("$[0].codigoBarras", is("P001")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nombre", is("Producto 2")))
                .andExpect(jsonPath("$[1].descripcion", is("Descripción 2")))
                .andExpect(jsonPath("$[1].precio", is(20.99)))
                .andExpect(jsonPath("$[1].categoria", is("Categoría 2")))
                .andExpect(jsonPath("$[1].codigoBarras", is("P002")));
    }

    @Test
    public void testObtenerProductoPorId() throws Exception {
        // Given
        ProductoDto producto = new ProductoDto(1L, "Producto 1", "Descripción 1", new BigDecimal("10.99"), "Categoría 1", "P001");

        when(servicioProducto.obtenerProductoPorId(1L)).thenReturn(Optional.of(producto));
        when(servicioProducto.obtenerProductoPorId(2L)).thenReturn(Optional.empty());

        // When & Then - Existing producto
        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Producto 1")))
                .andExpect(jsonPath("$.descripcion", is("Descripción 1")))
                .andExpect(jsonPath("$.precio", is(10.99)))
                .andExpect(jsonPath("$.categoria", is("Categoría 1")))
                .andExpect(jsonPath("$.codigoBarras", is("P001")));

        // When & Then - Non-existing producto
        mockMvc.perform(get("/api/productos/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCrearProducto() throws Exception {
        // Given
        ProductoDto producto = new ProductoDto(1L, "Producto 1", "Descripción 1", new BigDecimal("10.99"), "Categoría 1", "P001");

        when(servicioProducto.crearProducto(any(ProductoDto.class))).thenReturn(producto);

        // When & Then
        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Producto 1\",\"descripcion\":\"Descripción 1\",\"precio\":10.99,\"categoria\":\"Categoría 1\",\"codigoBarras\":\"P001\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Producto 1")))
                .andExpect(jsonPath("$.descripcion", is("Descripción 1")))
                .andExpect(jsonPath("$.precio", is(10.99)))
                .andExpect(jsonPath("$.categoria", is("Categoría 1")))
                .andExpect(jsonPath("$.codigoBarras", is("P001")));
    }

    @Test
    public void testActualizarProducto() throws Exception {
        // Given
        ProductoDto producto = new ProductoDto(1L, "Producto 1 Actualizado", "Descripción 1 Actualizada", new BigDecimal("15.99"), "Categoría 1", "P001");

        when(servicioProducto.actualizarProducto(anyLong(), any(ProductoDto.class))).thenReturn(producto);

        // When & Then
        mockMvc.perform(put("/api/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Producto 1 Actualizado\",\"descripcion\":\"Descripción 1 Actualizada\",\"precio\":15.99,\"categoria\":\"Categoría 1\",\"codigoBarras\":\"P001\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Producto 1 Actualizado")))
                .andExpect(jsonPath("$.descripcion", is("Descripción 1 Actualizada")))
                .andExpect(jsonPath("$.precio", is(15.99)))
                .andExpect(jsonPath("$.categoria", is("Categoría 1")))
                .andExpect(jsonPath("$.codigoBarras", is("P001")));
    }

    @Test
    public void testEliminarProducto() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testObtenerProductosPorCategoria() throws Exception {
        // Given
        ProductoDto producto1 = new ProductoDto(1L, "Producto 1", "Descripción 1", new BigDecimal("10.99"), "Categoría 1", "P001");
        ProductoDto producto2 = new ProductoDto(3L, "Producto 3", "Descripción 3", new BigDecimal("30.99"), "Categoría 1", "P003");
        List<ProductoDto> productos = Arrays.asList(producto1, producto2);

        when(servicioProducto.obtenerProductosPorCategoria("Categoría 1")).thenReturn(productos);

        // When & Then
        mockMvc.perform(get("/api/productos/categoria/Categoría 1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Producto 1")))
                .andExpect(jsonPath("$[0].categoria", is("Categoría 1")))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].nombre", is("Producto 3")))
                .andExpect(jsonPath("$[1].categoria", is("Categoría 1")));
    }

    @Test
    public void testObtenerProductoPorCodigoBarras() throws Exception {
        // Given
        ProductoDto producto = new ProductoDto(1L, "Producto 1", "Descripción 1", new BigDecimal("10.99"), "Categoría 1", "P001");

        when(servicioProducto.obtenerProductoPorCodigoBarras("P001")).thenReturn(Optional.of(producto));
        when(servicioProducto.obtenerProductoPorCodigoBarras("P999")).thenReturn(Optional.empty());

        // When & Then - Existing producto
        mockMvc.perform(get("/api/productos/codigo-barras/P001"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Producto 1")))
                .andExpect(jsonPath("$.codigoBarras", is("P001")));

        // When & Then - Non-existing producto
        mockMvc.perform(get("/api/productos/codigo-barras/P999"))
                .andExpect(status().isNotFound());
    }
}
