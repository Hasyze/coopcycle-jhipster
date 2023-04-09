package gl.coopcycle.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gl.coopcycle.IntegrationTest;
import gl.coopcycle.domain.Livraison;
import gl.coopcycle.repository.LivraisonRepository;
import gl.coopcycle.service.dto.LivraisonDTO;
import gl.coopcycle.service.mapper.LivraisonMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LivraisonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LivraisonResourceIT {

    private static final Integer DEFAULT_ID_LIVRAISON = 1;
    private static final Integer UPDATED_ID_LIVRAISON = 2;

    private static final Float DEFAULT_PRIX_LIVRAISON = 0F;
    private static final Float UPDATED_PRIX_LIVRAISON = 1F;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ADRESSE_LIVRAISON = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_LIVRAISON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/livraisons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LivraisonRepository livraisonRepository;

    @Autowired
    private LivraisonMapper livraisonMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLivraisonMockMvc;

    private Livraison livraison;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livraison createEntity(EntityManager em) {
        Livraison livraison = new Livraison()
            .idLivraison(DEFAULT_ID_LIVRAISON)
            .prixLivraison(DEFAULT_PRIX_LIVRAISON)
            .date(DEFAULT_DATE)
            .adresseLivraison(DEFAULT_ADRESSE_LIVRAISON);
        return livraison;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livraison createUpdatedEntity(EntityManager em) {
        Livraison livraison = new Livraison()
            .idLivraison(UPDATED_ID_LIVRAISON)
            .prixLivraison(UPDATED_PRIX_LIVRAISON)
            .date(UPDATED_DATE)
            .adresseLivraison(UPDATED_ADRESSE_LIVRAISON);
        return livraison;
    }

    @BeforeEach
    public void initTest() {
        livraison = createEntity(em);
    }

    @Test
    @Transactional
    void createLivraison() throws Exception {
        int databaseSizeBeforeCreate = livraisonRepository.findAll().size();
        // Create the Livraison
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);
        restLivraisonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livraisonDTO)))
            .andExpect(status().isCreated());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeCreate + 1);
        Livraison testLivraison = livraisonList.get(livraisonList.size() - 1);
        assertThat(testLivraison.getIdLivraison()).isEqualTo(DEFAULT_ID_LIVRAISON);
        assertThat(testLivraison.getPrixLivraison()).isEqualTo(DEFAULT_PRIX_LIVRAISON);
        assertThat(testLivraison.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testLivraison.getAdresseLivraison()).isEqualTo(DEFAULT_ADRESSE_LIVRAISON);
    }

    @Test
    @Transactional
    void createLivraisonWithExistingId() throws Exception {
        // Create the Livraison with an existing ID
        livraison.setId(1L);
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);

        int databaseSizeBeforeCreate = livraisonRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLivraisonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livraisonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdLivraisonIsRequired() throws Exception {
        int databaseSizeBeforeTest = livraisonRepository.findAll().size();
        // set the field null
        livraison.setIdLivraison(null);

        // Create the Livraison, which fails.
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);

        restLivraisonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livraisonDTO)))
            .andExpect(status().isBadRequest());

        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrixLivraisonIsRequired() throws Exception {
        int databaseSizeBeforeTest = livraisonRepository.findAll().size();
        // set the field null
        livraison.setPrixLivraison(null);

        // Create the Livraison, which fails.
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);

        restLivraisonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livraisonDTO)))
            .andExpect(status().isBadRequest());

        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = livraisonRepository.findAll().size();
        // set the field null
        livraison.setDate(null);

        // Create the Livraison, which fails.
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);

        restLivraisonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livraisonDTO)))
            .andExpect(status().isBadRequest());

        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAdresseLivraisonIsRequired() throws Exception {
        int databaseSizeBeforeTest = livraisonRepository.findAll().size();
        // set the field null
        livraison.setAdresseLivraison(null);

        // Create the Livraison, which fails.
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);

        restLivraisonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livraisonDTO)))
            .andExpect(status().isBadRequest());

        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLivraisons() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList
        restLivraisonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livraison.getId().intValue())))
            .andExpect(jsonPath("$.[*].idLivraison").value(hasItem(DEFAULT_ID_LIVRAISON)))
            .andExpect(jsonPath("$.[*].prixLivraison").value(hasItem(DEFAULT_PRIX_LIVRAISON.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].adresseLivraison").value(hasItem(DEFAULT_ADRESSE_LIVRAISON)));
    }

    @Test
    @Transactional
    void getLivraison() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get the livraison
        restLivraisonMockMvc
            .perform(get(ENTITY_API_URL_ID, livraison.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(livraison.getId().intValue()))
            .andExpect(jsonPath("$.idLivraison").value(DEFAULT_ID_LIVRAISON))
            .andExpect(jsonPath("$.prixLivraison").value(DEFAULT_PRIX_LIVRAISON.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.adresseLivraison").value(DEFAULT_ADRESSE_LIVRAISON));
    }

    @Test
    @Transactional
    void getNonExistingLivraison() throws Exception {
        // Get the livraison
        restLivraisonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLivraison() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        int databaseSizeBeforeUpdate = livraisonRepository.findAll().size();

        // Update the livraison
        Livraison updatedLivraison = livraisonRepository.findById(livraison.getId()).get();
        // Disconnect from session so that the updates on updatedLivraison are not directly saved in db
        em.detach(updatedLivraison);
        updatedLivraison
            .idLivraison(UPDATED_ID_LIVRAISON)
            .prixLivraison(UPDATED_PRIX_LIVRAISON)
            .date(UPDATED_DATE)
            .adresseLivraison(UPDATED_ADRESSE_LIVRAISON);
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(updatedLivraison);

        restLivraisonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, livraisonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(livraisonDTO))
            )
            .andExpect(status().isOk());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeUpdate);
        Livraison testLivraison = livraisonList.get(livraisonList.size() - 1);
        assertThat(testLivraison.getIdLivraison()).isEqualTo(UPDATED_ID_LIVRAISON);
        assertThat(testLivraison.getPrixLivraison()).isEqualTo(UPDATED_PRIX_LIVRAISON);
        assertThat(testLivraison.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testLivraison.getAdresseLivraison()).isEqualTo(UPDATED_ADRESSE_LIVRAISON);
    }

    @Test
    @Transactional
    void putNonExistingLivraison() throws Exception {
        int databaseSizeBeforeUpdate = livraisonRepository.findAll().size();
        livraison.setId(count.incrementAndGet());

        // Create the Livraison
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivraisonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, livraisonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(livraisonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLivraison() throws Exception {
        int databaseSizeBeforeUpdate = livraisonRepository.findAll().size();
        livraison.setId(count.incrementAndGet());

        // Create the Livraison
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivraisonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(livraisonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLivraison() throws Exception {
        int databaseSizeBeforeUpdate = livraisonRepository.findAll().size();
        livraison.setId(count.incrementAndGet());

        // Create the Livraison
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivraisonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(livraisonDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLivraisonWithPatch() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        int databaseSizeBeforeUpdate = livraisonRepository.findAll().size();

        // Update the livraison using partial update
        Livraison partialUpdatedLivraison = new Livraison();
        partialUpdatedLivraison.setId(livraison.getId());

        partialUpdatedLivraison.date(UPDATED_DATE).adresseLivraison(UPDATED_ADRESSE_LIVRAISON);

        restLivraisonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLivraison.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLivraison))
            )
            .andExpect(status().isOk());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeUpdate);
        Livraison testLivraison = livraisonList.get(livraisonList.size() - 1);
        assertThat(testLivraison.getIdLivraison()).isEqualTo(DEFAULT_ID_LIVRAISON);
        assertThat(testLivraison.getPrixLivraison()).isEqualTo(DEFAULT_PRIX_LIVRAISON);
        assertThat(testLivraison.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testLivraison.getAdresseLivraison()).isEqualTo(UPDATED_ADRESSE_LIVRAISON);
    }

    @Test
    @Transactional
    void fullUpdateLivraisonWithPatch() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        int databaseSizeBeforeUpdate = livraisonRepository.findAll().size();

        // Update the livraison using partial update
        Livraison partialUpdatedLivraison = new Livraison();
        partialUpdatedLivraison.setId(livraison.getId());

        partialUpdatedLivraison
            .idLivraison(UPDATED_ID_LIVRAISON)
            .prixLivraison(UPDATED_PRIX_LIVRAISON)
            .date(UPDATED_DATE)
            .adresseLivraison(UPDATED_ADRESSE_LIVRAISON);

        restLivraisonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLivraison.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLivraison))
            )
            .andExpect(status().isOk());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeUpdate);
        Livraison testLivraison = livraisonList.get(livraisonList.size() - 1);
        assertThat(testLivraison.getIdLivraison()).isEqualTo(UPDATED_ID_LIVRAISON);
        assertThat(testLivraison.getPrixLivraison()).isEqualTo(UPDATED_PRIX_LIVRAISON);
        assertThat(testLivraison.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testLivraison.getAdresseLivraison()).isEqualTo(UPDATED_ADRESSE_LIVRAISON);
    }

    @Test
    @Transactional
    void patchNonExistingLivraison() throws Exception {
        int databaseSizeBeforeUpdate = livraisonRepository.findAll().size();
        livraison.setId(count.incrementAndGet());

        // Create the Livraison
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivraisonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, livraisonDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(livraisonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLivraison() throws Exception {
        int databaseSizeBeforeUpdate = livraisonRepository.findAll().size();
        livraison.setId(count.incrementAndGet());

        // Create the Livraison
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivraisonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(livraisonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLivraison() throws Exception {
        int databaseSizeBeforeUpdate = livraisonRepository.findAll().size();
        livraison.setId(count.incrementAndGet());

        // Create the Livraison
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLivraisonMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(livraisonDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLivraison() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        int databaseSizeBeforeDelete = livraisonRepository.findAll().size();

        // Delete the livraison
        restLivraisonMockMvc
            .perform(delete(ENTITY_API_URL_ID, livraison.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
