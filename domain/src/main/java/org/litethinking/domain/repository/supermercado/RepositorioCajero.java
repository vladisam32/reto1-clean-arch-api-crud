package org.litethinking.domain.repository.supermercado;

import org.litethinking.domain.model.supermercado.Cajero;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Cajero entity.
 */
public interface RepositorioCajero {
    
    /**
     * Save a cashier.
     *
     * @param cajero the cashier to save
     * @return the saved cashier
     */
    Cajero save(Cajero cajero);
    
    /**
     * Find a cashier by its id.
     *
     * @param id the id of the cashier
     * @return the cashier if found, empty otherwise
     */
    Optional<Cajero> findById(Long id);
    
    /**
     * Find all cashiers.
     *
     * @return the list of cashiers
     */
    List<Cajero> findAll();
    
    /**
     * Delete a cashier by its id.
     *
     * @param id the id of the cashier to delete
     */
    void deleteById(Long id);
    
    /**
     * Find a cashier by its code.
     *
     * @param codigo the code to search for
     * @return the cashier if found, empty otherwise
     */
    Optional<Cajero> findByCodigo(String codigo);
    
    /**
     * Find cashiers by shift.
     *
     * @param turno the shift to search for
     * @return the list of cashiers in the shift
     */
    List<Cajero> findByTurno(String turno);
}