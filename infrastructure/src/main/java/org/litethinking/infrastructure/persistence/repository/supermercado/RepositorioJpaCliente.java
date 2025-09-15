package org.litethinking.infrastructure.persistence.repository.supermercado;

import org.litethinking.infrastructure.persistence.entity.supermercado.EntidadJpaCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository for EntidadJpaCliente.
 */
public interface RepositorioJpaCliente extends JpaRepository<EntidadJpaCliente, Long> {

    /**
     * Find a customer by its email.
     *
     * @param email the email to search for
     * @return the customer if found, empty otherwise
     */
    Optional<EntidadJpaCliente> findByEmail(String email);

    /**
     * Find customers by name containing the given string.
     *
     * @param nombre the name substring to search for
     * @return the list of customers with matching names
     */
    List<EntidadJpaCliente> findByNombreContaining(String nombre);
}
