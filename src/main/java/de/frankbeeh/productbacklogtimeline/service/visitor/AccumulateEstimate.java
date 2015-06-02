package de.frankbeeh.productbacklogtimeline.service.visitor;

import java.math.BigDecimal;

import de.frankbeeh.productbacklogtimeline.domain.DecoratedProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.State;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;

/**
 * Responsibility:
 * <ul>
 * <li>Compute the accumulated estimate for completing a {@link ProductBacklogItem}.
 * </ul>
 */
public class AccumulateEstimate implements ProductBacklogItemVisitor {

    private BigDecimal accumulatedEstimate;

    public AccumulateEstimate() {
        reset();
    }

    public void reset() {
        accumulatedEstimate = BigDecimal.ZERO;
    }

    public void visit(DecoratedProductBacklogItem productBacklogItem, VelocityForecast selectedVelocityForecast) {
        if (State.Canceled.equals(productBacklogItem.getState())) {
            productBacklogItem.setAccumulatedEstimate(null);
        } else {
        	accumulatedEstimate = accumulatedEstimate.add(productBacklogItem.getCleanedEstimate());
            productBacklogItem.setAccumulatedEstimate(accumulatedEstimate);
        }
    }
}
