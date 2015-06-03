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
import de.frankbeeh.productbacklogtimeline.domain.DecoratedSprint;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItemComparison;
import de.frankbeeh.productbacklogtimeline.domain.ProductTimestampComparison;
import de.frankbeeh.productbacklogtimeline.domain.Sprint;
import de.frankbeeh.productbacklogtimeline.domain.SprintComparison;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;
import de.frankbeeh.productbacklogtimeline.repository.ProductBacklogItemRepository;
import de.frankbeeh.productbacklogtimeline.repository.SprintRepository;

@Service
@Transactional
public class ProductTimestampService {

	private final Logger log = LoggerFactory
			.getLogger(ProductTimestampService.class);
	@Inject
	private ProductBacklogItemRepository productBacklogItemRepository;
	@Inject
	private SprintRepository sprintRepository;

	public List<ProductBacklogItemComparison> getProductBacklog(
			Long selectedTimestamp, Long referenceTimestamp) {
		return getProductTimestampComparison(selectedTimestamp,
				referenceTimestamp).getProductBacklogComparision()
				.getComparisons();
	}

	public List<SprintComparison> getVelocityForecast(Long selectedTimestamp,
			Long referenceTimestamp) {
		return getProductTimestampComparison(selectedTimestamp,
				referenceTimestamp).getVelocityForecastComparison()
				.getComparisons();
	}

	private ProductTimestampComparison getProductTimestampComparison(
			Long selectedTimestamp, Long referenceTimestamp) {
		final ProductTimestampComparison productTimestampComparison = new ProductTimestampComparison();
		productTimestampComparison
				.setSelectedTimestamp(new DecoratedProductTimestamp(null, null,
						createProductBacklog(productBacklogItemRepository
								.findByProductTimestampId(selectedTimestamp)),
						createVelocityForecast(sprintRepository
								.findByProductTimestampId(selectedTimestamp))));
		productTimestampComparison
				.setReferenceTimestamp(new DecoratedProductTimestamp(null,
						null, createProductBacklog(productBacklogItemRepository
								.findByProductTimestampId(referenceTimestamp)),
						createVelocityForecast(sprintRepository
								.findByProductTimestampId(referenceTimestamp))));
		return productTimestampComparison;
	}

	private VelocityForecast createVelocityForecast(List<Sprint> sprints) {
		final List<DecoratedSprint> decoratedSprints = new ArrayList<DecoratedSprint>();
		for (Sprint sprint : sprints) {
			decoratedSprints.add(new DecoratedSprint(sprint));
		}
		return new VelocityForecast(decoratedSprints);
	}

	private ProductBacklog createProductBacklog(
			final List<ProductBacklogItem> productBacklogItems) {
		final List<DecoratedProductBacklogItem> productBacklog = new ArrayList<DecoratedProductBacklogItem>();
		for (ProductBacklogItem productBacklogItem : productBacklogItems) {
			productBacklog.add(new DecoratedProductBacklogItem(
					productBacklogItem));
		}
		return new ProductBacklog(productBacklog);
	}

}
