package gl.coopcycle.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Restaurant.
 */
@Entity
@Table(name = "restaurant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_rest", nullable = false, unique = true)
    private Integer idRest;

    @NotNull
    @Column(name = "nom_rest", nullable = false)
    private String nomRest;

    @NotNull
    @Size(max = 50)
    @Column(name = "adresse_rest", length = 50, nullable = false)
    private String adresseRest;

    @OneToMany(mappedBy = "restaurant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "livraison", "client", "restaurant" }, allowSetters = true)
    private Set<Produit> produits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Restaurant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdRest() {
        return this.idRest;
    }

    public Restaurant idRest(Integer idRest) {
        this.setIdRest(idRest);
        return this;
    }

    public void setIdRest(Integer idRest) {
        this.idRest = idRest;
    }

    public String getNomRest() {
        return this.nomRest;
    }

    public Restaurant nomRest(String nomRest) {
        this.setNomRest(nomRest);
        return this;
    }

    public void setNomRest(String nomRest) {
        this.nomRest = nomRest;
    }

    public String getAdresseRest() {
        return this.adresseRest;
    }

    public Restaurant adresseRest(String adresseRest) {
        this.setAdresseRest(adresseRest);
        return this;
    }

    public void setAdresseRest(String adresseRest) {
        this.adresseRest = adresseRest;
    }

    public Set<Produit> getProduits() {
        return this.produits;
    }

    public void setProduits(Set<Produit> produits) {
        if (this.produits != null) {
            this.produits.forEach(i -> i.setRestaurant(null));
        }
        if (produits != null) {
            produits.forEach(i -> i.setRestaurant(this));
        }
        this.produits = produits;
    }

    public Restaurant produits(Set<Produit> produits) {
        this.setProduits(produits);
        return this;
    }

    public Restaurant addProduit(Produit produit) {
        this.produits.add(produit);
        produit.setRestaurant(this);
        return this;
    }

    public Restaurant removeProduit(Produit produit) {
        this.produits.remove(produit);
        produit.setRestaurant(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restaurant)) {
            return false;
        }
        return id != null && id.equals(((Restaurant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Restaurant{" +
            "id=" + getId() +
            ", idRest=" + getIdRest() +
            ", nomRest='" + getNomRest() + "'" +
            ", adresseRest='" + getAdresseRest() + "'" +
            "}";
    }
}
