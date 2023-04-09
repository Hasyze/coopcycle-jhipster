package gl.coopcycle.service.mapper;

import gl.coopcycle.domain.Restaurant;
import gl.coopcycle.service.dto.RestaurantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Restaurant} and its DTO {@link RestaurantDTO}.
 */
@Mapper(componentModel = "spring")
public interface RestaurantMapper extends EntityMapper<RestaurantDTO, Restaurant> {}
