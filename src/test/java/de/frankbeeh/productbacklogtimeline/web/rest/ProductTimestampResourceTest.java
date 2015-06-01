package de.frankbeeh.productbacklogtimeline.web.rest;

import de.frankbeeh.productbacklogtimeline.Application;
import de.frankbeeh.productbacklogtimeline.domain.ProductTimestamp;
import de.frankbeeh.productbacklogtimeline.repository.ProductTimestampRepository;

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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductTimestampResource REST controller.
 *
 * @see ProductTimestampResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProductTimestampResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final DateTime DEFAULT_DATE_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_TIME_STR = dateTimeFormatter.print(DEFAULT_DATE_TIME);
    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    @Inject
    private ProductTimestampRepository productTimestampRepository;

    private MockMvc restProductTimestampMockMvc;

    private ProductTimestamp productTimestamp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductTimestampResource productTimestampResource = new ProductTimestampResource();
        ReflectionTestUtils.setField(productTimestampResource, "productTimestampRepository", productTimestampRepository);
        this.restProductTimestampMockMvc = MockMvcBuilders.standaloneSetup(productTimestampResource).build();
    }

    @Before
    public void initTest() {
        productTimestamp = new ProductTimestamp();
        productTimestamp.setDateTime(DEFAULT_DATE_TIME);
        productTimestamp.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProductTimestamp() throws Exception {
        int databaseSizeBeforeCreate = productTimestampRepository.findAll().size();

        // Create the ProductTimestamp
        restProductTimestampMockMvc.perform(post("/api/productTimestamps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productTimestamp)))
                .andExpect(status().isCreated());

        // Validate the ProductTimestamp in the database
        List<ProductTimestamp> productTimestamps = productTimestampRepository.findAll();
        assertThat(productTimestamps).hasSize(databaseSizeBeforeCreate + 1);
        ProductTimestamp testProductTimestamp = productTimestamps.get(productTimestamps.size() - 1);
        assertThat(testProductTimestamp.getDateTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testProductTimestamp.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkDateTimeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(productTimestampRepository.findAll()).hasSize(0);
        // set the field null
        productTimestamp.setDateTime(null);

        // Create the ProductTimestamp, which fails.
        restProductTimestampMockMvc.perform(post("/api/productTimestamps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productTimestamp)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<ProductTimestamp> productTimestamps = productTimestampRepository.findAll();
        assertThat(productTimestamps).hasSize(0);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(productTimestampRepository.findAll()).hasSize(0);
        // set the field null
        productTimestamp.setName(null);

        // Create the ProductTimestamp, which fails.
        restProductTimestampMockMvc.perform(post("/api/productTimestamps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productTimestamp)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<ProductTimestamp> productTimestamps = productTimestampRepository.findAll();
        assertThat(productTimestamps).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllProductTimestamps() throws Exception {
        // Initialize the database
        productTimestampRepository.saveAndFlush(productTimestamp);

        // Get all the productTimestamps
        restProductTimestampMockMvc.perform(get("/api/productTimestamps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(productTimestamp.getId().intValue())))
                .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DEFAULT_DATE_TIME_STR)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getProductTimestamp() throws Exception {
        // Initialize the database
        productTimestampRepository.saveAndFlush(productTimestamp);

        // Get the productTimestamp
        restProductTimestampMockMvc.perform(get("/api/productTimestamps/{id}", productTimestamp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(productTimestamp.getId().intValue()))
            .andExpect(jsonPath("$.dateTime").value(DEFAULT_DATE_TIME_STR))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductTimestamp() throws Exception {
        // Get the productTimestamp
        restProductTimestampMockMvc.perform(get("/api/productTimestamps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductTimestamp() throws Exception {
        // Initialize the database
        productTimestampRepository.saveAndFlush(productTimestamp);

		int databaseSizeBeforeUpdate = productTimestampRepository.findAll().size();

        // Update the productTimestamp
        productTimestamp.setDateTime(UPDATED_DATE_TIME);
        productTimestamp.setName(UPDATED_NAME);
        restProductTimestampMockMvc.perform(put("/api/productTimestamps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productTimestamp)))
                .andExpect(status().isOk());

        // Validate the ProductTimestamp in the database
        List<ProductTimestamp> productTimestamps = productTimestampRepository.findAll();
        assertThat(productTimestamps).hasSize(databaseSizeBeforeUpdate);
        ProductTimestamp testProductTimestamp = productTimestamps.get(productTimestamps.size() - 1);
        assertThat(testProductTimestamp.getDateTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testProductTimestamp.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteProductTimestamp() throws Exception {
        // Initialize the database
        productTimestampRepository.saveAndFlush(productTimestamp);

		int databaseSizeBeforeDelete = productTimestampRepository.findAll().size();

        // Get the productTimestamp
        restProductTimestampMockMvc.perform(delete("/api/productTimestamps/{id}", productTimestamp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductTimestamp> productTimestamps = productTimestampRepository.findAll();
        assertThat(productTimestamps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
