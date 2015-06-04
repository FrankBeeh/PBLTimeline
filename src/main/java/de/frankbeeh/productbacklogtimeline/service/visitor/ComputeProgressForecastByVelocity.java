package de.frankbeeh.productbacklogtimeline.service.visitor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.common.annotations.VisibleForTesting;

import de.frankbeeh.productbacklogtimeline.domain.DecoratedSprint;
import de.frankbeeh.productbacklogtimeline.domain.Sprint;

/**
 * Responsibility:
 * <ul>
 * <li>Compute the progress forecast using <b>velocity</b> by visiting
 * {@link Sprint}.
 * <li>Delegate the computation of the velocity to
 * {@link ComputeVelocityStrategy}.
 * </ul>
 */
public class ComputeProgressForecastByVelocity implements SprintDataVisitor {
	private final String progressForecastName;
	private final ComputeVelocityStrategy computeVelocityStrategy;
	private SprintDataVisitor computeAccumulatedProgressForecast;

	private int clientsVisitedCount;
	private BigDecimal historicVelocity;

	public ComputeProgressForecastByVelocity(String progressForecastName,
			ComputeVelocityStrategy computeVelocityStrategy) {
		this.progressForecastName = progressForecastName;
		this.computeAccumulatedProgressForecast = new ComputeAccumulatedProgressForecast(
				progressForecastName);
		this.computeVelocityStrategy = computeVelocityStrategy;
		reset();
	}

	@Override
	public final void reset() {
		historicVelocity = null;
		clientsVisitedCount = 0;
		computeAccumulatedProgressForecast.reset();
	}

	@Override
	public void visit(DecoratedSprint sprintData) {
		clientsVisitedCount++;
		final BigDecimal velocityOfThisSprint = computeVelocityOfThisSprint(sprintData);
		sprintData.setProgressForecastBasedOnHistory(
				progressForecastName,
				computeProgressForecastOfThisSprint(sprintData,
						velocityOfThisSprint));
		computeAccumulatedProgressForecast.visit(sprintData);
	}

	private BigDecimal getResultingVelocity(BigDecimal velocityOfThisSprint) {
		if (historicVelocity == null) {
			return velocityOfThisSprint;
		}
		if (velocityOfThisSprint == null) {
			return historicVelocity;
		}
		return computeVelocityStrategy.computeVelocity(velocityOfThisSprint,
				historicVelocity, clientsVisitedCount);
	}

	private BigDecimal computeProgressForecastOfThisSprint(
			DecoratedSprint sprintData, BigDecimal velocityOfThisSprint) {
		historicVelocity = getResultingVelocity(velocityOfThisSprint);
		BigDecimal effortForecast = null;
		if (historicVelocity != null) {
			effortForecast = computeProgressForecast(historicVelocity,
					sprintData.getCapacityDone());
			if (effortForecast == null) {
				effortForecast = computeProgressForecast(historicVelocity,
						sprintData.getCapacityForecast());
			}
		}
		return effortForecast;
	}

	private BigDecimal computeProgressForecast(BigDecimal velocity,
			BigDecimal capacity) {
		if (capacity == null) {
			return null;
		}
		return velocity.multiply(capacity);
	}

	private BigDecimal computeVelocityOfThisSprint(DecoratedSprint sprintData) {
		BigDecimal velocity = null;
		final BigDecimal capacityDone = sprintData.getCapacityDone();
		final BigDecimal effortDone = sprintData.getEffortDone();
		velocity = computeVelocity(capacityDone, effortDone);
		if (velocity == null) {
			velocity = computeVelocity(sprintData.getCapacityForecast(),
					sprintData.getEffortForecast());
		}
		return velocity;
	}

	private BigDecimal computeVelocity(final BigDecimal capacity,
			final BigDecimal effort) {
		if (capacity == null || effort == null) {
			return null;
		}
		return effort.divide(capacity, 10, RoundingMode.HALF_UP);
	}

	@VisibleForTesting
	void setComputeAccumulatedProcessForecastVisitor(
			SprintDataVisitor computeAccumulatedProgressForecastMock) {
		computeAccumulatedProgressForecast = computeAccumulatedProgressForecastMock;
	}
}
