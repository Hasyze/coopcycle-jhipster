package gl.coopcycle.service.mapper;

import gl.coopcycle.domain.Association;
import gl.coopcycle.service.dto.AssociationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Association} and its DTO {@link AssociationDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssociationMapper extends EntityMapper<AssociationDTO, Association> {}
