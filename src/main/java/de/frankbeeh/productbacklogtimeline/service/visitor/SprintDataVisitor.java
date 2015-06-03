package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.domain.DecoratedSprint;

public interface SprintDataVisitor {

    public abstract void reset();

    public abstract void visit(DecoratedSprint sprint);

}