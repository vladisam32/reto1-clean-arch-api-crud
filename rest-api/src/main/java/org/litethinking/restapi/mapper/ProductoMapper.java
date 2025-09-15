package org.litethinking.restapi.mapper;

import org.litethinking.domain.model.supermercado.Producto;
import org.litethinking.shareddto.supermercado.ProductoDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between Producto domain model and ProductoDto.
 */
@Component
public class ProductoMapper {

    /**
     * Converts a Producto domain model to a ProductoDto.
     *
     * @param producto the domain model to convert
     * @return the corresponding DTO
     */
    public ProductoDto toDto(Producto producto) {
        if (producto == null) {
            return null;
        }
        
        return new ProductoDto(
            producto.getId(),
            producto.getNombre(),
            producto.getDescripcion(),
            producto.getPrecio(),
            producto.getCategoria(),
            producto.getCodigoBarras()
        );
    }

    /**
     * Converts a list of Producto domain models to a list of ProductoDtos.
     *
     * @param productos the domain models to convert
     * @return the corresponding DTOs
     */
    public List<ProductoDto> toDtoList(List<Producto> productos) {
        if (productos == null) {
            return List.of();
        }
        
        return productos.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Converts a ProductoDto to a Producto domain model.
     *
     * @param dto the DTO to convert
     * @return the corresponding domain model
     */
    public Producto toDomain(ProductoDto dto) {
        if (dto == null) {
            return null;
        }
        
        return Producto.builder()
            .id(dto.id())
            .nombre(dto.nombre())
            .descripcion(dto.descripcion())
            .precio(dto.precio())
            .categoria(dto.categoria())
            .codigoBarras(dto.codigoBarras())
            .build();
    }

    /**
     * Converts a list of ProductoDtos to a list of Producto domain models.
     *
     * @param dtos the DTOs to convert
     * @return the corresponding domain models
     */
    public List<Producto> toDomainList(List<ProductoDto> dtos) {
        if (dtos == null) {
            return List.of();
        }
        
        return dtos.stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }
}