package gl.coopcycle.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gl.coopcycle.IntegrationTest;
import gl.coopcycle.domain.Restaurant;
import gl.coopcycle.repository.RestaurantRepository;
import gl.coopcycle.service.dto.RestaurantDTO;
import gl.coopcycle.service.mapper.RestaurantMapper;
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
 * Integration tests for the {@link RestaurantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RestaurantResourceIT {

    private static final Integer DEFAULT_ID_REST = 1;
    private static final Integer UPDATED_ID_REST = 2;

    private static final String DEFAULT_NOM_REST = "AAAAAAAAAA";
    private static final String UPDATED_NOM_REST = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_REST = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_REST = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/restaurants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRestaurantMockMvc;

    private Restaurant restaurant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Restaurant createEntity(EntityManager em) {
        Restaurant restaurant = new Restaurant().idRest(DEFAULT_ID_REST).nomRest(DEFAULT_NOM_REST).adresseRest(DEFAULT_ADRESSE_REST);
        return restaurant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Restaurant createUpdatedEntity(EntityManager em) {
        Restaurant restaurant = new Restaurant().idRest(UPDATED_ID_REST).nomRest(UPDATED_NOM_REST).adresseRest(UPDATED_ADRESSE_REST);
        return restaurant;
    }

    @BeforeEach
    public void initTest() {
        restaurant = createEntity(em);
    }

    @Test
    @Transactional
    void createRestaurant() throws Exception {
        int databaseSizeBeforeCreate = restaurantRepository.findAll().size();
        // Create the Restaurant
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);
        restRestaurantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isCreated());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeCreate + 1);
        Restaurant testRestaurant = restaurantList.get(restaurantList.size() - 1);
        assertThat(testRestaurant.getIdRest()).isEqualTo(DEFAULT_ID_REST);
        assertThat(testRestaurant.getNomRest()).isEqualTo(DEFAULT_NOM_REST);
        assertThat(testRestaurant.getAdresseRest()).isEqualTo(DEFAULT_ADRESSE_REST);
    }

    @Test
    @Transactional
    void createRestaurantWithExistingId() throws Exception {
        // Create the Restaurant with an existing ID
        restaurant.setId(1L);
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);

        int databaseSizeBeforeCreate = restaurantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestaurantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdRestIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setIdRest(null);

        // Create the Restaurant, which fails.
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);

        restRestaurantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomRestIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setNomRest(null);

        // Create the Restaurant, which fails.
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);

        restRestaurantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAdresseRestIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setAdresseRest(null);

        // Create the Restaurant, which fails.
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);

        restRestaurantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRestaurants() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList
        restRestaurantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restaurant.getId().intValue())))
            .andExpect(jsonPath("$.[*].idRest").value(hasItem(DEFAULT_ID_REST)))
            .andExpect(jsonPath("$.[*].nomRest").value(hasItem(DEFAULT_NOM_REST)))
            .andExpect(jsonPath("$.[*].adresseRest").value(hasItem(DEFAULT_ADRESSE_REST)));
    }

    @Test
    @Transactional
    void getRestaurant() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get the restaurant
        restRestaurantMockMvc
            .perform(get(ENTITY_API_URL_ID, restaurant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(restaurant.getId().intValue()))
            .andExpect(jsonPath("$.idRest").value(DEFAULT_ID_REST))
            .andExpect(jsonPath("$.nomRest").value(DEFAULT_NOM_REST))
            .andExpect(jsonPath("$.adresseRest").value(DEFAULT_ADRESSE_REST));
    }

    @Test
    @Transactional
    void getNonExistingRestaurant() throws Exception {
        // Get the restaurant
        restRestaurantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRestaurant() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        int databaseSizeBeforeUpdate = restaurantRepository.findAll().size();

        // Update the restaurant
        Restaurant updatedRestaurant = restaurantRepository.findById(restaurant.getId()).get();
        // Disconnect from session so that the updates on updatedRestaurant are not directly saved in db
        em.detach(updatedRestaurant);
        updatedRestaurant.idRest(UPDATED_ID_REST).nomRest(UPDATED_NOM_REST).adresseRest(UPDATED_ADRESSE_REST);
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(updatedRestaurant);

        restRestaurantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, restaurantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(restaurantDTO))
            )
            .andExpect(status().isOk());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeUpdate);
        Restaurant testRestaurant = restaurantList.get(restaurantList.size() - 1);
        assertThat(testRestaurant.getIdRest()).isEqualTo(UPDATED_ID_REST);
        assertThat(testRestaurant.getNomRest()).isEqualTo(UPDATED_NOM_REST);
        assertThat(testRestaurant.getAdresseRest()).isEqualTo(UPDATED_ADRESSE_REST);
    }

    @Test
    @Transactional
    void putNonExistingRestaurant() throws Exception {
        int databaseSizeBeforeUpdate = restaurantRepository.findAll().size();
        restaurant.setId(count.incrementAndGet());

        // Create the Restaurant
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestaurantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, restaurantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(restaurantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRestaurant() throws Exception {
        int databaseSizeBeforeUpdate = restaurantRepository.findAll().size();
        restaurant.setId(count.incrementAndGet());

        // Create the Restaurant
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestaurantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(restaurantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRestaurant() throws Exception {
        int databaseSizeBeforeUpdate = restaurantRepository.findAll().size();
        restaurant.setId(count.incrementAndGet());

        // Create the Restaurant
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestaurantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRestaurantWithPatch() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        int databaseSizeBeforeUpdate = restaurantRepository.findAll().size();

        // Update the restaurant using partial update
        Restaurant partialUpdatedRestaurant = new Restaurant();
        partialUpdatedRestaurant.setId(restaurant.getId());

        restRestaurantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRestaurant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRestaurant))
            )
            .andExpect(status().isOk());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeUpdate);
        Restaurant testRestaurant = restaurantList.get(restaurantList.size() - 1);
        assertThat(testRestaurant.getIdRest()).isEqualTo(DEFAULT_ID_REST);
        assertThat(testRestaurant.getNomRest()).isEqualTo(DEFAULT_NOM_REST);
        assertThat(testRestaurant.getAdresseRest()).isEqualTo(DEFAULT_ADRESSE_REST);
    }

    @Test
    @Transactional
    void fullUpdateRestaurantWithPatch() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        int databaseSizeBeforeUpdate = restaurantRepository.findAll().size();

        // Update the restaurant using partial update
        Restaurant partialUpdatedRestaurant = new Restaurant();
        partialUpdatedRestaurant.setId(restaurant.getId());

        partialUpdatedRestaurant.idRest(UPDATED_ID_REST).nomRest(UPDATED_NOM_REST).adresseRest(UPDATED_ADRESSE_REST);

        restRestaurantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRestaurant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRestaurant))
            )
            .andExpect(status().isOk());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeUpdate);
        Restaurant testRestaurant = restaurantList.get(restaurantList.size() - 1);
        assertThat(testRestaurant.getIdRest()).isEqualTo(UPDATED_ID_REST);
        assertThat(testRestaurant.getNomRest()).isEqualTo(UPDATED_NOM_REST);
        assertThat(testRestaurant.getAdresseRest()).isEqualTo(UPDATED_ADRESSE_REST);
    }

    @Test
    @Transactional
    void patchNonExistingRestaurant() throws Exception {
        int databaseSizeBeforeUpdate = restaurantRepository.findAll().size();
        restaurant.setId(count.incrementAndGet());

        // Create the Restaurant
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestaurantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, restaurantDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(restaurantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRestaurant() throws Exception {
        int databaseSizeBeforeUpdate = restaurantRepository.findAll().size();
        restaurant.setId(count.incrementAndGet());

        // Create the Restaurant
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestaurantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(restaurantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRestaurant() throws Exception {
        int databaseSizeBeforeUpdate = restaurantRepository.findAll().size();
        restaurant.setId(count.incrementAndGet());

        // Create the Restaurant
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestaurantMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(restaurantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRestaurant() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        int databaseSizeBeforeDelete = restaurantRepository.findAll().size();

        // Delete the restaurant
        restRestaurantMockMvc
            .perform(delete(ENTITY_API_URL_ID, restaurant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
