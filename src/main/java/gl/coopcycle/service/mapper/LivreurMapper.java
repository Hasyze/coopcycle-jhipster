package gl.coopcycle.service.mapper;

import gl.coopcycle.domain.Association;
import gl.coopcycle.domain.Livreur;
import gl.coopcycle.service.dto.AssociationDTO;
import gl.coopcycle.service.dto.LivreurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Livreur} and its DTO {@link LivreurDTO}.
 */
@Mapper(componentModel = "spring")
public interface LivreurMapper extends EntityMapper<LivreurDTO, Livreur> {
    @Mapping(target = "association", source = "association", qualifiedByName = "associationId")
    LivreurDTO toDto(Livreur s);

    @Named("associationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AssociationDTO toDtoAssociationId(Association association);
}
