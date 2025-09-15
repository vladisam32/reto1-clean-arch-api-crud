package org.litethinking.restapi.controller.supermercado.inventario;

import org.litethinking.application.service.supermercado.inventario.ServicioInventario;
import org.litethinking.domain.model.supermercado.Producto;
import org.litethinking.domain.model.supermercado.inventario.Inventario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for Inventario operations.
 */
@RestController
@RequestMapping("/api/inventario")
public class ControladorInventario {

    private final ServicioInventario servicioInventario;

    public ControladorInventario(ServicioInventario servicioInventario) {
        this.servicioInventario = servicioInventario;
    }

    /**
     * Create a new inventory record.
     *
     * @param inventario the inventory to create
     * @return the created inventory
     */
    @PostMapping
    public ResponseEntity<Inventario> crearInventario(@RequestBody Inventario inventario) {
        Inventario inventarioCreado = servicioInventario.crearInventario(inventario);
        return new ResponseEntity<>(inventarioCreado, HttpStatus.CREATED);
    }

    /**
     * Update an existing inventory record.
     *
     * @param id the id of the inventory to update
     * @param inventario the updated inventory data
     * @return the updated inventory
     */
    @PutMapping("/{id}")
    public ResponseEntity<Inventario> actualizarInventario(@PathVariable Long id, @RequestBody Inventario inventario) {
        Inventario inventarioActualizado = servicioInventario.actualizarInventario(id, inventario);
        return ResponseEntity.ok(inventarioActualizado);
    }

    /**
     * Get an inventory record by its id.
     *
     * @param id the id of the inventory
     * @return the inventory if found, 404 otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inventario> obtenerInventarioPorId(@PathVariable Long id) {
        Optional<Inventario> inventario = servicioInventario.obtenerInventarioPorId(id);
        return inventario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Get all inventory records.
     *
     * @return the list of all inventory records
     */
    @GetMapping
    public ResponseEntity<List<Inventario>> obtenerTodoElInventario() {
        List<Inventario> listaInventario = servicioInventario.obtenerTodoElInventario();
        return ResponseEntity.ok(listaInventario);
    }

    /**
     * Delete an inventory record by its id.
     *
     * @param id the id of the inventory to delete
     * @return 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInventario(@PathVariable Long id) {
        servicioInventario.eliminarInventario(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get inventory record by product.
     *
     * @param productoId the id of the product to search for
     * @return the inventory if found, 404 otherwise
     */
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<Inventario> obtenerInventarioPorProducto(@PathVariable Long productoId) {
        Producto producto = new Producto();
        producto.setId(productoId);
        Optional<Inventario> inventario = servicioInventario.obtenerInventarioPorProducto(producto);
        return inventario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Get inventory records with low stock.
     *
     * @return the list of inventory records with low stock
     */
    @GetMapping("/bajo-stock")
    public ResponseEntity<List<Inventario>> obtenerInventarioConBajoStock() {
        List<Inventario> listaInventario = servicioInventario.obtenerInventarioConBajoStock();
        return ResponseEntity.ok(listaInventario);
    }

    /**
     * Get inventory records by location.
     *
     * @param ubicacion the location to search for
     * @return the list of inventory records in the location
     */
    @GetMapping("/ubicacion/{ubicacion}")
    public ResponseEntity<List<Inventario>> obtenerInventarioPorUbicacion(@PathVariable String ubicacion) {
        List<Inventario> listaInventario = servicioInventario.obtenerInventarioPorUbicacion(ubicacion);
        return ResponseEntity.ok(listaInventario);
    }

    /**
     * Update inventory quantity.
     *
     * @param id the id of the inventory to update
     * @param cantidad the new quantity
     * @return the updated inventory
     */
    @PatchMapping("/{id}/cantidad/{cantidad}")
    public ResponseEntity<Inventario> actualizarCantidadInventario(@PathVariable Long id, @PathVariable Integer cantidad) {
        Inventario inventarioActualizado = servicioInventario.actualizarCantidadInventario(id, cantidad);
        return ResponseEntity.ok(inventarioActualizado);
    }

    /**
     * Genera un archivo CSV con todo el inventario.
     *
     * @return un archivo CSV con todo el inventario
     */
    @GetMapping("/csv")
    public ResponseEntity<String> generarCsvInventario() {
        List<Inventario> inventarios = servicioInventario.obtenerTodoElInventario();

        // Crear el contenido del CSV
        StringBuilder csv = new StringBuilder();
        csv.append("id,producto_id,cantidad,stockMinimo,stockMaximo,fechaUltimaReposicion,ubicacion\n");

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        for (Inventario inventario : inventarios) {
            csv.append(inventario.getId()).append(",")
               .append(inventario.getProducto().getId()).append(",")
               .append(inventario.getCantidad()).append(",")
               .append(inventario.getStockMinimo()).append(",")
               .append(inventario.getStockMaximo()).append(",")
               .append(inventario.getFechaUltimaReposicion().format(formatter)).append(",")
               .append(inventario.getUbicacion()).append("\n");
        }

        // Configurar las cabeceras para la descarga del archivo
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "inventario.csv");

        return new ResponseEntity<>(csv.toString(), headers, HttpStatus.OK);
    }
}
