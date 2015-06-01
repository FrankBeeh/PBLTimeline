package de.frankbeeh.productbacklogtimeline.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.repository.ProductBacklogItemRepository;
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
 * REST controller for managing ProductBacklogItem.
 */
@RestController
@RequestMapping("/api")
public class ProductBacklogItemResource {

    private final Logger log = LoggerFactory.getLogger(ProductBacklogItemResource.class);

    @Inject
    private ProductBacklogItemRepository productBacklogItemRepository;

    /**
     * POST  /productBacklogItems -> Create a new productBacklogItem.
     */
    @RequestMapping(value = "/productBacklogItems",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody ProductBacklogItem productBacklogItem) throws URISyntaxException {
        log.debug("REST request to save ProductBacklogItem : {}", productBacklogItem);
        if (productBacklogItem.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new productBacklogItem cannot already have an ID").build();
        }
        productBacklogItemRepository.save(productBacklogItem);
        return ResponseEntity.created(new URI("/api/productBacklogItems/" + productBacklogItem.getId())).build();
    }

    /**
     * PUT  /productBacklogItems -> Updates an existing productBacklogItem.
     */
    @RequestMapping(value = "/productBacklogItems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody ProductBacklogItem productBacklogItem) throws URISyntaxException {
        log.debug("REST request to update ProductBacklogItem : {}", productBacklogItem);
        if (productBacklogItem.getId() == null) {
            return create(productBacklogItem);
        }
        productBacklogItemRepository.save(productBacklogItem);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /productBacklogItems -> get all the productBacklogItems.
     */
    @RequestMapping(value = "/productBacklogItems",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProductBacklogItem> getAll() {
        log.debug("REST request to get all ProductBacklogItems");
        return productBacklogItemRepository.findAll();
    }

    /**
     * GET  /productBacklogItems/:id -> get the "id" productBacklogItem.
     */
    @RequestMapping(value = "/productBacklogItems/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductBacklogItem> get(@PathVariable Long id) {
        log.debug("REST request to get ProductBacklogItem : {}", id);
        return Optional.ofNullable(productBacklogItemRepository.findOne(id))
            .map(productBacklogItem -> new ResponseEntity<>(
                productBacklogItem,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /productBacklogItems/:id -> delete the "id" productBacklogItem.
     */
    @RequestMapping(value = "/productBacklogItems/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete ProductBacklogItem : {}", id);
        productBacklogItemRepository.delete(id);
    }
}
