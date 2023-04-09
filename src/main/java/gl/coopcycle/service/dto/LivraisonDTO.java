package gl.coopcycle.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link gl.coopcycle.domain.Livraison} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LivraisonDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idLivraison;

    @NotNull
    @DecimalMin(value = "0")
    private Float prixLivraison;

    @NotNull
    private Instant date;

    @NotNull
    private String adresseLivraison;

    private ClientDTO client;

    private LivreurDTO livreur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdLivraison() {
        return idLivraison;
    }

    public void setIdLivraison(Integer idLivraison) {
        this.idLivraison = idLivraison;
    }

    public Float getPrixLivraison() {
        return prixLivraison;
    }

    public void setPrixLivraison(Float prixLivraison) {
        this.prixLivraison = prixLivraison;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public LivreurDTO getLivreur() {
        return livreur;
    }

    public void setLivreur(LivreurDTO livreur) {
        this.livreur = livreur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LivraisonDTO)) {
            return false;
        }

        LivraisonDTO livraisonDTO = (LivraisonDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, livraisonDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LivraisonDTO{" +
            "id=" + getId() +
            ", idLivraison=" + getIdLivraison() +
            ", prixLivraison=" + getPrixLivraison() +
            ", date='" + getDate() + "'" +
            ", adresseLivraison='" + getAdresseLivraison() + "'" +
            ", client=" + getClient() +
            ", livreur=" + getLivreur() +
            "}";
    }
}
