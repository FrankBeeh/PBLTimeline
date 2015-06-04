package de.frankbeeh.productbacklogtimeline.service.visitor;

import java.math.BigDecimal;

/**
 * Responsibility:
 * <ul>
 * <li>Compute the new <b>maximum</b> velocity.
 * </ul>
 */
public class ComputeMaximumVelocityStrategy implements ComputeVelocityStrategy {
    @Override
    public BigDecimal computeVelocity(BigDecimal velocityOfThisSprint, BigDecimal historicVelocity, int numberOfClientsVisited) {
        return velocityOfThisSprint.max(historicVelocity);
    }
}
