package de.frankbeeh.productbacklogtimeline.domain;

import java.util.Arrays;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

/**
 * Responsibility:
 * <ul>
 * <li>Compares two {@link ProductBacklog}s.
 * </ul>
 */
public class ProductBacklogComparison extends Comparison<ProductBacklog, DecoratedProductBacklogItem, ProductBacklogItemComparison> {
    public ProductBacklogComparison() {
        setSelected(new ProductBacklog());
    }

    @VisibleForTesting
    ProductBacklogComparison(ProductBacklogItemComparison... productBacklogComparisonItems) {
        getComparisons().addAll(Arrays.asList(productBacklogComparisonItems));
    }

    @Override
    protected ProductBacklogItemComparison createComparisonWithSelf(DecoratedProductBacklogItem productBacklogItem) {
        return new ProductBacklogItemComparison(productBacklogItem);
    }

    @Override
    protected ProductBacklogItemComparison createComparison(DecoratedProductBacklogItem productBacklogItem) {
        return new ProductBacklogItemComparison(productBacklogItem, getReference().getItemById(productBacklogItem.getPbiKey()));
    }

    @Override
    protected List<DecoratedProductBacklogItem> getSelectedItems() {
        return getSelected().getItems();
    }
}
