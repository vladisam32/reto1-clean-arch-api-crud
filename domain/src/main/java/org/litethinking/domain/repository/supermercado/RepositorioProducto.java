package org.litethinking.domain.repository.supermercado;

import org.litethinking.domain.model.supermercado.Producto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Producto entity.
 */
public interface RepositorioProducto {
    
    /**
     * Save a product.
     *
     * @param producto the product to save
     * @return the saved product
     */
    Producto save(Producto producto);
    
    /**
     * Find a product by its id.
     *
     * @param id the id of the product
     * @return the product if found, empty otherwise
     */
    Optional<Producto> findById(Long id);
    
    /**
     * Find all products.
     *
     * @return the list of products
     */
    List<Producto> findAll();
    
    /**
     * Delete a product by its id.
     *
     * @param id the id of the product to delete
     */
    void deleteById(Long id);
    
    /**
     * Find products by category.
     *
     * @param categoria the category to search for
     * @return the list of products in the category
     */
    List<Producto> findByCategoria(String categoria);
    
    /**
     * Find products by price range.
     *
     * @param precioMinimo the minimum price
     * @param precioMaximo the maximum price
     * @return the list of products in the price range
     */
    List<Producto> findByPrecioBetween(BigDecimal precioMinimo, BigDecimal precioMaximo);
    
    /**
     * Find a product by its barcode.
     *
     * @param codigoBarras the barcode to search for
     * @return the product if found, empty otherwise
     */
    Optional<Producto> findByCodigoBarras(String codigoBarras);
}