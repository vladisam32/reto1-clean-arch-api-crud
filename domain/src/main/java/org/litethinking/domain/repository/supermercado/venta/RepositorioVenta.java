package org.litethinking.domain.repository.supermercado.venta;

import org.litethinking.domain.model.supermercado.venta.Venta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Venta entity.
 */
public interface RepositorioVenta {
    
    /**
     * Save a sale.
     *
     * @param venta the sale to save
     * @return the saved sale
     */
    Venta save(Venta venta);
    
    /**
     * Find a sale by its id.
     *
     * @param id the id of the sale
     * @return the sale if found, empty otherwise
     */
    Optional<Venta> findById(Long id);
    
    /**
     * Find all sales.
     *
     * @return the list of sales
     */
    List<Venta> findAll();
    
    /**
     * Delete a sale by its id.
     *
     * @param id the id of the sale to delete
     */
    void deleteById(Long id);
    
    /**
     * Find sales by date range.
     *
     * @param fechaInicio the start date
     * @param fechaFin the end date
     * @return the list of sales in the date range
     */
    List<Venta> findByFechaVentaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    /**
     * Find sales by customer name.
     *
     * @param nombreCliente the customer name to search for
     * @return the list of sales for the customer
     */
    List<Venta> findByNombreCliente(String nombreCliente);
    
    /**
     * Find sales by total amount greater than.
     *
     * @param montoMinimo the minimum total amount
     * @return the list of sales with total amount greater than the minimum
     */
    List<Venta> findByMontoTotalGreaterThan(BigDecimal montoMinimo);
    
    /**
     * Find sales by payment method.
     *
     * @param metodoPago the payment method to search for
     * @return the list of sales with the payment method
     */
    List<Venta> findByMetodoPago(String metodoPago);
}