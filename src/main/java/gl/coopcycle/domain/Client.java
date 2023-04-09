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
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_client", nullable = false, unique = true)
    private Integer idClient;

    @NotNull
    @Size(min = 2)
    @Column(name = "prenom_client", nullable = false)
    private String prenomClient;

    @NotNull
    @Size(min = 2)
    @Column(name = "nom_client", nullable = false)
    private String nomClient;

    @NotNull
    @Column(name = "adresse_client", nullable = false)
    private String adresseClient;

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9.]+@[a-zA-Z0-9.]+.[a-z]+")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(min = 10, max = 10)
    @Pattern(regexp = "[0-9]")
    @Column(name = "tel_c_lient", length = 10, nullable = false, unique = true)
    private String telCLient;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "livraison", "client", "restaurant" }, allowSetters = true)
    private Set<Produit> produits = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "client", "produit", "livreur" }, allowSetters = true)
    private Set<Livraison> livraisons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdClient() {
        return this.idClient;
    }

    public Client idClient(Integer idClient) {
        this.setIdClient(idClient);
        return this;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getPrenomClient() {
        return this.prenomClient;
    }

    public Client prenomClient(String prenomClient) {
        this.setPrenomClient(prenomClient);
        return this;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }

    public String getNomClient() {
        return this.nomClient;
    }

    public Client nomClient(String nomClient) {
        this.setNomClient(nomClient);
        return this;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getAdresseClient() {
        return this.adresseClient;
    }

    public Client adresseClient(String adresseClient) {
        this.setAdresseClient(adresseClient);
        return this;
    }

    public void setAdresseClient(String adresseClient) {
        this.adresseClient = adresseClient;
    }

    public String getEmail() {
        return this.email;
    }

    public Client email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelCLient() {
        return this.telCLient;
    }

    public Client telCLient(String telCLient) {
        this.setTelCLient(telCLient);
        return this;
    }

    public void setTelCLient(String telCLient) {
        this.telCLient = telCLient;
    }

    public Set<Produit> getProduits() {
        return this.produits;
    }

    public void setProduits(Set<Produit> produits) {
        if (this.produits != null) {
            this.produits.forEach(i -> i.setClient(null));
        }
        if (produits != null) {
            produits.forEach(i -> i.setClient(this));
        }
        this.produits = produits;
    }

    public Client produits(Set<Produit> produits) {
        this.setProduits(produits);
        return this;
    }

    public Client addProduit(Produit produit) {
        this.produits.add(produit);
        produit.setClient(this);
        return this;
    }

    public Client removeProduit(Produit produit) {
        this.produits.remove(produit);
        produit.setClient(null);
        return this;
    }

    public Set<Livraison> getLivraisons() {
        return this.livraisons;
    }

    public void setLivraisons(Set<Livraison> livraisons) {
        if (this.livraisons != null) {
            this.livraisons.forEach(i -> i.setClient(null));
        }
        if (livraisons != null) {
            livraisons.forEach(i -> i.setClient(this));
        }
        this.livraisons = livraisons;
    }

    public Client livraisons(Set<Livraison> livraisons) {
        this.setLivraisons(livraisons);
        return this;
    }

    public Client addLivraison(Livraison livraison) {
        this.livraisons.add(livraison);
        livraison.setClient(this);
        return this;
    }

    public Client removeLivraison(Livraison livraison) {
        this.livraisons.remove(livraison);
        livraison.setClient(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", idClient=" + getIdClient() +
            ", prenomClient='" + getPrenomClient() + "'" +
            ", nomClient='" + getNomClient() + "'" +
            ", adresseClient='" + getAdresseClient() + "'" +
            ", email='" + getEmail() + "'" +
            ", telCLient='" + getTelCLient() + "'" +
            "}";
    }
}
