package de.frankbeeh.productbacklogtimeline.service.visitor;

import de.frankbeeh.productbacklogtimeline.domain.Sprint;

public interface SprintDataVisitor {

    public abstract void reset();

    public abstract void visit(Sprint sprintData);

}