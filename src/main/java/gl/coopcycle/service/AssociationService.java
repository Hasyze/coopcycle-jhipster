package gl.coopcycle.service;

import gl.coopcycle.domain.Association;
import gl.coopcycle.repository.AssociationRepository;
import gl.coopcycle.service.dto.AssociationDTO;
import gl.coopcycle.service.mapper.AssociationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Association}.
 */
@Service
@Transactional
public class AssociationService {

    private final Logger log = LoggerFactory.getLogger(AssociationService.class);

    private final AssociationRepository associationRepository;

    private final AssociationMapper associationMapper;

    public AssociationService(AssociationRepository associationRepository, AssociationMapper associationMapper) {
        this.associationRepository = associationRepository;
        this.associationMapper = associationMapper;
    }

    /**
     * Save a association.
     *
     * @param associationDTO the entity to save.
     * @return the persisted entity.
     */
    public AssociationDTO save(AssociationDTO associationDTO) {
        log.debug("Request to save Association : {}", associationDTO);
        Association association = associationMapper.toEntity(associationDTO);
        association = associationRepository.save(association);
        return associationMapper.toDto(association);
    }

    /**
     * Update a association.
     *
     * @param associationDTO the entity to save.
     * @return the persisted entity.
     */
    public AssociationDTO update(AssociationDTO associationDTO) {
        log.debug("Request to update Association : {}", associationDTO);
        Association association = associationMapper.toEntity(associationDTO);
        association = associationRepository.save(association);
        return associationMapper.toDto(association);
    }

    /**
     * Partially update a association.
     *
     * @param associationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AssociationDTO> partialUpdate(AssociationDTO associationDTO) {
        log.debug("Request to partially update Association : {}", associationDTO);

        return associationRepository
            .findById(associationDTO.getId())
            .map(existingAssociation -> {
                associationMapper.partialUpdate(existingAssociation, associationDTO);

                return existingAssociation;
            })
            .map(associationRepository::save)
            .map(associationMapper::toDto);
    }

    /**
     * Get all the associations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AssociationDTO> findAll() {
        log.debug("Request to get all Associations");
        return associationRepository.findAll().stream().map(associationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one association by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AssociationDTO> findOne(Long id) {
        log.debug("Request to get Association : {}", id);
        return associationRepository.findById(id).map(associationMapper::toDto);
    }

    /**
     * Delete the association by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Association : {}", id);
        associationRepository.deleteById(id);
    }
}
