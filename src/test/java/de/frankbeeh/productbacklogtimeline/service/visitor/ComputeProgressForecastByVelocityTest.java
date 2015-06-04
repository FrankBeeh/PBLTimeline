package de.frankbeeh.productbacklogtimeline.service.visitor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.DecoratedSprint;

public class ComputeProgressForecastByVelocityTest {

	private final class SprintDataVisitorMock implements SprintDataVisitor {
		boolean resetWasCalled = false;

		@Override
		public void visit(DecoratedSprint sprint) {
		}

		@Override
		public void reset() {
			resetWasCalled = true;
		}
	}

	private static final BigDecimal EFFORT_FORECAST_1 = BigDecimal.valueOf(7d);
	private static final BigDecimal CAPACITY_FORECAST_1 = BigDecimal
			.valueOf(11d);
	private static final BigDecimal VELOCITY_FORECAST_1 = EFFORT_FORECAST_1
			.divide(CAPACITY_FORECAST_1, 10, RoundingMode.HALF_UP);
	private static final BigDecimal EFFORT_DONE_1 = BigDecimal.valueOf(8d);
	private static final BigDecimal CAPACITY_DONE_1 = BigDecimal.valueOf(13d);
	private static final BigDecimal VELOCITY_DONE_1 = EFFORT_DONE_1.divide(
			CAPACITY_DONE_1, 10, RoundingMode.HALF_UP);

	private static final BigDecimal CAPACITY_FORECAST_2 = BigDecimal
			.valueOf(10d);
	private static final BigDecimal EFFORT_FORECAST_2 = BigDecimal.valueOf(5d);
	private static final BigDecimal VELOCITY_FORECAST_2 = EFFORT_FORECAST_2
			.divide(CAPACITY_FORECAST_2, 10, RoundingMode.HALF_UP);
	private static final BigDecimal CAPACITY_DONE_2 = BigDecimal.valueOf(6d);
	private static final BigDecimal EFFORT_DONE_2 = BigDecimal.valueOf(2d);
	private static final BigDecimal VELOCITY_DONE_2 = EFFORT_DONE_2.divide(
			CAPACITY_DONE_2, 10, RoundingMode.HALF_UP);

	private static final BigDecimal EFFORT_FORECAST_3 = BigDecimal.valueOf(11d);
	private static final BigDecimal CAPACITY_FORECAST_3 = BigDecimal
			.valueOf(14d);
	private static final BigDecimal VELOCITY_FORECAST_3 = EFFORT_FORECAST_3
			.divide(CAPACITY_FORECAST_3, 10, RoundingMode.HALF_UP);
	private static final BigDecimal EFFORT_DONE_3 = BigDecimal.valueOf(12d);
	private static final BigDecimal CAPACITY_DONE_3 = BigDecimal.valueOf(9d);
	private static final BigDecimal VELOCITY_DONE_3 = EFFORT_DONE_3.divide(
			CAPACITY_DONE_3, 10, RoundingMode.HALF_UP);
	private ComputeProgressForecastByVelocity visitor;

	public ComputeProgressForecastByVelocityTest() {
		super();
	}

	@Test
	public void visitFirst_capacityForecastMissing() {
		final DecoratedSprint sprintData = new DecoratedSprint(null, null,
				null, null, null, null, null);
		visitor.visit(sprintData);
		assertEqualsEffortForecastBasedOnHistory(null, sprintData);
	}

	@Test
	public void visitFirst_effortForecastMissing() {
		final DecoratedSprint sprintData = new DecoratedSprint(null, null,
				null, CAPACITY_FORECAST_1, null, null, null);
		visitor.visit(sprintData);
		assertEqualsEffortForecastBasedOnHistory(null, sprintData);
	}

	@Test
	public void visitFirst_capacityDoneMissing() {
		final DecoratedSprint sprintData = new DecoratedSprint(null, null,
				null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, null, null);
		visitor.visit(sprintData);
		assertEqualsEffortForecastBasedOnHistory(
				CAPACITY_FORECAST_1
						.multiply(getExpectedVelocity(VELOCITY_FORECAST_1)),
				sprintData);
	}

	@Test
	public void visitFirst_effortDoneMissing() {
		final DecoratedSprint sprintData = new DecoratedSprint(null, null,
				null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1,
				null);
		visitor.visit(sprintData);
		assertEqualsEffortForecastBasedOnHistory(
				CAPACITY_DONE_1
						.multiply(getExpectedVelocity(VELOCITY_FORECAST_1)),
				sprintData);
	}

