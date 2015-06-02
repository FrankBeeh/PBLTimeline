package de.frankbeeh.productbacklogtimeline.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.frankbeeh.productbacklogtimeline.domain.DecoratedProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItemComparison;
import de.frankbeeh.productbacklogtimeline.repository.ProductBacklogItemRepository;

@Service
@Transactional
public class ProductTimestampService {

	private final Logger log = LoggerFactory
			.getLogger(ProductTimestampService.class);
	@Inject
	private ProductBacklogItemRepository productBacklogItemRepository;

	public List<ProductBacklogItemComparison> getProductBacklog(Long selectedTimestamp, Long referenceTimestamp) {
		final List<ProductBacklogItemComparison> productBacklog = new ArrayList<ProductBacklogItemComparison>();
		final List<ProductBacklogItem> selectedProductBacklogItems = productBacklogItemRepository
				.findByProductTimestampId(selectedTimestamp);
		for (ProductBacklogItem productBacklogItem : selectedProductBacklogItems) {
			productBacklog.add(new ProductBacklogItemComparison(
					new DecoratedProductBacklogItem(productBacklogItem)));
		}
		return productBacklog;
	}
}
