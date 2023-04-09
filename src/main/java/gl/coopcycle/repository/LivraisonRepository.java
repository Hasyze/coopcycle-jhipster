package gl.coopcycle.repository;

import gl.coopcycle.domain.Livraison;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Livraison entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LivraisonRepository extends JpaRepository<Livraison, Long> {}
