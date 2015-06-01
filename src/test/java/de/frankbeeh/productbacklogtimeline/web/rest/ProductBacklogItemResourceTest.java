package de.frankbeeh.productbacklogtimeline.web.rest;

import de.frankbeeh.productbacklogtimeline.Application;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.repository.ProductBacklogItemRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductBacklogItemResource REST controller.
 *
 * @see ProductBacklogItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProductBacklogItemResourceTest {

    private static final String DEFAULT_PBI_KEY = "SAMPLE_TEXT";
    private static final String UPDATED_PBI_KEY = "UPDATED_TEXT";
    private static final String DEFAULT_TITLE = "SAMPLE_TEXT";
    private static final String UPDATED_TITLE = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_ESTIMATE = new BigDecimal(0);
    private static final BigDecimal UPDATED_ESTIMATE = new BigDecimal(1);
    private static final String DEFAULT_STATE = "SAMPLE_TEXT";
    private static final String UPDATED_STATE = "UPDATED_TEXT";

    @Inject
    private ProductBacklogItemRepository productBacklogItemRepository;

    private MockMvc restProductBacklogItemMockMvc;

    private ProductBacklogItem productBacklogItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductBacklogItemResource productBacklogItemResource = new ProductBacklogItemResource();
        ReflectionTestUtils.setField(productBacklogItemResource, "productBacklogItemRepository", productBacklogItemRepository);
        this.restProductBacklogItemMockMvc = MockMvcBuilders.standaloneSetup(productBacklogItemResource).build();
    }

    @Before
    public void initTest() {
        productBacklogItem = new ProductBacklogItem();
        productBacklogItem.setPbiKey(DEFAULT_PBI_KEY);
        productBacklogItem.setTitle(DEFAULT_TITLE);
        productBacklogItem.setDescription(DEFAULT_DESCRIPTION);
        productBacklogItem.setEstimate(DEFAULT_ESTIMATE);
        productBacklogItem.setState(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createProductBacklogItem() throws Exception {
        int databaseSizeBeforeCreate = productBacklogItemRepository.findAll().size();

        // Create the ProductBacklogItem
        restProductBacklogItemMockMvc.perform(post("/api/productBacklogItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productBacklogItem)))
                .andExpect(status().isCreated());

        // Validate the ProductBacklogItem in the database
        List<ProductBacklogItem> productBacklogItems = productBacklogItemRepository.findAll();
        assertThat(productBacklogItems).hasSize(databaseSizeBeforeCreate + 1);
        ProductBacklogItem testProductBacklogItem = productBacklogItems.get(productBacklogItems.size() - 1);
        assertThat(testProductBacklogItem.getPbiKey()).isEqualTo(DEFAULT_PBI_KEY);
        assertThat(testProductBacklogItem.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProductBacklogItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductBacklogItem.getEstimate()).isEqualTo(DEFAULT_ESTIMATE);
        assertThat(testProductBacklogItem.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void checkPbiKeyIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(productBacklogItemRepository.findAll()).hasSize(0);
        // set the field null
        productBacklogItem.setPbiKey(null);

        // Create the ProductBacklogItem, which fails.
        restProductBacklogItemMockMvc.perform(post("/api/productBacklogItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productBacklogItem)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<ProductBacklogItem> productBacklogItems = productBacklogItemRepository.findAll();
        assertThat(productBacklogItems).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllProductBacklogItems() throws Exception {
        // Initialize the database
        productBacklogItemRepository.saveAndFlush(productBacklogItem);

        // Get all the productBacklogItems
        restProductBacklogItemMockMvc.perform(get("/api/productBacklogItems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(productBacklogItem.getId().intValue())))
                .andExpect(jsonPath("$.[*].pbiKey").value(hasItem(DEFAULT_PBI_KEY.toString())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].estimate").value(hasItem(DEFAULT_ESTIMATE.intValue())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    public void getProductBacklogItem() throws Exception {
        // Initialize the database
        productBacklogItemRepository.saveAndFlush(productBacklogItem);

        // Get the productBacklogItem
        restProductBacklogItemMockMvc.perform(get("/api/productBacklogItems/{id}", productBacklogItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(productBacklogItem.getId().intValue()))
            .andExpect(jsonPath("$.pbiKey").value(DEFAULT_PBI_KEY.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.estimate").value(DEFAULT_ESTIMATE.intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductBacklogItem() throws Exception {
        // Get the productBacklogItem
        restProductBacklogItemMockMvc.perform(get("/api/productBacklogItems/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductBacklogItem() throws Exception {
        // Initialize the database
        productBacklogItemRepository.saveAndFlush(productBacklogItem);

		int databaseSizeBeforeUpdate = productBacklogItemRepository.findAll().size();

        // Update the productBacklogItem
        productBacklogItem.setPbiKey(UPDATED_PBI_KEY);
        productBacklogItem.setTitle(UPDATED_TITLE);
        productBacklogItem.setDescription(UPDATED_DESCRIPTION);
        productBacklogItem.setEstimate(UPDATED_ESTIMATE);
        productBacklogItem.setState(UPDATED_STATE);
        restProductBacklogItemMockMvc.perform(put("/api/productBacklogItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productBacklogItem)))
                .andExpect(status().isOk());

        // Validate the ProductBacklogItem in the database
        List<ProductBacklogItem> productBacklogItems = productBacklogItemRepository.findAll();
        assertThat(productBacklogItems).hasSize(databaseSizeBeforeUpdate);
        ProductBacklogItem testProductBacklogItem = productBacklogItems.get(productBacklogItems.size() - 1);
        assertThat(testProductBacklogItem.getPbiKey()).isEqualTo(UPDATED_PBI_KEY);
        assertThat(testProductBacklogItem.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProductBacklogItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductBacklogItem.getEstimate()).isEqualTo(UPDATED_ESTIMATE);
        assertThat(testProductBacklogItem.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void deleteProductBacklogItem() throws Exception {
        // Initialize the database
        productBacklogItemRepository.saveAndFlush(productBacklogItem);

		int databaseSizeBeforeDelete = productBacklogItemRepository.findAll().size();

        // Get the productBacklogItem
        restProductBacklogItemMockMvc.perform(delete("/api/productBacklogItems/{id}", productBacklogItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductBacklogItem> productBacklogItems = productBacklogItemRepository.findAll();
        assertThat(productBacklogItems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
