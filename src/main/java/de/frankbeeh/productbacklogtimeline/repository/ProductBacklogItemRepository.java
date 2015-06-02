package de.frankbeeh.productbacklogtimeline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;

/**
 * Spring Data JPA repository for the ProductBacklogItem entity.
 */
public interface ProductBacklogItemRepository extends JpaRepository<ProductBacklogItem,Long> {
	List<ProductBacklogItem> findByProductTimestampId(Long selectedTimestamp);
}
