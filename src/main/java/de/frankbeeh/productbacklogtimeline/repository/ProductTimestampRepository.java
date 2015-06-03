package de.frankbeeh.productbacklogtimeline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.frankbeeh.productbacklogtimeline.domain.ProductTimestamp;

/**
 * Spring Data JPA repository for the ProductTimestamp entity.
 */
public interface ProductTimestampRepository extends JpaRepository<ProductTimestamp,Long> {

}
