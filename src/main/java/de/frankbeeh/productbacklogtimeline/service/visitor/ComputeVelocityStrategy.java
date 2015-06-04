package de.frankbeeh.productbacklogtimeline.service.visitor;

import java.math.BigDecimal;

/**
 * Responsibility:
 * <ul>
 * <li>Define the interface for all strategies that compute velocity.
 * </ul>
 */
public interface ComputeVelocityStrategy {
	BigDecimal computeVelocity(BigDecimal velocityOfThisSprint, BigDecimal historicVelocity, int numberOfClientsVisited);
}