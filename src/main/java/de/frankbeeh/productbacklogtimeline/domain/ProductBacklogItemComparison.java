package de.frankbeeh.productbacklogtimeline.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.frankbeeh.productbacklogtimeline.domain.util.DifferenceFormatter;


/**
 * Responsibility:
 * <ul>
 * <li>Immutable representation of the comparison of two {@link ProductBacklogItem}s.
 * </ul>
 */
public class ProductBacklogItemComparison {
    private final DecoratedProductBacklogItem productBacklogItem;
    private final DecoratedProductBacklogItem referenceProductBacklogItem;

    public ProductBacklogItemComparison(DecoratedProductBacklogItem productBacklogItem) {
        this(productBacklogItem, productBacklogItem);
    }

    public ProductBacklogItemComparison(DecoratedProductBacklogItem productBacklogItem, DecoratedProductBacklogItem referenceProductBacklogItem) {
        this.productBacklogItem = productBacklogItem;
        if (referenceProductBacklogItem == null) {
            this.referenceProductBacklogItem = new DecoratedProductBacklogItem(null, null, null, null, State.New, null, null, null);
        } else {
            this.referenceProductBacklogItem = referenceProductBacklogItem;
        }
    }

    public ComparedValue getComparedPbiKey() {
        return DifferenceFormatter.formatTextualDifference(productBacklogItem.getPbiKey(), referenceProductBacklogItem.getPbiKey());
    }

    public ComparedValue getComparedTitle() {
        return DifferenceFormatter.formatTextualDifference(productBacklogItem.getTitle(), referenceProductBacklogItem.getTitle());
    }

    public ComparedValue getComparedDescription() {
        return DifferenceFormatter.formatTextualDifference(productBacklogItem.getDescription(), referenceProductBacklogItem.getDescription());
    }

    public ComparedValue getComparedCompletionForecast(String progressForecastName) {
        return DifferenceFormatter.formatSprintDifference(productBacklogItem.getCompletionForecast(progressForecastName), referenceProductBacklogItem.getCompletionForecast(progressForecastName));
    }

    public ComparedValue getComparedState() {
        return DifferenceFormatter.formatStateDifference(productBacklogItem.getState(), referenceProductBacklogItem.getState());
    }

    public ComparedValue getComparedEstimate() {
        return DifferenceFormatter.formatBigDecimalDifference(productBacklogItem.getEstimate(), referenceProductBacklogItem.getEstimate(), true);
    }

    public ComparedValue getComparedAccumulatedEstimate() {
        return DifferenceFormatter.formatDoubleDifference(productBacklogItem.getAccumulatedEstimate(), referenceProductBacklogItem.getAccumulatedEstimate(), true);
    }

    public ComparedValue getComparedProductBacklogRank() {
        return DifferenceFormatter.formatProductBacklogRankDifference(productBacklogItem.getProductBacklogRank(), referenceProductBacklogItem.getProductBacklogRank());
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
