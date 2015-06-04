package de.frankbeeh.productbacklogtimeline.service.visitor;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Responsibility:
 * <ul>
 * <li>Compute the new <b>average</b> velocity.
 * </ul>
 */
public class ComputeAverageVelocityStrategy implements ComputeVelocityStrategy {
	@Override
	public BigDecimal computeVelocity(BigDecimal velocityOfThisSprint,
			BigDecimal historicVelocity, int numberOfClientsVisited) {
		return (historicVelocity.multiply(BigDecimal
				.valueOf(numberOfClientsVisited - 1l))
				.add(velocityOfThisSprint)).divide(BigDecimal
				.valueOf((long) numberOfClientsVisited), 10, RoundingMode.HALF_UP);
	}
}
