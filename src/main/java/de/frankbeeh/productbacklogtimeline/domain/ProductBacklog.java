package de.frankbeeh.productbacklogtimeline.domain;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.frankbeeh.productbacklogtimeline.service.visitor.AccumulateEstimate;
import de.frankbeeh.productbacklogtimeline.service.visitor.ForecastSprintOfCompletion;
import de.frankbeeh.productbacklogtimeline.service.visitor.ProductBacklogItemVisitor;

/**
 * Responsibility:
 * <ul>
 * <li>Contain all items of the product backlog.
 * <li>Allow visitors to visit all items.
 * </ul>
 */
public class ProductBacklog {
	private final LinkedList<DecoratedProductBacklogItem> items;

	private final ProductBacklogItemVisitor[] visitors;

	// private final ProductBacklogSortingStrategy sortingStrategy;

	public ProductBacklog() {
		// new RankProductBacklogItem()
		this.visitors = new ProductBacklogItemVisitor[] {
				new AccumulateEstimate(),
				new ForecastSprintOfCompletion(
						VelocityForecast.AVERAGE_VELOCITY_FORECAST),
				new ForecastSprintOfCompletion(
						VelocityForecast.MINIMUM_VELOCITY_FORECAST),
				new ForecastSprintOfCompletion(
						VelocityForecast.MAXIMUM_VELOCITY_FORECAST) };
		this.items = new LinkedList<DecoratedProductBacklogItem>();
	}

	// @VisibleForTesting
	// ProductBacklog(ProductBacklogSortingStrategy sortingStrategy,
	// ProductBacklogItemVisitor... visitorMocks) {
	// this.sortingStrategy = sortingStrategy;
	// this.visitors = visitorMocks;
	// this.items = new LinkedList<ProductBacklogItem>();
	// }

	public ProductBacklog(List<? extends DecoratedProductBacklogItem> items) {
		this();
		this.items.addAll(items);
	}

	public void addItem(DecoratedProductBacklogItem productBacklogItem) {
		items.add(productBacklogItem);
	}

	public List<DecoratedProductBacklogItem> getItems() {
		return items;
	}

	public void updateAllItems(VelocityForecast selectedVelocityForecast) {
		// sortingStrategy.sortProductBacklog(this, selectedVelocityForecast);
		for (final ProductBacklogItemVisitor visitor : visitors) {
			visitor.reset();
			for (final DecoratedProductBacklogItem item : items) {
				visitor.visit(item, selectedVelocityForecast);
			}
		}
	}

	public boolean containsId(String key) {
		for (final DecoratedProductBacklogItem item : items) {
			if (item.getPbiKey().equals(key)) {
				return true;
			}
		}
		return false;
	}

	public DecoratedProductBacklogItem getItemById(String key) {
		for (final DecoratedProductBacklogItem item : items) {
			if (item.getPbiKey().equals(key)) {
				return item;
			}
		}
		return null;
	}

	public BigDecimal getTotalEffort() {
		if (items.isEmpty()) {
			return BigDecimal.ZERO;
		}
		return items.getLast().getAccumulatedEstimate();
	}

	public int size() {
		return items.size();
	}

	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(items.toString()).toString();
	}
	
	// public List<ProductBacklogItem> getMatchingProductBacklogItems(
	// ReleaseCriteria criteria) {
	// final List<ProductBacklogItem> matchingProductBacklogItems = new
	// ArrayList<ProductBacklogItem>();
	// for (final ProductBacklogItem item : getItems()) {
	// if (criteria.isMatching(item)) {
	// matchingProductBacklogItems.add(item);
	// }
	// }
	// return matchingProductBacklogItems;
	// }

	// public IntegerByState getCountByState() {
	// final IntegerByState countByState = new IntegerByState();
	// for (ProductBacklogItem item : getItems()) {
	// final State state = item.getState();
	// countByState.add(state, 1);
	// }
	// return countByState;
	// }
	//
	// public NumberByState<Double> getEstimateByState() {
	// final DoubleByState estimateByState = new DoubleByState();
	// for (ProductBacklogItem item : getItems()) {
	// final State state = item.getState();
	// estimateByState.add(state, item.getEstimate());
	// }
	// return estimateByState;
	// }
}