	@Test
	public void visitFirst_capacityAndEffortDone() {
		final DecoratedSprint sprintData = new DecoratedSprint(null, null,
				null, CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1,
				EFFORT_DONE_1);
		visitor.visit(sprintData);
		assertEqualsEffortForecastBasedOnHistory(
				CAPACITY_DONE_1.multiply(getExpectedVelocity(VELOCITY_DONE_1)),
				sprintData);
	}

	@Test
	public void visitSecond_capacityForecastMissing() {
		visitor.visit(new DecoratedSprint(null, null, null,
				CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1,
				EFFORT_DONE_1));
		final DecoratedSprint sprintData = new DecoratedSprint(null, null,
				null, null, null, null, null);
		visitor.visit(sprintData);
		assertEqualsEffortForecastBasedOnHistory(null, sprintData);
	}

	@Test
	public void visitSecond_effortForecastMissing() {
		visitor.visit(new DecoratedSprint(null, null, null,
				CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1,
				EFFORT_DONE_1));
		final DecoratedSprint sprintData = new DecoratedSprint(null, null,
				null, CAPACITY_FORECAST_2, null, null, null);
		visitor.visit(sprintData);
		assertEqualsEffortForecastBasedOnHistory(
				CAPACITY_FORECAST_2
						.multiply(getExpectedVelocity(VELOCITY_DONE_1)),
				sprintData);
	}

	@Test
	public void visitSecond_capacityDoneMissing() {
		visitor.visit(new DecoratedSprint(null, null, null,
				CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1,
				EFFORT_DONE_1));
		final DecoratedSprint sprintData = new DecoratedSprint(null, null,
				null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, null, null);
		visitor.visit(sprintData);
		assertEqualsEffortForecastBasedOnHistory(
				CAPACITY_FORECAST_2.multiply(getExpectedVelocity(
						VELOCITY_DONE_1, VELOCITY_FORECAST_2)), sprintData);
	}

	@Test
	public void visitSecond_effortDoneMissing() {
		visitor.visit(new DecoratedSprint(null, null, null,
				CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1,
				EFFORT_DONE_1));
		final DecoratedSprint sprintData = new DecoratedSprint(null, null,
				null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2,
				null);
		visitor.visit(sprintData);
		assertEqualsEffortForecastBasedOnHistory(
				CAPACITY_DONE_2.multiply(getExpectedVelocity(VELOCITY_DONE_1,
						VELOCITY_FORECAST_2)), sprintData);
	}

	@Test
	public void visitSecond_capacityAndEffortDone() {
		visitor.visit(new DecoratedSprint(null, null, null,
				CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1,
				EFFORT_DONE_1));
		final DecoratedSprint sprintData = new DecoratedSprint(null, null,
				null, CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2,
				EFFORT_DONE_2);
		visitor.visit(sprintData);
		assertEqualsEffortForecastBasedOnHistory(
				CAPACITY_DONE_2.multiply(getExpectedVelocity(VELOCITY_DONE_1,
						VELOCITY_DONE_2)), sprintData);
	}

	@Test
	public void visitThird_capacityForecastMissing() {
		visitor.visit(new DecoratedSprint(null, null, null,
				CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1,
				EFFORT_DONE_1));
		visitor.visit(new DecoratedSprint(null, null, null,
				CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2,
				EFFORT_DONE_2));
		final DecoratedSprint sprintData = new DecoratedSprint(null, null,
				null, null, null, null, null);
		visitor.visit(sprintData);
		assertEqualsEffortForecastBasedOnHistory(null, sprintData);
	}

	@Test
	public void visitThird_effortForecastMissing() {
		visitor.visit(new DecoratedSprint(null, null, null,
				CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1,
				EFFORT_DONE_1));
		visitor.visit(new DecoratedSprint(null, null, null,
				CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2,
				EFFORT_DONE_2));
		final DecoratedSprint sprintData = new DecoratedSprint(null, null,
				null, CAPACITY_FORECAST_3, null, null, null);
		visitor.visit(sprintData);
		assertEqualsEffortForecastBasedOnHistory(
				CAPACITY_FORECAST_3.multiply(getExpectedVelocity(
						VELOCITY_DONE_1, VELOCITY_DONE_2)), sprintData);
	}

	@Test
	public void visitThird_capacityDoneMissing() {
		visitor.visit(new DecoratedSprint(null, null, null,
				CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1,
				EFFORT_DONE_1));
		visitor.visit(new DecoratedSprint(null, null, null,
				CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2,
				EFFORT_DONE_2));
		final DecoratedSprint sprintData = new DecoratedSprint(null, null,
				null, CAPACITY_FORECAST_3, EFFORT_FORECAST_3, null, null);
		visitor.visit(sprintData);
		assertEqualsEffortForecastBasedOnHistory(
				CAPACITY_FORECAST_3.multiply(getExpectedVelocity(
						VELOCITY_DONE_1, VELOCITY_DONE_2, VELOCITY_FORECAST_3)),
				sprintData);
	}

