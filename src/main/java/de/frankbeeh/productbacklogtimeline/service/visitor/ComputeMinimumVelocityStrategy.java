package de.frankbeeh.productbacklogtimeline.service.visitor;

import java.math.BigDecimal;

/**
 * Responsibility:
 * <ul>
 * <li>Compute the new <b>minimum</b> velocity.
 * </ul>
 */
public class ComputeMinimumVelocityStrategy implements ComputeVelocityStrategy {
    @Override
    public BigDecimal computeVelocity(BigDecimal velocityOfThisSprint, BigDecimal historicVelocity, int numberOfClientsVisited) {
        return velocityOfThisSprint.min(historicVelocity);
    }
}
