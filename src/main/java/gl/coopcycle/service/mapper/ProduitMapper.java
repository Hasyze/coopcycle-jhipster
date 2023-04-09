package gl.coopcycle.service.mapper;

import gl.coopcycle.domain.Client;
import gl.coopcycle.domain.Livraison;
import gl.coopcycle.domain.Produit;
import gl.coopcycle.domain.Restaurant;
import gl.coopcycle.service.dto.ClientDTO;
import gl.coopcycle.service.dto.LivraisonDTO;
import gl.coopcycle.service.dto.ProduitDTO;
import gl.coopcycle.service.dto.RestaurantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produit} and its DTO {@link ProduitDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProduitMapper extends EntityMapper<ProduitDTO, Produit> {
    @Mapping(target = "livraison", source = "livraison", qualifiedByName = "livraisonId")
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    @Mapping(target = "restaurant", source = "restaurant", qualifiedByName = "restaurantId")
    ProduitDTO toDto(Produit s);

    @Named("livraisonId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LivraisonDTO toDtoLivraisonId(Livraison livraison);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);

    @Named("restaurantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RestaurantDTO toDtoRestaurantId(Restaurant restaurant);
}
