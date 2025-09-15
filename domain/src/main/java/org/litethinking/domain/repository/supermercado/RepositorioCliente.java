package org.litethinking.domain.repository.supermercado;

import org.litethinking.domain.model.supermercado.Cliente;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Cliente entity.
 */
public interface RepositorioCliente {
    
    /**
     * Save a customer.
     *
     * @param cliente the customer to save
     * @return the saved customer
     */
    Cliente save(Cliente cliente);
    
    /**
     * Find a customer by its id.
     *
     * @param id the id of the customer
     * @return the customer if found, empty otherwise
     */
    Optional<Cliente> findById(Long id);
    
    /**
     * Find all customers.
     *
     * @return the list of customers
     */
    List<Cliente> findAll();
    
    /**
     * Delete a customer by its id.
     *
     * @param id the id of the customer to delete
     */
    void deleteById(Long id);
    
    /**
     * Find a customer by its email.
     *
     * @param email the email to search for
     * @return the customer if found, empty otherwise
     */
    Optional<Cliente> findByEmail(String email);
    
    /**
     * Find customers by name containing the given string.
     *
     * @param nombre the name substring to search for
     * @return the list of customers with matching names
     */
    List<Cliente> findByNombreContaining(String nombre);
}