package de.frankbeeh.productbacklogtimeline.web.rest;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;
import de.frankbeeh.productbacklogtimeline.service.ProductTimestampService;
import de.frankbeeh.productbacklogtimeline.service.importer.ProductBacklogFromCsvImporter;

/**
 * REST controller for managing ProductBacklogItem.
 */
@RestController
@RequestMapping("/api")
public class UploadResource {

	private final Logger log = LoggerFactory.getLogger(UploadResource.class);
	@Inject
	private ProductTimestampService productTimestampService;
	
	/**
	 * Upload a csv with PBIs.
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/upload")
	@Timed
	public void upload(@RequestParam("file") MultipartFile file,
			@RequestParam("username") String username, @RequestParam("selectedTimestamp") Long selectedTimestamp) throws IOException {
		final VelocityForecast velocityForecast = productTimestampService.getVelocityForecast(
				selectedTimestamp);
		final ProductBacklog productBacklog = new ProductBacklogFromCsvImporter()
				.importData(new InputStreamReader(file.getInputStream()), velocityForecast);
		log.debug(String.format("receive %s from %s: %s",
				file.getOriginalFilename(), username, productBacklog));
	}
}
