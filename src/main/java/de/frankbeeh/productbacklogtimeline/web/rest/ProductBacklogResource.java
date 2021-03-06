package de.frankbeeh.productbacklogtimeline.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	private final Logger log = LoggerFactory
			.getLogger(ProductBacklogResource.class);

	@Inject
	private ProductTimestampService productTimestampService;

	/**
	 * Get the {@link ProductBacklogItemComparison} between the
	 * 'selectedTimestamp' and 'referenceTimestamp'.
	 */
	@RequestMapping(value = "/productBacklog", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<ProductBacklogItemComparison>> get(
			@RequestParam(required=false) Long selectedTimestamp,
			@RequestParam(required=false) Long referenceTimestamp) {
		log.debug("REST request to get ProductBacklog : {} {}",
				selectedTimestamp, referenceTimestamp);
		if (selectedTimestamp==null){
			return new ResponseEntity<>(new ArrayList<ProductBacklogItemComparison>(), HttpStatus.OK);
		}
		if (referenceTimestamp == null) {
			referenceTimestamp = selectedTimestamp;
		}
		return Optional
				.ofNullable(
						productTimestampService.getProductBacklogComparison(
								selectedTimestamp, referenceTimestamp))
				.map(productBacklogItem -> new ResponseEntity<>(
						productBacklogItem, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

}
