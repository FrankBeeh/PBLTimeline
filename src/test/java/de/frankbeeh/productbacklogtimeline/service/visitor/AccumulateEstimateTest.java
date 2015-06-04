package de.frankbeeh.productbacklogtimeline.service.visitor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.DecoratedProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.State;

public class AccumulateEstimateTest {

    private AccumulateEstimate visitor;

    @Test
    public void visitFirst_noEstimate() {
        final DecoratedProductBacklogItem productBacklogItem = newProductBacklogItem(null, State.Todo);
        visitor.visit(productBacklogItem, null);
        assertEquals(BigDecimal.ZERO, productBacklogItem.getAccumulatedEstimate());
    }

    @Test
    public void visitFirst_withEstimate() {
        final BigDecimal estimate = BigDecimal.ONE;
        final DecoratedProductBacklogItem productBacklogItem = newProductBacklogItem(estimate, State.Done);
        visitor.visit(productBacklogItem, null);
        assertEquals(estimate, productBacklogItem.getAccumulatedEstimate());
    }
    
    @Test
    public void visitFirst_Canceled() {
        final BigDecimal estimate = BigDecimal.ONE;
        final DecoratedProductBacklogItem productBacklogItem = newProductBacklogItem(estimate, State.Canceled);
        visitor.visit(productBacklogItem, null);
        assertNull(productBacklogItem.getAccumulatedEstimate());
    }

    @Test
    public void visitSecond_withEstimate() {
        final BigDecimal estimate1 = BigDecimal.ONE;
        final DecoratedProductBacklogItem productBacklogItem1 = newProductBacklogItem(estimate1, State.InProgress);
        visitor.visit(productBacklogItem1, null);

        final BigDecimal estimate2 = BigDecimal.valueOf(2d);
        final DecoratedProductBacklogItem productBacklogItem2 = newProductBacklogItem(estimate2, State.Done);
        visitor.visit(productBacklogItem2, null);
        assertEquals(estimate1.add(estimate2), productBacklogItem2.getAccumulatedEstimate());
    }
    
    @Test
    public void visitSecond_canceled() {
        final BigDecimal estimate1 = BigDecimal.ONE;
        final DecoratedProductBacklogItem productBacklogItem1 = newProductBacklogItem(estimate1, State.InProgress);
        visitor.visit(productBacklogItem1, null);

        final BigDecimal estimate2 = BigDecimal.valueOf(2d);
        final DecoratedProductBacklogItem productBacklogItem2 = newProductBacklogItem(estimate2, State.Canceled);
        visitor.visit(productBacklogItem2, null);
        assertNull(productBacklogItem2.getAccumulatedEstimate());
    }

    @Test
    public void visitSecond_noEstimate() {
        final BigDecimal estimate1 = BigDecimal.ONE;
        final DecoratedProductBacklogItem productBacklogItem1 = newProductBacklogItem(estimate1, State.Todo);
        visitor.visit(productBacklogItem1, null);

        final DecoratedProductBacklogItem productBacklogItem2 = newProductBacklogItem(null, State.Done);
        visitor.visit(productBacklogItem2, null);
        assertEquals(estimate1, productBacklogItem2.getAccumulatedEstimate());
    }

    @Test
    public void reset() {
        final BigDecimal estimate1 = BigDecimal.ONE;
        final DecoratedProductBacklogItem productBacklogItem1 = newProductBacklogItem(estimate1, State.Done);
        visitor.visit(productBacklogItem1, null);

        visitor.reset();

        final BigDecimal estimate2 = BigDecimal.valueOf(2d);
        final DecoratedProductBacklogItem productBacklogItem2 = newProductBacklogItem(estimate2, State.Done);
        visitor.visit(productBacklogItem2, null);
        assertEquals(estimate2, productBacklogItem2.getAccumulatedEstimate());
    }

    @Before
    public void setUp() {
        visitor = new AccumulateEstimate();
    }

    private DecoratedProductBacklogItem newProductBacklogItem(
			final BigDecimal estimate, State state) {
		return new DecoratedProductBacklogItem(null, null, null, estimate, state, null, null, null);
	}
}
