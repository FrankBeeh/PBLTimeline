package de.frankbeeh.productbacklogtimeline.web.rest;

import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing ProductBacklogItem.
 */
@RestController
@RequestMapping("/api")
public class UploadResource {

	private final Logger log = LoggerFactory
			.getLogger(UploadResource.class);

    /**
     * Upload a csv with PBIs.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/upload")
    @Timed
    public void upload(@RequestParam("file") MultipartFile file, @RequestParam("username") String username ) throws IOException {

        byte[] bytes = new byte[]{};

        if (!file.isEmpty()) {
             bytes = file.getBytes();
            //store file in storage
             // FIXME: parse the CSV
        }

        log.debug(String.format("receive %s from %s: %s", file.getOriginalFilename(), username, Arrays.toString(bytes)));
    }

}
