package de.frankbeeh.productbacklogtimeline.web.rest;

import de.frankbeeh.productbacklogtimeline.Application;
import de.frankbeeh.productbacklogtimeline.domain.Sprint;
import de.frankbeeh.productbacklogtimeline.repository.SprintRepository;

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
import org.joda.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SprintResource REST controller.
 *
 * @see SprintResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SprintResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    private static final LocalDate DEFAULT_START_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_START_DATE = new LocalDate();

    private static final LocalDate DEFAULT_END_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_END_DATE = new LocalDate();

    private static final BigDecimal DEFAULT_CAPACITY_FORECAST = new BigDecimal(0);
    private static final BigDecimal UPDATED_CAPACITY_FORECAST = new BigDecimal(1);

    private static final BigDecimal DEFAULT_EFFORT_FORECAST = new BigDecimal(0);
    private static final BigDecimal UPDATED_EFFORT_FORECAST = new BigDecimal(1);

    private static final BigDecimal DEFAULT_CAPACITY_DONE = new BigDecimal(0);
    private static final BigDecimal UPDATED_CAPACITY_DONE = new BigDecimal(1);

    private static final BigDecimal DEFAULT_EFFORT_DONE = new BigDecimal(0);
    private static final BigDecimal UPDATED_EFFORT_DONE = new BigDecimal(1);

    @Inject
    private SprintRepository sprintRepository;

    private MockMvc restSprintMockMvc;

    private Sprint sprint;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SprintResource sprintResource = new SprintResource();
        ReflectionTestUtils.setField(sprintResource, "sprintRepository", sprintRepository);
        this.restSprintMockMvc = MockMvcBuilders.standaloneSetup(sprintResource).build();
    }

    @Before
    public void initTest() {
        sprint = new Sprint();
        sprint.setName(DEFAULT_NAME);
        sprint.setStartDate(DEFAULT_START_DATE);
        sprint.setEndDate(DEFAULT_END_DATE);
        sprint.setCapacityForecast(DEFAULT_CAPACITY_FORECAST);
        sprint.setEffortForecast(DEFAULT_EFFORT_FORECAST);
        sprint.setCapacityDone(DEFAULT_CAPACITY_DONE);
        sprint.setEffortDone(DEFAULT_EFFORT_DONE);
    }

    @Test
    @Transactional
    public void createSprint() throws Exception {
        int databaseSizeBeforeCreate = sprintRepository.findAll().size();

        // Create the Sprint
        restSprintMockMvc.perform(post("/api/sprints")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sprint)))
                .andExpect(status().isCreated());

        // Validate the Sprint in the database
        List<Sprint> sprints = sprintRepository.findAll();
        assertThat(sprints).hasSize(databaseSizeBeforeCreate + 1);
        Sprint testSprint = sprints.get(sprints.size() - 1);
        assertThat(testSprint.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSprint.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSprint.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testSprint.getCapacityForecast()).isEqualTo(DEFAULT_CAPACITY_FORECAST);
        assertThat(testSprint.getEffortForecast()).isEqualTo(DEFAULT_EFFORT_FORECAST);
        assertThat(testSprint.getCapacityDone()).isEqualTo(DEFAULT_CAPACITY_DONE);
        assertThat(testSprint.getEffortDone()).isEqualTo(DEFAULT_EFFORT_DONE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(sprintRepository.findAll()).hasSize(0);
        // set the field null
        sprint.setName(null);

        // Create the Sprint, which fails.
        restSprintMockMvc.perform(post("/api/sprints")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sprint)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Sprint> sprints = sprintRepository.findAll();
        assertThat(sprints).hasSize(0);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(sprintRepository.findAll()).hasSize(0);
        // set the field null
        sprint.setStartDate(null);

        // Create the Sprint, which fails.
        restSprintMockMvc.perform(post("/api/sprints")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sprint)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Sprint> sprints = sprintRepository.findAll();
        assertThat(sprints).hasSize(0);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(sprintRepository.findAll()).hasSize(0);
        // set the field null
        sprint.setEndDate(null);

        // Create the Sprint, which fails.
        restSprintMockMvc.perform(post("/api/sprints")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sprint)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Sprint> sprints = sprintRepository.findAll();
        assertThat(sprints).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllSprints() throws Exception {
        // Initialize the database
        sprintRepository.saveAndFlush(sprint);

        // Get all the sprints
        restSprintMockMvc.perform(get("/api/sprints"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sprint.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].capacityForecast").value(hasItem(DEFAULT_CAPACITY_FORECAST.intValue())))
                .andExpect(jsonPath("$.[*].effortForecast").value(hasItem(DEFAULT_EFFORT_FORECAST.intValue())))
                .andExpect(jsonPath("$.[*].capacityDone").value(hasItem(DEFAULT_CAPACITY_DONE.intValue())))
                .andExpect(jsonPath("$.[*].effortDone").value(hasItem(DEFAULT_EFFORT_DONE.intValue())));
    }

    @Test
    @Transactional
    public void getSprint() throws Exception {
        // Initialize the database
        sprintRepository.saveAndFlush(sprint);

        // Get the sprint
        restSprintMockMvc.perform(get("/api/sprints/{id}", sprint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sprint.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.capacityForecast").value(DEFAULT_CAPACITY_FORECAST.intValue()))
            .andExpect(jsonPath("$.effortForecast").value(DEFAULT_EFFORT_FORECAST.intValue()))
            .andExpect(jsonPath("$.capacityDone").value(DEFAULT_CAPACITY_DONE.intValue()))
            .andExpect(jsonPath("$.effortDone").value(DEFAULT_EFFORT_DONE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSprint() throws Exception {
        // Get the sprint
        restSprintMockMvc.perform(get("/api/sprints/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSprint() throws Exception {
        // Initialize the database
        sprintRepository.saveAndFlush(sprint);

		int databaseSizeBeforeUpdate = sprintRepository.findAll().size();

        // Update the sprint
        sprint.setName(UPDATED_NAME);
        sprint.setStartDate(UPDATED_START_DATE);
        sprint.setEndDate(UPDATED_END_DATE);
        sprint.setCapacityForecast(UPDATED_CAPACITY_FORECAST);
        sprint.setEffortForecast(UPDATED_EFFORT_FORECAST);
        sprint.setCapacityDone(UPDATED_CAPACITY_DONE);
        sprint.setEffortDone(UPDATED_EFFORT_DONE);
        restSprintMockMvc.perform(put("/api/sprints")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sprint)))
                .andExpect(status().isOk());

        // Validate the Sprint in the database
        List<Sprint> sprints = sprintRepository.findAll();
        assertThat(sprints).hasSize(databaseSizeBeforeUpdate);
        Sprint testSprint = sprints.get(sprints.size() - 1);
        assertThat(testSprint.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSprint.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSprint.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSprint.getCapacityForecast()).isEqualTo(UPDATED_CAPACITY_FORECAST);
        assertThat(testSprint.getEffortForecast()).isEqualTo(UPDATED_EFFORT_FORECAST);
        assertThat(testSprint.getCapacityDone()).isEqualTo(UPDATED_CAPACITY_DONE);
        assertThat(testSprint.getEffortDone()).isEqualTo(UPDATED_EFFORT_DONE);
    }

    @Test
    @Transactional
    public void deleteSprint() throws Exception {
        // Initialize the database
        sprintRepository.saveAndFlush(sprint);

		int databaseSizeBeforeDelete = sprintRepository.findAll().size();

        // Get the sprint
        restSprintMockMvc.perform(delete("/api/sprints/{id}", sprint.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Sprint> sprints = sprintRepository.findAll();
        assertThat(sprints).hasSize(databaseSizeBeforeDelete - 1);
    }
}
