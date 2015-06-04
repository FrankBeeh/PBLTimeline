package de.frankbeeh.productbacklogtimeline.service.visitor;


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.DecoratedSprint;

public class ComputeAccumulatedProgressForecastTest {

    private static final BigDecimal EFFORT_DONE_1 = BigDecimal.valueOf(10d);
    private static final BigDecimal PROGRESS_FORECAST_1 = BigDecimal.valueOf(5d);
    private static final BigDecimal PROGRESS_FORECAST_2 = BigDecimal.valueOf(6d);
    private static final BigDecimal PROGRESS_FORECAST_3 = BigDecimal.valueOf(7d);
    private static final BigDecimal PROGRESS_FORECAST_5 = BigDecimal.valueOf(8d);
    private static final String HISTORY_NAME = "history";

    private ComputeAccumulatedProgressForecast visitor;

    @Test
    public void visit() { 
        final DecoratedSprint sprint1 = createSprintData(EFFORT_DONE_1, PROGRESS_FORECAST_1);
        visitor.visit(sprint1);
        assertEquals(null, sprint1.getAccumulatedProgressForecastBasedOnHistory(HISTORY_NAME));

        final DecoratedSprint sprint2 = createSprintData(null, PROGRESS_FORECAST_2);
        visitor.visit(sprint2);
        assertEquals(EFFORT_DONE_1.add(PROGRESS_FORECAST_2), sprint2.getAccumulatedProgressForecastBasedOnHistory(HISTORY_NAME));

        final DecoratedSprint sprint3 = createSprintData(null, PROGRESS_FORECAST_3);
        visitor.visit(sprint3);
        assertEquals(EFFORT_DONE_1.add(PROGRESS_FORECAST_2).add(PROGRESS_FORECAST_3), sprint3.getAccumulatedProgressForecastBasedOnHistory(HISTORY_NAME));

        final DecoratedSprint sprint4 = createSprintData(null, null);
        visitor.visit(sprint4);
        assertEquals(null, sprint4.getAccumulatedProgressForecastBasedOnHistory(HISTORY_NAME));

        final DecoratedSprint sprint5 = createSprintData(null, PROGRESS_FORECAST_5);
        visitor.visit(sprint5);
        assertEquals(EFFORT_DONE_1.add(PROGRESS_FORECAST_2).add(PROGRESS_FORECAST_3).add(PROGRESS_FORECAST_5), sprint5.getAccumulatedProgressForecastBasedOnHistory(HISTORY_NAME));
    }

    @Test
    public void reset() throws Exception {
        final DecoratedSprint sprint1 = createSprintData(EFFORT_DONE_1, PROGRESS_FORECAST_1);
        visitor.visit(sprint1);
        assertEquals(null, sprint1.getAccumulatedProgressForecastBasedOnHistory(HISTORY_NAME));

        visitor.reset();

        final DecoratedSprint sprint2 = createSprintData(null, PROGRESS_FORECAST_2);
        visitor.visit(sprint2);
        assertEquals(PROGRESS_FORECAST_2, sprint2.getAccumulatedProgressForecastBasedOnHistory(HISTORY_NAME));
    }

    @Before
    public void setUp() {
        visitor = new ComputeAccumulatedProgressForecast(HISTORY_NAME);
    }

    private DecoratedSprint createSprintData(BigDecimal accumulatedEffortDone, BigDecimal progressForecast) {
        final DecoratedSprint sprint = new DecoratedSprint(null, null, null, null, null, null, null);
        sprint.setAccumulatedEffortDone(accumulatedEffortDone);
        sprint.setProgressForecastBasedOnHistory(HISTORY_NAME, progressForecast);
        return sprint;
    }
}
