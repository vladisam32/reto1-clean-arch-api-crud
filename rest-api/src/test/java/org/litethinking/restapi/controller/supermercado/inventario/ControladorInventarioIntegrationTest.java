package org.litethinking.restapi.controller.supermercado.inventario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.litethinking.application.service.supermercado.inventario.ServicioInventario;
import org.litethinking.domain.model.supermercado.Producto;
import org.litethinking.domain.model.supermercado.inventario.Inventario;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ControladorInventarioIntegrationTest {

    @Mock
    private ServicioInventario servicioInventario;

    @InjectMocks
    private ControladorInventario controladorInventario;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controladorInventario).build();
    }

    @Test
    public void testObtenerTodoElInventario() throws Exception {
        // Given
        Producto producto1 = new Producto();
        producto1.setId(1L);
        producto1.setNombre("Producto 1");
        producto1.setDescripcion("Descripción 1");
        producto1.setPrecio(new BigDecimal("10.99"));
        producto1.setCategoria("Categoría 1");
        producto1.setCodigoBarras("P001");

        Producto producto2 = new Producto();
        producto2.setId(2L);
        producto2.setNombre("Producto 2");
        producto2.setDescripcion("Descripción 2");
        producto2.setPrecio(new BigDecimal("20.99"));
        producto2.setCategoria("Categoría 2");
        producto2.setCodigoBarras("P002");

        Inventario inventario1 = new Inventario();
        inventario1.setId(1L);
        inventario1.setProducto(producto1);
        inventario1.setCantidad(10);
        inventario1.setStockMinimo(5);
        inventario1.setStockMaximo(20);
        inventario1.setFechaUltimaReposicion(LocalDate.now());
        inventario1.setUbicacion("Pasillo 1");

        Inventario inventario2 = new Inventario();
        inventario2.setId(2L);
        inventario2.setProducto(producto2);
        inventario2.setCantidad(20);
        inventario2.setStockMinimo(5);
        inventario2.setStockMaximo(30);
        inventario2.setFechaUltimaReposicion(LocalDate.now());
        inventario2.setUbicacion("Pasillo 2");

        List<Inventario> inventarios = Arrays.asList(inventario1, inventario2);

        when(servicioInventario.obtenerTodoElInventario()).thenReturn(inventarios);

        // When & Then
        mockMvc.perform(get("/api/inventario"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].producto.nombre", is("Producto 1")))
                .andExpect(jsonPath("$[0].cantidad", is(10)))
                .andExpect(jsonPath("$[0].stockMinimo", is(5)))
                .andExpect(jsonPath("$[0].stockMaximo", is(20)))
                .andExpect(jsonPath("$[0].ubicacion", is("Pasillo 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].producto.nombre", is("Producto 2")))
                .andExpect(jsonPath("$[1].cantidad", is(20)))
                .andExpect(jsonPath("$[1].stockMinimo", is(5)))
                .andExpect(jsonPath("$[1].stockMaximo", is(30)))
                .andExpect(jsonPath("$[1].ubicacion", is("Pasillo 2")));
    }

    @Test
    public void testObtenerInventarioPorId() throws Exception {
        // Given
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto 1");
        producto.setDescripcion("Descripción 1");
        producto.setPrecio(new BigDecimal("10.99"));
        producto.setCategoria("Categoría 1");
        producto.setCodigoBarras("P001");

        Inventario inventario = new Inventario();
        inventario.setId(1L);
        inventario.setProducto(producto);
        inventario.setCantidad(10);
        inventario.setStockMinimo(5);
        inventario.setStockMaximo(20);
        inventario.setFechaUltimaReposicion(LocalDate.now());
        inventario.setUbicacion("Pasillo 1");

        when(servicioInventario.obtenerInventarioPorId(1L)).thenReturn(Optional.of(inventario));
        when(servicioInventario.obtenerInventarioPorId(2L)).thenReturn(Optional.empty());

        // When & Then - Existing inventario
        mockMvc.perform(get("/api/inventario/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.producto.nombre", is("Producto 1")))
                .andExpect(jsonPath("$.cantidad", is(10)))
                .andExpect(jsonPath("$.stockMinimo", is(5)))
                .andExpect(jsonPath("$.stockMaximo", is(20)))
                .andExpect(jsonPath("$.ubicacion", is("Pasillo 1")));

        // When & Then - Non-existing inventario
        mockMvc.perform(get("/api/inventario/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testObtenerInventarioPorProducto() throws Exception {
        // Given
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto 1");
        producto.setDescripcion("Descripción 1");
        producto.setPrecio(new BigDecimal("10.99"));
        producto.setCategoria("Categoría 1");
        producto.setCodigoBarras("P001");

        Inventario inventario = new Inventario();
        inventario.setId(1L);
        inventario.setProducto(producto);
        inventario.setCantidad(10);
        inventario.setStockMinimo(5);
        inventario.setStockMaximo(20);
        inventario.setFechaUltimaReposicion(LocalDate.now());
        inventario.setUbicacion("Pasillo 1");

        when(servicioInventario.obtenerInventarioPorProducto(any(Producto.class))).thenReturn(Optional.of(inventario));

        // When & Then - Existing inventario
        mockMvc.perform(get("/api/inventario/producto/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.producto.nombre", is("Producto 1")))
                .andExpect(jsonPath("$.cantidad", is(10)));
    }

    @Test
    public void testActualizarCantidadInventario() throws Exception {
        // Given
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto 1");
        producto.setDescripcion("Descripción 1");
        producto.setPrecio(new BigDecimal("10.99"));
        producto.setCategoria("Categoría 1");
        producto.setCodigoBarras("P001");

        Inventario inventario = new Inventario();
        inventario.setId(1L);
        inventario.setProducto(producto);
        inventario.setCantidad(15);
        inventario.setStockMinimo(5);
        inventario.setStockMaximo(20);
        inventario.setFechaUltimaReposicion(LocalDate.now());
        inventario.setUbicacion("Pasillo 1");

        when(servicioInventario.actualizarCantidadInventario(anyLong(), any(Integer.class))).thenReturn(inventario);

        // When & Then
        mockMvc.perform(patch("/api/inventario/1/cantidad/15")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.producto.nombre", is("Producto 1")))
                .andExpect(jsonPath("$.cantidad", is(15)));
    }

    @Test
    public void testObtenerInventarioConBajoStock() throws Exception {
        // Given
        Producto producto1 = new Producto();
        producto1.setId(1L);
        producto1.setNombre("Producto 1");
        producto1.setDescripcion("Descripción 1");
        producto1.setPrecio(new BigDecimal("10.99"));
        producto1.setCategoria("Categoría 1");
        producto1.setCodigoBarras("P001");

        Producto producto2 = new Producto();
        producto2.setId(2L);
        producto2.setNombre("Producto 2");
        producto2.setDescripcion("Descripción 2");
        producto2.setPrecio(new BigDecimal("20.99"));
        producto2.setCategoria("Categoría 2");
        producto2.setCodigoBarras("P002");

        Inventario inventario1 = new Inventario();
        inventario1.setId(1L);
        inventario1.setProducto(producto1);
        inventario1.setCantidad(3);
        inventario1.setStockMinimo(5);
        inventario1.setStockMaximo(20);
        inventario1.setFechaUltimaReposicion(LocalDate.now());
        inventario1.setUbicacion("Pasillo 1");

        Inventario inventario2 = new Inventario();
        inventario2.setId(2L);
        inventario2.setProducto(producto2);
        inventario2.setCantidad(4);
        inventario2.setStockMinimo(5);
        inventario2.setStockMaximo(30);
        inventario2.setFechaUltimaReposicion(LocalDate.now());
        inventario2.setUbicacion("Pasillo 2");

        List<Inventario> inventariosBajos = Arrays.asList(inventario1, inventario2);

        when(servicioInventario.obtenerInventarioConBajoStock()).thenReturn(inventariosBajos);

        // When & Then
        mockMvc.perform(get("/api/inventario/bajo-stock"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].producto.nombre", is("Producto 1")))
                .andExpect(jsonPath("$[0].cantidad", is(3)))
                .andExpect(jsonPath("$[0].stockMinimo", is(5)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].producto.nombre", is("Producto 2")))
                .andExpect(jsonPath("$[1].cantidad", is(4)))
                .andExpect(jsonPath("$[1].stockMinimo", is(5)));
    }
    
    @Test
    public void testCrearInventario() throws Exception {
        // Given
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto 1");
        producto.setDescripcion("Descripción 1");
        producto.setPrecio(new BigDecimal("10.99"));
        producto.setCategoria("Categoría 1");
        producto.setCodigoBarras("P001");

        Inventario inventario = new Inventario();
        inventario.setId(1L);
        inventario.setProducto(producto);
        inventario.setCantidad(10);
        inventario.setStockMinimo(5);
        inventario.setStockMaximo(20);
        inventario.setFechaUltimaReposicion(LocalDate.now());
        inventario.setUbicacion("Pasillo 1");

        when(servicioInventario.crearInventario(any(Inventario.class))).thenReturn(inventario);

        // When & Then
        mockMvc.perform(post("/api/inventario")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"producto\":{\"id\":1,\"nombre\":\"Producto 1\",\"descripcion\":\"Descripción 1\",\"precio\":10.99,\"categoria\":\"Categoría 1\",\"codigoBarras\":\"P001\"},\"cantidad\":10,\"stockMinimo\":5,\"stockMaximo\":20,\"ubicacion\":\"Pasillo 1\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.producto.nombre", is("Producto 1")))
                .andExpect(jsonPath("$.cantidad", is(10)))
                .andExpect(jsonPath("$.stockMinimo", is(5)))
                .andExpect(jsonPath("$.stockMaximo", is(20)))
                .andExpect(jsonPath("$.ubicacion", is("Pasillo 1")));
    }
}