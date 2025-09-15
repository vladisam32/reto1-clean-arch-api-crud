package org.litethinking.infrastructure.persistence.repository.supermercado.venta;

import org.litethinking.infrastructure.persistence.entity.supermercado.venta.EntidadJpaVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * JPA repository for EntidadJpaVenta.
 */
public interface RepositorioJpaVenta extends JpaRepository<EntidadJpaVenta, Long> {

    /**
     * Find sales by date range.
     *
     * @param fechaInicio the start date
     * @param fechaFin the end date
     * @return the list of sales in the date range
     */
    List<EntidadJpaVenta> findByFechaVentaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    /**
     * Find sales by customer name.
     *
     * @param nombreCliente the customer name to search for
     * @return the list of sales for the customer
     */
    List<EntidadJpaVenta> findByNombreCliente(String nombreCliente);

    /**
     * Find sales by total amount greater than.
     *
     * @param montoMinimo the minimum total amount
     * @return the list of sales with total amount greater than the minimum
     */
    List<EntidadJpaVenta> findByMontoTotalGreaterThan(BigDecimal montoMinimo);

    /**
     * Find sales by payment method.
     *
     * @param metodoPago the payment method to search for
     * @return the list of sales with the payment method
     */
    List<EntidadJpaVenta> findByMetodoPago(String metodoPago);
}
