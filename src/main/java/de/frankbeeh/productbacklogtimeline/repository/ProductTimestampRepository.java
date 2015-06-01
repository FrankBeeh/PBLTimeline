package de.frankbeeh.productbacklogtimeline.repository;

import de.frankbeeh.productbacklogtimeline.domain.ProductTimestamp;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductTimestamp entity.
 */
public interface ProductTimestampRepository extends JpaRepository<ProductTimestamp,Long> {

}
