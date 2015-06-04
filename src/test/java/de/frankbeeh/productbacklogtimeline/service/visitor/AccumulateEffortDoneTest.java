package de.frankbeeh.productbacklogtimeline.service.visitor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.DecoratedSprint;

public class AccumulateEffortDoneTest {

    private AccumulateEffortDone visitor;

    @Test
    public void visit() {
        final BigDecimal effortDone1 = BigDecimal.valueOf(10l);
		final DecoratedSprint sprint1 = newSprint(effortDone1);
        visitor.visit(sprint1);
        final BigDecimal effortDone2 = BigDecimal.valueOf(12l);
        assertEquals(effortDone1, sprint1.getAccumulatedEffortDone());
        final DecoratedSprint sprint2 = newSprint(effortDone2);
        visitor.visit(sprint2);
        assertEquals(effortDone1.add(effortDone2), sprint2.getAccumulatedEffortDone());
    }

    @Test
    public void visit_effortDoneMissing() {
        final BigDecimal effortDone1 = BigDecimal.valueOf(10l);
        final DecoratedSprint sprint1 = newSprint(effortDone1);
        visitor.visit(sprint1);
        assertEquals(effortDone1, sprint1.getAccumulatedEffortDone());
        final DecoratedSprint sprint2 = newSprint(null);
        visitor.visit(sprint2);
        assertNull(sprint2.getAccumulatedEffortDone());
    }

    @Test
    public void reset() throws Exception {
        final BigDecimal effortDone1 = BigDecimal.valueOf(10l);
        final DecoratedSprint sprint1 = newSprint(effortDone1);
        visitor.visit(sprint1);

        visitor.reset();

        final BigDecimal effortDone2 = BigDecimal.valueOf(12l);
        assertEquals(effortDone1, sprint1.getAccumulatedEffortDone());
        final DecoratedSprint sprint2 = newSprint(effortDone2);
        visitor.visit(sprint2);
        assertEquals(effortDone2, sprint2.getAccumulatedEffortDone());
    }

    @Before
    public void setUp() {
        visitor = new AccumulateEffortDone();
    }

    private DecoratedSprint newSprint(final BigDecimal effortDone) {
		return new DecoratedSprint(null, null, null, null, null, null, effortDone);
	}
}
