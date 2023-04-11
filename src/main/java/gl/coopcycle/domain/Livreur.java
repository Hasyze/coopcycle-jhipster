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
 * A Livreur.
 */
@Entity
@Table(name = "livreur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Livreur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_livreur", nullable = false, unique = true)
    private Integer idLivreur;

    @NotNull
    @Size(min = 2)
    @Column(name = "nom_livreur", nullable = false)
    private String nomLivreur;

    @NotNull
    @Size(min = 2)
    @Column(name = "prenom_livreur", nullable = false)
    private String prenomLivreur;

    @NotNull
    @Pattern(regexp = "(\\+\\d+)?[0-9 ]+")
    @Column(name = "tel_livreur", nullable = false, unique = true)
    private String telLivreur;

    @OneToMany(mappedBy = "livreur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "client", "produit", "livreur" }, allowSetters = true)
    private Set<Livraison> livraisons = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "livreurs" }, allowSetters = true)
    private Association association;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Livreur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdLivreur() {
        return this.idLivreur;
    }

    public Livreur idLivreur(Integer idLivreur) {
        this.setIdLivreur(idLivreur);
        return this;
    }

    public void setIdLivreur(Integer idLivreur) {
        this.idLivreur = idLivreur;
    }

    public String getNomLivreur() {
        return this.nomLivreur;
    }

    public Livreur nomLivreur(String nomLivreur) {
        this.setNomLivreur(nomLivreur);
        return this;
    }

    public void setNomLivreur(String nomLivreur) {
        this.nomLivreur = nomLivreur;
    }

    public String getPrenomLivreur() {
        return this.prenomLivreur;
    }

    public Livreur prenomLivreur(String prenomLivreur) {
        this.setPrenomLivreur(prenomLivreur);
        return this;
    }

    public void setPrenomLivreur(String prenomLivreur) {
        this.prenomLivreur = prenomLivreur;
    }

    public String getTelLivreur() {
        return this.telLivreur;
    }

    public Livreur telLivreur(String telLivreur) {
        this.setTelLivreur(telLivreur);
        return this;
    }

    public void setTelLivreur(String telLivreur) {
        this.telLivreur = telLivreur;
    }

    public Set<Livraison> getLivraisons() {
        return this.livraisons;
    }

    public void setLivraisons(Set<Livraison> livraisons) {
        if (this.livraisons != null) {
            this.livraisons.forEach(i -> i.setLivreur(null));
        }
        if (livraisons != null) {
            livraisons.forEach(i -> i.setLivreur(this));
        }
        this.livraisons = livraisons;
    }

    public Livreur livraisons(Set<Livraison> livraisons) {
        this.setLivraisons(livraisons);
        return this;
    }

    public Livreur addLivraison(Livraison livraison) {
        this.livraisons.add(livraison);
        livraison.setLivreur(this);
        return this;
    }

    public Livreur removeLivraison(Livraison livraison) {
        this.livraisons.remove(livraison);
        livraison.setLivreur(null);
        return this;
    }

    public Association getAssociation() {
        return this.association;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }

    public Livreur association(Association association) {
        this.setAssociation(association);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Livreur)) {
            return false;
        }
        return id != null && id.equals(((Livreur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Livreur{" +
            "id=" + getId() +
            ", idLivreur=" + getIdLivreur() +
            ", nomLivreur='" + getNomLivreur() + "'" +
            ", prenomLivreur='" + getPrenomLivreur() + "'" +
            ", telLivreur='" + getTelLivreur() + "'" +
            "}";
    }
}
