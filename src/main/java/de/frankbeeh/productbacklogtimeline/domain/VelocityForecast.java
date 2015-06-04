package de.frankbeeh.productbacklogtimeline.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.service.visitor.AccumulateEffortDone;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeAverageVelocityStrategy;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeMaximumVelocityStrategy;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeMinimumVelocityStrategy;
import de.frankbeeh.productbacklogtimeline.service.visitor.ComputeProgressForecastByVelocity;
import de.frankbeeh.productbacklogtimeline.service.visitor.SprintDataVisitor;

/**
 * Responsibility:
 * <ul>
 * <li>Forecast the progress of {@link Sprint}s depending on the velocity of
 * previous.
 * </ul>
 */
public class VelocityForecast {
	public static final String AVERAGE_VELOCITY_FORECAST = "Avg. Vel.";
	public static final String MINIMUM_VELOCITY_FORECAST = "Min. Vel.";
	public static final String MAXIMUM_VELOCITY_FORECAST = "Max. Vel.";
	public static final List<String> COMPLETION_FORECASTS = Arrays.asList(
			MINIMUM_VELOCITY_FORECAST, AVERAGE_VELOCITY_FORECAST,
			MAXIMUM_VELOCITY_FORECAST);

	private final List<DecoratedSprint> sprints;
	private final List<SprintDataVisitor> visitors;
	private final Map<String, Integer> sortIndexMap;

	public VelocityForecast() {
		this(new ArrayList<DecoratedSprint>());
	}

	@VisibleForTesting
	public VelocityForecast(List<DecoratedSprint> sprints,
			List<SprintDataVisitor> visitors) {
		this.visitors = visitors;
		this.sprints = sprints;
		this.sortIndexMap = new HashMap<String, Integer>();
	}

	public VelocityForecast(List<DecoratedSprint> sprints) {
		this(sprints, Arrays.asList(new AccumulateEffortDone(),
				new ComputeProgressForecastByVelocity(
						AVERAGE_VELOCITY_FORECAST,
						new ComputeAverageVelocityStrategy()),
				new ComputeProgressForecastByVelocity(
						MINIMUM_VELOCITY_FORECAST,
						new ComputeMinimumVelocityStrategy()),
				new ComputeProgressForecastByVelocity(
						MAXIMUM_VELOCITY_FORECAST,
						new ComputeMaximumVelocityStrategy())));
	}

	public List<DecoratedSprint> getSprints() {
		return sprints;
	}

	public void addItem(DecoratedSprint sprint) {
		sprints.add(sprint);
		sortIndexMap.put(sprint.getName(), sprints.size());
	}

	public void updateForecast() {
		for (final SprintDataVisitor visitor : visitors) {
			visitor.reset();
			for (final DecoratedSprint sprintData : sprints) {
				sprintData.accept(visitor);
			}
		}
	}

	public DecoratedSprint getCompletionSprintForecast(String progressForecastName,
			BigDecimal accumulatedEstimate) {
		for (final DecoratedSprint sprint : sprints) {
			final BigDecimal accumulatedEffortDoneOrProgressForcast = sprint
					.getAccumulatedEffortDoneOrProgressForcast(progressForecastName);
			if (accumulatedEffortDoneOrProgressForcast != null
					&& accumulatedEstimate != null
					&& accumulatedEstimate
							.compareTo(accumulatedEffortDoneOrProgressForcast) <= 0) {
				return sprint;
			}
		}
		return null;
	}

	public int getSortIndex(String sprintNames) {
		final String[] splittedSprintNames = sprintNames.split(", ");
		final Integer sortIndex = sortIndexMap
				.get(splittedSprintNames[splittedSprintNames.length - 1]);
		if (sortIndex == null) {
			return Integer.MAX_VALUE;
		}
		return sortIndex;
	}

	public DecoratedSprint getSprintByEndDate(LocalDate endDate) {
		for (DecoratedSprint sprint : sprints) {
			if (endDate.equals(sprint.getEndDate())) {
				return sprint;
			}
		}
		return null;
	}
}
