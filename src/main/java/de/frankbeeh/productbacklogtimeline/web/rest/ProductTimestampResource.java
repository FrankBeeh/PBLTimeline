package de.frankbeeh.productbacklogtimeline.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.frankbeeh.productbacklogtimeline.domain.ProductTimestamp;
import de.frankbeeh.productbacklogtimeline.repository.ProductTimestampRepository;
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
 * REST controller for managing ProductTimestamp.
 */
@RestController
@RequestMapping("/api")
public class ProductTimestampResource {

    private final Logger log = LoggerFactory.getLogger(ProductTimestampResource.class);

    @Inject
    private ProductTimestampRepository productTimestampRepository;

    /**
     * POST  /productTimestamps -> Create a new productTimestamp.
     */
    @RequestMapping(value = "/productTimestamps",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody ProductTimestamp productTimestamp) throws URISyntaxException {
        log.debug("REST request to save ProductTimestamp : {}", productTimestamp);
        if (productTimestamp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new productTimestamp cannot already have an ID").build();
        }
        productTimestampRepository.save(productTimestamp);
        return ResponseEntity.created(new URI("/api/productTimestamps/" + productTimestamp.getId())).build();
    }

    /**
     * PUT  /productTimestamps -> Updates an existing productTimestamp.
     */
    @RequestMapping(value = "/productTimestamps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody ProductTimestamp productTimestamp) throws URISyntaxException {
        log.debug("REST request to update ProductTimestamp : {}", productTimestamp);
        if (productTimestamp.getId() == null) {
            return create(productTimestamp);
        }
        productTimestampRepository.save(productTimestamp);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /productTimestamps -> get all the productTimestamps.
     */
    @RequestMapping(value = "/productTimestamps",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProductTimestamp> getAll() {
        log.debug("REST request to get all ProductTimestamps");
        return productTimestampRepository.findAll();
    }

    /**
     * GET  /productTimestamps/:id -> get the "id" productTimestamp.
     */
    @RequestMapping(value = "/productTimestamps/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductTimestamp> get(@PathVariable Long id) {
        log.debug("REST request to get ProductTimestamp : {}", id);
        return Optional.ofNullable(productTimestampRepository.findOne(id))
            .map(productTimestamp -> new ResponseEntity<>(
                productTimestamp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /productTimestamps/:id -> delete the "id" productTimestamp.
     */
    @RequestMapping(value = "/productTimestamps/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete ProductTimestamp : {}", id);
        productTimestampRepository.delete(id);
    }
}
