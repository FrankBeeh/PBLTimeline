package de.frankbeeh.productbacklogtimeline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.frankbeeh.productbacklogtimeline.domain.Sprint;

/**
 * Spring Data JPA repository for the Sprint entity.
 */
public interface SprintRepository extends JpaRepository<Sprint,Long> {
	List<Sprint> findByProductTimestampId(Long selectedTimestamp);
}
