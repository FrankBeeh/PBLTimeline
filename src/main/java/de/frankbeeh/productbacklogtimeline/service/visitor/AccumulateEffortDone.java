package de.frankbeeh.productbacklogtimeline.service.visitor;

import java.math.BigDecimal;

import de.frankbeeh.productbacklogtimeline.domain.DecoratedSprint;

public class AccumulateEffortDone implements SprintDataVisitor {

    private BigDecimal accumulatedEffortDone;

    public AccumulateEffortDone() {
        reset();
    }

    @Override
    public void reset() {
        accumulatedEffortDone = null;
    }

    @Override
    public void visit(DecoratedSprint sprintData) {
        accumulatedEffortDone = computeAccumulatedEffortDone(sprintData);
        sprintData.setAccumulatedEffortDone(accumulatedEffortDone);
    }

    private BigDecimal computeAccumulatedEffortDone(DecoratedSprint sprintData) {
        final BigDecimal effortDone = sprintData.getEffortDone();
        if (accumulatedEffortDone == null) {
            return effortDone;
        }
        if (effortDone == null) {
            return null;
        }
        return accumulatedEffortDone.add(effortDone);
    }
}
