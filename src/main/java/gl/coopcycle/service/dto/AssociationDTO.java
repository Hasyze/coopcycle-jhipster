package gl.coopcycle.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link gl.coopcycle.domain.Association} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssociationDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idAsso;

    @NotNull
    private String nomAsso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdAsso() {
        return idAsso;
    }

    public void setIdAsso(Integer idAsso) {
        this.idAsso = idAsso;
    }

    public String getNomAsso() {
        return nomAsso;
    }

    public void setNomAsso(String nomAsso) {
        this.nomAsso = nomAsso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssociationDTO)) {
            return false;
        }

        AssociationDTO associationDTO = (AssociationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, associationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssociationDTO{" +
            "id=" + getId() +
            ", idAsso=" + getIdAsso() +
            ", nomAsso='" + getNomAsso() + "'" +
            "}";
    }
}
