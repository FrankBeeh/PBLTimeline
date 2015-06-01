package de.frankbeeh.productbacklogtimeline.web.rest;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItemComparison;
import de.frankbeeh.productbacklogtimeline.service.ProductTimestampService;

/**
 * REST controller for managing ProductBacklogItem.
 */
@RestController
@RequestMapping("/api")
public class ProductBacklogResource {

    private final Logger log = LoggerFactory.getLogger(ProductBacklogResource.class);

    @Inject
    private ProductTimestampService productTimestampService;

    /**
     * GET  /productBacklog -> get all the productBacklogItems.
     */
    @RequestMapping(value = "/productBacklog",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProductBacklogItemComparison> getAll() {
        log.debug("REST request to get ProductBacklog");
        return productTimestampService.getProductBacklog();
    }
}
