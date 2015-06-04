package de.frankbeeh.productbacklogtimeline.service.visitor;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;

public class ComputeAverageVelocityStrategyTest {
	private ComputeAverageVelocityStrategy strategy;

	@Test
	public void computeVelocity_secondVisit() {
		final BigDecimal historicVelocity = BigDecimal.valueOf(10d);
		final BigDecimal velocityOfThisSprint = BigDecimal.valueOf(5d);
		final BigDecimal expectedVelocity = historicVelocity.add(
				velocityOfThisSprint).divide(BigDecimal.valueOf(2l),10,
				RoundingMode.HALF_UP);
		assertEquals(expectedVelocity, strategy.computeVelocity(
				velocityOfThisSprint, historicVelocity, 2));
	}

	@Test
	public void computeVelocity_thirdVisit() {
		final BigDecimal historicVelocity = BigDecimal.valueOf(10d);
		final BigDecimal velocityOfThisSprint = BigDecimal.valueOf(5d);
		final BigDecimal expectedVelocity = (BigDecimal.valueOf(2l).multiply(
				historicVelocity).add(velocityOfThisSprint)).divide(BigDecimal
				.valueOf(3l), 10, RoundingMode.HALF_UP);
		assertEquals(expectedVelocity, strategy.computeVelocity(
				velocityOfThisSprint, historicVelocity, 3));
	}

	@Before
	public void setUp() {
		strategy = new ComputeAverageVelocityStrategy();
	}
}
