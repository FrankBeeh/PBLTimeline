package de.frankbeeh.productbacklogtimeline.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.frankbeeh.productbacklogtimeline.domain.Sprint;
import de.frankbeeh.productbacklogtimeline.repository.SprintRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Sprint.
 */
@RestController
@RequestMapping("/api")
public class SprintResource {

    private final Logger log = LoggerFactory.getLogger(SprintResource.class);

    @Inject
    private SprintRepository sprintRepository;

    /**
     * POST  /sprints -> Create a new sprint.
     */
    @RequestMapping(value = "/sprints",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Sprint sprint) throws URISyntaxException {
        log.debug("REST request to save Sprint : {}", sprint);
        if (sprint.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new sprint cannot already have an ID").build();
        }
        sprintRepository.save(sprint);
        return ResponseEntity.created(new URI("/api/sprints/" + sprint.getId())).build();
    }

    /**
     * PUT  /sprints -> Updates an existing sprint.
     */
    @RequestMapping(value = "/sprints",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Sprint sprint) throws URISyntaxException {
        log.debug("REST request to update Sprint : {}", sprint);
        if (sprint.getId() == null) {
            return create(sprint);
        }
        sprintRepository.save(sprint);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /sprints -> get all the sprints.
     */
    @RequestMapping(value = "/sprints",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Sprint> getAll() {
        log.debug("REST request to get all Sprints");
        return sprintRepository.findAll();
    }

    /**
     * GET  /sprints/:id -> get the "id" sprint.
     */
    @RequestMapping(value = "/sprints/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Sprint> get(@PathVariable Long id) {
        log.debug("REST request to get Sprint : {}", id);
        return Optional.ofNullable(sprintRepository.findOne(id))
            .map(sprint -> new ResponseEntity<>(
                sprint,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sprints/:id -> delete the "id" sprint.
     */
    @RequestMapping(value = "/sprints/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Sprint : {}", id);
        sprintRepository.delete(id);
    }
}
