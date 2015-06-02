package de.frankbeeh.productbacklogtimeline.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.service.visitor.SprintDataVisitor;

/**
 * Responsibility:
 * <ul>
 * <li>Forecast the progress of {@link Sprint}s depending on the velocity of previous.
 * </ul>
 */
public class VelocityForecast {
    public static final String AVERAGE_VELOCITY_FORECAST = "Avg. Vel.";
    public static final String MINIMUM_VELOCITY_FORECAST = "Min. Vel.";
    public static final String MAXIMUM_VELOCITY_FORECAST = "Max. Vel.";
    public static final List<String> COMPLETION_FORECASTS = Arrays.asList(MINIMUM_VELOCITY_FORECAST, AVERAGE_VELOCITY_FORECAST, MAXIMUM_VELOCITY_FORECAST);

    private final List<Sprint> sprints;
    private final List<SprintDataVisitor> visitors;
    private final Map<String, Integer> sortIndexMap;

    public VelocityForecast() {
        this(new ArrayList<Sprint>(), new ArrayList<SprintDataVisitor>() /*, Arrays.asList(new AccumulateEffortDone(), new ComputeProgressForecastByVelocity(AVERAGE_VELOCITY_FORECAST, new ComputeAverageVelocityStrategy()),
                new ComputeProgressForecastByVelocity(MINIMUM_VELOCITY_FORECAST, new ComputeMinimumVelocityStrategy()), new ComputeProgressForecastByVelocity(MAXIMUM_VELOCITY_FORECAST,
                        new ComputeMaximumVelocityStrategy()))*/);
    }

    @VisibleForTesting
    public VelocityForecast(List<Sprint> sprints, List<SprintDataVisitor> visitors) {
        this.visitors = visitors;
        this.sprints = sprints;
        this.sortIndexMap = new HashMap<String, Integer>();
    }

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void addItem(Sprint sprint) {
        sprints.add(sprint);
        sortIndexMap.put(sprint.getName(), sprints.size());
    }

    public void updateForecast() {
//        for (final SprintDataVisitor visitor : visitors) {
//            visitor.reset();
//            for (final Sprint sprintData : sprints) {
//                sprintData.accept(visitor);
//            }
//        }
    }

    public Sprint getCompletionSprintForecast(String progressForecastName, Double accumulatedEstimate) {
//        for (final Sprint sprint : sprints) {
//            final Double accumulatedEffortDoneOrProgressForcast = sprint.getAccumulatedEffortDoneOrProgressForcast(progressForecastName);
//            if (accumulatedEffortDoneOrProgressForcast != null && accumulatedEstimate != null && accumulatedEstimate <= accumulatedEffortDoneOrProgressForcast) {
//                return sprint;
//            }
//        }
        return null;
    }

    public int getSortIndex(String sprintNames) {
        final String[] splittedSprintNames = sprintNames.split(", ");
        final Integer sortIndex = sortIndexMap.get(splittedSprintNames[splittedSprintNames.length - 1]);
        if (sortIndex == null) {
            return Integer.MAX_VALUE;
        }
        return sortIndex;
    }

    public Sprint getSprintByEndDate(LocalDate endDate) {
        for (Sprint sprint : sprints) {
            if (endDate.equals(sprint.getEndDate())) {
                return sprint;
            }
        }
        return null;
    }
}
