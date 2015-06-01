package de.frankbeeh.productbacklogtimeline.domain;

/**
 * Responsibility:
 * <ul>
 * <li>Immutable representation of a compared value including style for being used in ComparedValueCellFactory.
 * </ul>
 */
public class ComparedValue {
    private final String comparedValue;
    private final ProductBacklogDirection direction;

    public ComparedValue(ProductBacklogDirection direction, String comparedValue) {
        this.direction = direction;
        this.comparedValue = comparedValue;
    }
    
    public String getStyleClass() {
        return direction.toString();
    }
    
    public ProductBacklogDirection getDirection() {
        return direction;
    }
    
    public String getComparedValue() {
        return comparedValue;
    }
}
