package gl.coopcycle.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Livraison.
 */
@Entity
@Table(name = "livraison")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Livraison implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_livraison", nullable = false, unique = true)
    private Integer idLivraison;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "prix_livraison", nullable = false)
    private Float prixLivraison;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @NotNull
    @Column(name = "adresse_livraison", nullable = false)
    private String adresseLivraison;

    @ManyToOne
    @JsonIgnoreProperties(value = { "produits", "livraisons" }, allowSetters = true)
    private Client client;

    @JsonIgnoreProperties(value = { "livraison", "client", "restaurant" }, allowSetters = true)
    @OneToOne(mappedBy = "livraison")
    private Produit produit;

    @ManyToOne
    @JsonIgnoreProperties(value = { "livraisons", "association" }, allowSetters = true)
    private Livreur livreur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Livraison id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdLivraison() {
        return this.idLivraison;
    }

    public Livraison idLivraison(Integer idLivraison) {
        this.setIdLivraison(idLivraison);
        return this;
    }

    public void setIdLivraison(Integer idLivraison) {
        this.idLivraison = idLivraison;
    }

    public Float getPrixLivraison() {
        return this.prixLivraison;
    }

    public Livraison prixLivraison(Float prixLivraison) {
        this.setPrixLivraison(prixLivraison);
        return this;
    }

    public void setPrixLivraison(Float prixLivraison) {
        this.prixLivraison = prixLivraison;
    }

    public Instant getDate() {
        return this.date;
    }

    public Livraison date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getAdresseLivraison() {
        return this.adresseLivraison;
    }

    public Livraison adresseLivraison(String adresseLivraison) {
        this.setAdresseLivraison(adresseLivraison);
        return this;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Livraison client(Client client) {
        this.setClient(client);
        return this;
    }

    public Produit getProduit() {
        return this.produit;
    }

    public void setProduit(Produit produit) {
        if (this.produit != null) {
            this.produit.setLivraison(null);
        }
        if (produit != null) {
            produit.setLivraison(this);
        }
        this.produit = produit;
    }

    public Livraison produit(Produit produit) {
        this.setProduit(produit);
        return this;
    }

    public Livreur getLivreur() {
        return this.livreur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

    public Livraison livreur(Livreur livreur) {
        this.setLivreur(livreur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Livraison)) {
            return false;
        }
        return id != null && id.equals(((Livraison) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Livraison{" +
            "id=" + getId() +
            ", idLivraison=" + getIdLivraison() +
            ", prixLivraison=" + getPrixLivraison() +
            ", date='" + getDate() + "'" +
            ", adresseLivraison='" + getAdresseLivraison() + "'" +
            "}";
    }
}
