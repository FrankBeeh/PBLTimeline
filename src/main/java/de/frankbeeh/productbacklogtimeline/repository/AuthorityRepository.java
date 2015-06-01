package de.frankbeeh.productbacklogtimeline.repository;

import de.frankbeeh.productbacklogtimeline.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
