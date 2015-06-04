package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.domain.DecoratedProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.DecoratedSprint;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.ProductTimestamp;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;

/**
 * Responsibility:
 * <ul>
 * <li>Forecast the sprint for completing a {@link ProductBacklogItem}.
 * <li>Compute the difference to the sprint forecast by the referenced {@link ProductTimestamp}.
 * </ul>
 */
public class ForecastSprintOfCompletion implements ProductBacklogItemVisitor {

    private final String progressForecastName;

    public ForecastSprintOfCompletion(String progressForecastName) {
        this.progressForecastName = progressForecastName;
    }

    @Override
    public void reset() {
        // Nothing to do.
    }

    @Override
    public void visit(DecoratedProductBacklogItem productBacklogItem, VelocityForecast selectedVelocityForecast) {
        final DecoratedSprint completionSprintForecast = selectedVelocityForecast.getCompletionSprintForecast(progressForecastName, productBacklogItem.getAccumulatedEstimate());
        productBacklogItem.setCompletionForecast(progressForecastName, completionSprintForecast);
    }
}
