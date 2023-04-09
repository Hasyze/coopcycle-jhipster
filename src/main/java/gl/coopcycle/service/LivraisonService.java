package gl.coopcycle.service;

import gl.coopcycle.domain.Livraison;
import gl.coopcycle.repository.LivraisonRepository;
import gl.coopcycle.service.dto.LivraisonDTO;
import gl.coopcycle.service.mapper.LivraisonMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Livraison}.
 */
@Service
@Transactional
public class LivraisonService {

    private final Logger log = LoggerFactory.getLogger(LivraisonService.class);

    private final LivraisonRepository livraisonRepository;

    private final LivraisonMapper livraisonMapper;

    public LivraisonService(LivraisonRepository livraisonRepository, LivraisonMapper livraisonMapper) {
        this.livraisonRepository = livraisonRepository;
        this.livraisonMapper = livraisonMapper;
    }

    /**
     * Save a livraison.
     *
     * @param livraisonDTO the entity to save.
     * @return the persisted entity.
     */
    public LivraisonDTO save(LivraisonDTO livraisonDTO) {
        log.debug("Request to save Livraison : {}", livraisonDTO);
        Livraison livraison = livraisonMapper.toEntity(livraisonDTO);
        livraison = livraisonRepository.save(livraison);
        return livraisonMapper.toDto(livraison);
    }

    /**
     * Update a livraison.
     *
     * @param livraisonDTO the entity to save.
     * @return the persisted entity.
     */
    public LivraisonDTO update(LivraisonDTO livraisonDTO) {
        log.debug("Request to update Livraison : {}", livraisonDTO);
        Livraison livraison = livraisonMapper.toEntity(livraisonDTO);
        livraison = livraisonRepository.save(livraison);
        return livraisonMapper.toDto(livraison);
    }

    /**
     * Partially update a livraison.
     *
     * @param livraisonDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LivraisonDTO> partialUpdate(LivraisonDTO livraisonDTO) {
        log.debug("Request to partially update Livraison : {}", livraisonDTO);

        return livraisonRepository
            .findById(livraisonDTO.getId())
            .map(existingLivraison -> {
                livraisonMapper.partialUpdate(existingLivraison, livraisonDTO);

                return existingLivraison;
            })
            .map(livraisonRepository::save)
            .map(livraisonMapper::toDto);
    }

    /**
     * Get all the livraisons.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LivraisonDTO> findAll() {
        log.debug("Request to get all Livraisons");
        return livraisonRepository.findAll().stream().map(livraisonMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the livraisons where Produit is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LivraisonDTO> findAllWhereProduitIsNull() {
        log.debug("Request to get all livraisons where Produit is null");
        return StreamSupport
            .stream(livraisonRepository.findAll().spliterator(), false)
            .filter(livraison -> livraison.getProduit() == null)
            .map(livraisonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one livraison by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LivraisonDTO> findOne(Long id) {
        log.debug("Request to get Livraison : {}", id);
        return livraisonRepository.findById(id).map(livraisonMapper::toDto);
    }

    /**
     * Delete the livraison by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Livraison : {}", id);
        livraisonRepository.deleteById(id);
    }
}
