package org.litethinking.domain.repository.supermercado.inventario;

import org.litethinking.domain.model.supermercado.Producto;
import org.litethinking.domain.model.supermercado.inventario.Inventario;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Inventario entity.
 */
public interface RepositorioInventario {
    
    /**
     * Save an inventory record.
     *
     * @param inventario the inventory to save
     * @return the saved inventory
     */
    Inventario save(Inventario inventario);
    
    /**
     * Find an inventory record by its id.
     *
     * @param id the id of the inventory
     * @return the inventory if found, empty otherwise
     */
    Optional<Inventario> findById(Long id);
    
    /**
     * Find all inventory records.
     *
     * @return the list of inventory records
     */
    List<Inventario> findAll();
    
    /**
     * Delete an inventory record by its id.
     *
     * @param id the id of the inventory to delete
     */
    void deleteById(Long id);
    
    /**
     * Find an inventory record by its product.
     *
     * @param producto the product to search for
     * @return the inventory if found, empty otherwise
     */
    Optional<Inventario> findByProducto(Producto producto);
    
    /**
     * Find inventory records with low stock.
     *
     * @return the list of inventory records with low stock
     */
    List<Inventario> findBajoStock();
    
    /**
     * Find inventory records by location.
     *
     * @param ubicacion the location to search for
     * @return the list of inventory records in the location
     */
    List<Inventario> findByUbicacion(String ubicacion);
}