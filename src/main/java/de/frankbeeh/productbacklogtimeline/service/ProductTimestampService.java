package de.frankbeeh.productbacklogtimeline.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.frankbeeh.productbacklogtimeline.domain.DecoratedProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.DecoratedProductTimestamp;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItemComparison;
import de.frankbeeh.productbacklogtimeline.domain.ProductTimestampComparison;
import de.frankbeeh.productbacklogtimeline.repository.ProductBacklogItemRepository;

@Service
@Transactional
public class ProductTimestampService {

	private final Logger log = LoggerFactory
			.getLogger(ProductTimestampService.class);
	@Inject
	private ProductBacklogItemRepository productBacklogItemRepository;

	public List<ProductBacklogItemComparison> getProductBacklog(
			Long selectedTimestamp, Long referenceTimestamp) {
		final ProductTimestampComparison productTimestampComparison = new ProductTimestampComparison();
		productTimestampComparison
				.setSelectedTimestamp(new DecoratedProductTimestamp(null, null,
						createProductBacklog(productBacklogItemRepository
								.findByProductTimestampId(selectedTimestamp))));
		productTimestampComparison
				.setReferenceTimestamp(new DecoratedProductTimestamp(null,
						null, createProductBacklog(productBacklogItemRepository
								.findByProductTimestampId(referenceTimestamp))));
		final List<ProductBacklogItemComparison> comparisons = productTimestampComparison.getProductBacklogComparision()
				.getComparisons();
		log.debug(comparisons.toString());
		return comparisons;
	}

	public ProductBacklog createProductBacklog(
			final List<ProductBacklogItem> selectedProductBacklogItems) {
		final List<DecoratedProductBacklogItem> productBacklog = new ArrayList<DecoratedProductBacklogItem>();
		for (ProductBacklogItem productBacklogItem : selectedProductBacklogItems) {
			productBacklog.add(new DecoratedProductBacklogItem(
					productBacklogItem));
		}
		ProductBacklog productBacklog2 = new ProductBacklog(productBacklog);
		return productBacklog2;
	}
}
