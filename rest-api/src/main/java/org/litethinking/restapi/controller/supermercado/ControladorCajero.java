package org.litethinking.restapi.controller.supermercado;

import org.litethinking.application.service.supermercado.ServicioCajero;
import org.litethinking.shareddto.supermercado.CajeroDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST pa' las operaciones de Cajero, ¡dale!
 */
@RestController
@RequestMapping("/api/cajeros")
@Slf4j
public class ControladorCajero {

    private final ServicioCajero servicioCajero;

    public ControladorCajero(ServicioCajero servicioCajero) {
        this.servicioCajero = servicioCajero;
        log.info("Controlador de Cajero inicializao y listo pa' usarse");
    }

    /**
     * Crea un cajero nuevo, pa' que atienda la caja.
     *
     * @param cajeroDto el cajero que vamo' a crear
     * @return el cajero ya creao'
     */
    @PostMapping
    public ResponseEntity<CajeroDto> crearCajero(@RequestBody CajeroDto cajeroDto) {
        log.info("Creando un cajero nuevo con nombre: {}", cajeroDto.nombre());
        CajeroDto cajeroCreado = servicioCajero.crearCajero(cajeroDto);
        log.debug("Cajero creao exitosamente con ID: {}", cajeroCreado.id());
        return new ResponseEntity<>(cajeroCreado, HttpStatus.CREATED);
    }

    /**
     * Actualiza un cajero que ya existe, pa' cambiarle los datos
     *
     * @param id el ID del cajero que vamo' a actualizar
     * @param cajeroDto los datos nuevos del cajero
     * @return el cajero ya actualizao
     */
    @PutMapping("/{id}")
    public ResponseEntity<CajeroDto> actualizarCajero(@PathVariable Long id, @RequestBody CajeroDto cajeroDto) {
        log.info("Actualizando el cajero con ID: {}", id);
        CajeroDto cajeroActualizado = servicioCajero.actualizarCajero(id, cajeroDto);
        log.debug("Cajero actualizao exitosamente: {}", cajeroActualizado.nombre());
        return ResponseEntity.ok(cajeroActualizado);
    }

    /**
     * Busca un cajero por su ID, a ver si lo encontramo'.
     *
     * @param id el ID del cajero que tamo' buscando
     * @return el cajero si aparece, si no un 404 pa' que sepa
     */
    @GetMapping("/{id}")
    public ResponseEntity<CajeroDto> obtenerCajeroPorId(@PathVariable Long id) {
        log.info("Buscando cajero con ID: {}", id);
        Optional<CajeroDto> cajero = servicioCajero.obtenerCajeroPorId(id);
        if (cajero.isPresent()) {
            log.debug("Cajero encontrao: {}", cajero.get().nombre());
            return ResponseEntity.ok(cajero.get());
        } else {
            log.warn("No se encontró ningún cajero con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Trae toos los cajeros que hay en el sistema
     *
     * @return la lista completa de cajeros
     */
    @GetMapping
    public ResponseEntity<List<CajeroDto>> obtenerTodosLosCajeros() {
        log.info("Obteniendo toos los cajeros");
        List<CajeroDto> cajeros = servicioCajero.obtenerTodosLosCajeros();
        log.debug("Se encontraron {} cajeros", cajeros.size());
        return ResponseEntity.ok(cajeros);
    }

    /**
     * Elimina un cajero del sistema, pa' cuando ya no trabaje aquí
     *
     * @param id el ID del cajero que vamo' a eliminar
     * @return 204 si to' salió bien
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCajero(@PathVariable Long id) {
        log.info("Eliminando cajero con ID: {}", id);
        servicioCajero.eliminarCajero(id);
        log.debug("Cajero eliminao exitosamente");
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca un cajero por su código, ¡al toque!
     *
     * @param codigo el código que tamo' buscando
     * @return el cajero si lo encontramo', si no un 404
     */
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<CajeroDto> obtenerCajeroPorCodigo(@PathVariable String codigo) {
        log.info("Buscando cajero con código: {}", codigo);
        Optional<CajeroDto> cajero = servicioCajero.obtenerCajeroPorCodigo(codigo);
        if (cajero.isPresent()) {
            log.debug("Cajero encontrao por código: {}", cajero.get().nombre());
            return ResponseEntity.ok(cajero.get());
        } else {
            log.warn("No se encontró ningún cajero con código: {}", codigo);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Busca toos los cajeros de un turno específico
     *
     * @param turno el turno que queremo filtrar
     * @return la lista de cajeros de ese turno
     */
    @GetMapping("/turno/{turno}")
    public ResponseEntity<List<CajeroDto>> obtenerCajerosPorTurno(@PathVariable String turno) {
        log.info("Buscando cajeros del turno: {}", turno);
        List<CajeroDto> cajeros = servicioCajero.obtenerCajerosPorTurno(turno);
        log.debug("Se encontraron {} cajeros en el turno {}", cajeros.size(), turno);
        return ResponseEntity.ok(cajeros);
    }
}
