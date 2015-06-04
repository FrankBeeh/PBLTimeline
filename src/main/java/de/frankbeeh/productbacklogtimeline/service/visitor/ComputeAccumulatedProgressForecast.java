package de.frankbeeh.productbacklogtimeline.service.visitor;

import java.math.BigDecimal;

import de.frankbeeh.productbacklogtimeline.domain.DecoratedSprint;

public class ComputeAccumulatedProgressForecast implements SprintDataVisitor {
    private BigDecimal accumulatedProgressForecast;
    private final String progressForecastName;

    public ComputeAccumulatedProgressForecast(String progressForecastName) {
        this.progressForecastName = progressForecastName;
    }

    @Override
    public void reset() {
        accumulatedProgressForecast = null;
    }

    @Override
    public void visit(DecoratedSprint sprintData) {
        final BigDecimal accumulatedEffortDone = sprintData.getAccumulatedEffortDone();
        final BigDecimal progressForecast = sprintData.getProgressForecastBasedOnHistory(progressForecastName);
        if (accumulatedEffortDone == null) {
            if (progressForecast != null) {
                accumulatedProgressForecast = addProgressForecast(progressForecast);
                sprintData.setAccumulatedProgressForecastBasedOnHistory(progressForecastName, accumulatedProgressForecast);
            }
        } else {
            accumulatedProgressForecast = accumulatedEffortDone;
        }
    }

    private BigDecimal addProgressForecast(BigDecimal progressForecast) {
        if (accumulatedProgressForecast == null) {
            return progressForecast;
        } else {
            return accumulatedProgressForecast.add(progressForecast);
        }
    }
}
