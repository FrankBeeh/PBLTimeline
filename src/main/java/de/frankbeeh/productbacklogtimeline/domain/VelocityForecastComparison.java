package de.frankbeeh.productbacklogtimeline.domain;

import java.util.List;

/**
 * Responsibility:
 * <ul>
 * <li>Compares two {@link VelocityForecast}s.
 * </ul>
 */
public class VelocityForecastComparison extends Comparison<VelocityForecast, DecoratedSprint, SprintComparison> {
    public VelocityForecastComparison(){
        setSelected(new VelocityForecast());
    }

    @Override
    protected List<DecoratedSprint> getSelectedItems() {
        return getSelected().getSprints();
    }

    @Override
    protected SprintComparison createComparison(DecoratedSprint sprint) {
        return new SprintComparison(sprint, getReference().getSprintByEndDate(sprint.getEndDate()));
    }

    @Override
    protected SprintComparison createComparisonWithSelf(DecoratedSprint sprint) {
        return new SprintComparison(sprint);
    }
}