	@Test
	public void visitThird_effortDoneMissing() {
		visitor.visit(new DecoratedSprint(null, null, null,
				CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1,
				EFFORT_DONE_1));
		visitor.visit(new DecoratedSprint(null, null, null,
				CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2,
				EFFORT_DONE_2));
		final DecoratedSprint sprintData = new DecoratedSprint(null, null,
				null, CAPACITY_FORECAST_3, EFFORT_FORECAST_3, CAPACITY_DONE_3,
				null);
		visitor.visit(sprintData);
		assertEqualsEffortForecastBasedOnHistory(
				CAPACITY_DONE_3.multiply(getExpectedVelocity(VELOCITY_DONE_1,
						VELOCITY_DONE_2, VELOCITY_FORECAST_3)), sprintData);
	}

	@Test
	public void visitThird_capacityAndEffortDone() {
		visitor.visit(new DecoratedSprint(null, null, null,
				CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1,
				EFFORT_DONE_1));
		visitor.visit(new DecoratedSprint(null, null, null,
				CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2,
				EFFORT_DONE_2));
		final DecoratedSprint sprintData = new DecoratedSprint(null, null,
				null, CAPACITY_FORECAST_3, EFFORT_FORECAST_3, CAPACITY_DONE_3,
				EFFORT_DONE_3);
		visitor.visit(sprintData);
		BigDecimal expectedVelocity = getExpectedVelocity(VELOCITY_DONE_1,
				VELOCITY_DONE_2, VELOCITY_DONE_3);
		assertEqualsEffortForecastBasedOnHistory(
				CAPACITY_DONE_3.multiply(expectedVelocity), sprintData);
	}

	@Test
	public void reset() throws Exception {
		visitor.visit(new DecoratedSprint(null, null, null,
				CAPACITY_FORECAST_1, EFFORT_FORECAST_1, CAPACITY_DONE_1,
				EFFORT_DONE_1));
		visitor.reset();
		visitor.visit(new DecoratedSprint(null, null, null,
				CAPACITY_FORECAST_2, EFFORT_FORECAST_2, CAPACITY_DONE_2,
				EFFORT_DONE_2));
		final DecoratedSprint sprintData = new DecoratedSprint(null, null,
				null, CAPACITY_FORECAST_3, EFFORT_FORECAST_3, CAPACITY_DONE_3,
				EFFORT_DONE_3);
		visitor.visit(sprintData);
		assertEqualsEffortForecastBasedOnHistory(
				CAPACITY_DONE_3.multiply(getExpectedVelocity(VELOCITY_DONE_2,
						VELOCITY_DONE_3)), sprintData);
	}

	@Test
	public void reset_callsComputeAccumulatedProcessForecastVisitor()
			throws Exception {
		final SprintDataVisitorMock sprintDataVisitor = new SprintDataVisitorMock();
		visitor.setComputeAccumulatedProcessForecastVisitor(sprintDataVisitor);
		visitor.reset();
		assertTrue(sprintDataVisitor.resetWasCalled);
	}

	@Before
	public void setUp() {
		visitor = createVisitor();
	}

	private void assertEqualsEffortForecastBasedOnHistory(
			BigDecimal expectedProgressForecast,
			final DecoratedSprint sprintData) {
		BigDecimal progressForecastBasedOnHistory = sprintData.getProgressForecastBasedOnHistory("");
		assertEquals(round(expectedProgressForecast),
				progressForecastBasedOnHistory);
	}

	private BigDecimal round(BigDecimal value) {
		if (value == null) {
			return null;
		}
		final BigDecimal ten = BigDecimal.valueOf(10l);
		return value.multiply(ten).divide(ten, 1, RoundingMode.HALF_UP);
	}

	protected BigDecimal getExpectedVelocity(final BigDecimal... velocities) {
		BigDecimal averageVelocity = BigDecimal.ZERO;
		for (final BigDecimal velocity : velocities) {
			averageVelocity = averageVelocity.add(velocity);
		}
		return averageVelocity.divide(BigDecimal.valueOf(velocities.length),
				RoundingMode.HALF_UP);
	}

	public ComputeProgressForecastByVelocity createVisitor() {
		return new ComputeProgressForecastByVelocity("",
				new ComputeAverageVelocityStrategy());
	}
}