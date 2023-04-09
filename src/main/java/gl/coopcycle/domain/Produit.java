package gl.coopcycle.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Produit.
 */
@Entity
@Table(name = "produit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_prod", nullable = false, unique = true)
    private Integer idProd;

    @NotNull
    @Column(name = "nom_prod", nullable = false)
    private String nomProd;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "prix_prod", nullable = false)
    private Float prixProd;

    @JsonIgnoreProperties(value = { "client", "produit", "livreur" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Livraison livraison;

    @ManyToOne
    @JsonIgnoreProperties(value = { "produits", "livraisons" }, allowSetters = true)
    private Client client;

    @ManyToOne
    @JsonIgnoreProperties(value = { "produits" }, allowSetters = true)
    private Restaurant restaurant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Produit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdProd() {
        return this.idProd;
    }

    public Produit idProd(Integer idProd) {
        this.setIdProd(idProd);
        return this;
    }

    public void setIdProd(Integer idProd) {
        this.idProd = idProd;
    }

    public String getNomProd() {
        return this.nomProd;
    }

    public Produit nomProd(String nomProd) {
        this.setNomProd(nomProd);
        return this;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public Float getPrixProd() {
        return this.prixProd;
    }

    public Produit prixProd(Float prixProd) {
        this.setPrixProd(prixProd);
        return this;
    }

    public void setPrixProd(Float prixProd) {
        this.prixProd = prixProd;
    }

    public Livraison getLivraison() {
        return this.livraison;
    }

    public void setLivraison(Livraison livraison) {
        this.livraison = livraison;
    }

    public Produit livraison(Livraison livraison) {
        this.setLivraison(livraison);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Produit client(Client client) {
        this.setClient(client);
        return this;
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Produit restaurant(Restaurant restaurant) {
        this.setRestaurant(restaurant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produit)) {
            return false;
        }
        return id != null && id.equals(((Produit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produit{" +
            "id=" + getId() +
            ", idProd=" + getIdProd() +
            ", nomProd='" + getNomProd() + "'" +
            ", prixProd=" + getPrixProd() +
            "}";
    }
}
