package gl.coopcycle.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link gl.coopcycle.domain.Produit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idProd;

    @NotNull
    private String nomProd;

    @NotNull
    @DecimalMin(value = "0")
    private Float prixProd;

    private LivraisonDTO livraison;

    private ClientDTO client;

    private RestaurantDTO restaurant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdProd() {
        return idProd;
    }

    public void setIdProd(Integer idProd) {
        this.idProd = idProd;
    }

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public Float getPrixProd() {
        return prixProd;
    }

    public void setPrixProd(Float prixProd) {
        this.prixProd = prixProd;
    }

    public LivraisonDTO getLivraison() {
        return livraison;
    }

    public void setLivraison(LivraisonDTO livraison) {
        this.livraison = livraison;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public RestaurantDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDTO restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitDTO)) {
            return false;
        }

        ProduitDTO produitDTO = (ProduitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, produitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitDTO{" +
            "id=" + getId() +
            ", idProd=" + getIdProd() +
            ", nomProd='" + getNomProd() + "'" +
            ", prixProd=" + getPrixProd() +
            ", livraison=" + getLivraison() +
            ", client=" + getClient() +
            ", restaurant=" + getRestaurant() +
            "}";
    }
}
