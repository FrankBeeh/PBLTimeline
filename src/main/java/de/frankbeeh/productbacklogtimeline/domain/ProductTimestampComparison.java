package de.frankbeeh.productbacklogtimeline.domain;

/**
 * Responsibility:
 * <ul>
 * <li>Compares two {@link ProductTimestamp}s.
 * </ul>
 */
public class ProductTimestampComparison {
    private ProductBacklogComparison productBacklogComparison = new ProductBacklogComparison();
    private VelocityForecastComparison velocityForecastComparison = new VelocityForecastComparison();
//    private ReleaseForecastComparison releaseForecastComparison = new ReleaseForecastComparison();

    public void setSelectedTimestamp(DecoratedProductTimestamp selectedProductTimestamp) {
        productBacklogComparison.setSelected(selectedProductTimestamp.getProductBacklog());
        velocityForecastComparison.setSelected(selectedProductTimestamp.getVelocityForecast());
//        releaseForecastComparison.setSelected(selectedProductTimestamp.getReleaseForecast());
    }

    public void setReferenceTimestamp(DecoratedProductTimestamp referenceProductTimestamp) {
        productBacklogComparison.setReference(referenceProductTimestamp.getProductBacklog());
        velocityForecastComparison.setReference(referenceProductTimestamp.getVelocityForecast());
//        releaseForecastComparison.setReference(referenceProductTimestamp.getReleaseForecast());
    }

    public ProductBacklogComparison getProductBacklogComparision() {
        return productBacklogComparison;
    }

    public VelocityForecastComparison getVelocityForecastComparison() {
        return velocityForecastComparison;
    }
    
//    public ReleaseForecastComparison getReleaseForecastComparison() {
//        return releaseForecastComparison;
//    }
}
