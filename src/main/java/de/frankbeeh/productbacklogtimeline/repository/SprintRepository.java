package de.frankbeeh.productbacklogtimeline.repository;

import de.frankbeeh.productbacklogtimeline.domain.Sprint;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Sprint entity.
 */
public interface SprintRepository extends JpaRepository<Sprint,Long> {

}
