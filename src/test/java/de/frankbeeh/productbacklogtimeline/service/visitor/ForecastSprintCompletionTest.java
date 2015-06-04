package de.frankbeeh.productbacklogtimeline.service.visitor;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.DecoratedProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.DecoratedSprint;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;
import de.frankbeeh.productbacklogtimeline.domain.util.DateConverter;

public class ForecastSprintCompletionTest {
    private static final String ID = "ID";

    private static final String FORECAST_NAME = "forecast name";

    private VelocityForecast velocityForecast = mock(VelocityForecast.class);

    private ForecastSprintOfCompletion visitor;

    @Test
    public void visit() throws Exception {
        final DecoratedSprint sprint = new DecoratedSprint("Sprint 1", null, DateConverter.parseLocalDate("01.02.2003"), null, null, null, null);
        final BigDecimal accumulatedEstimate = BigDecimal.TEN;
        final DecoratedProductBacklogItem productBacklogItem = createProductBacklogItem(accumulatedEstimate);

        when(velocityForecast.getCompletionSprintForecast(FORECAST_NAME, accumulatedEstimate)).thenReturn(sprint);

        visitor.visit(productBacklogItem, velocityForecast);
        assertSame(sprint, productBacklogItem.getCompletionForecast(FORECAST_NAME));
    }

    @Before
    public void setUp() {
        visitor = new ForecastSprintOfCompletion(FORECAST_NAME);
    }

    private DecoratedProductBacklogItem createProductBacklogItem(BigDecimal accumulatedEstimate) {
        final DecoratedProductBacklogItem productBacklogItem = new DecoratedProductBacklogItem(ID, null, null, null, null, null, null, null);
        productBacklogItem.setAccumulatedEstimate(accumulatedEstimate);
        return productBacklogItem;
    }
}
