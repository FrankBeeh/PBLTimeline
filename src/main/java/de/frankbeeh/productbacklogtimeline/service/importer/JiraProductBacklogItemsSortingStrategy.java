package de.frankbeeh.productbacklogtimeline.service.importer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;

public class JiraProductBacklogItemsSortingStrategy {

    private static class ProductBacklogItemComparator implements Comparator<JiraProductBacklogItem> {
        private final VelocityForecast velocityForecast;

        public ProductBacklogItemComparator(VelocityForecast velocityForecast) {
            this.velocityForecast = velocityForecast;
        }

        @Override
        public int compare(JiraProductBacklogItem productBacklogItem1, JiraProductBacklogItem productBacklogItem2) {
            final int sortIndexDifference = velocityForecast.getSortIndex(productBacklogItem1.getJiraSprint()) - velocityForecast.getSortIndex(productBacklogItem2.getJiraSprint());
            if (sortIndexDifference == 0) {
                return productBacklogItem1.getJiraRank().compareTo(productBacklogItem2.getJiraRank());
            }
            return sortIndexDifference;
        }
    }

    public void sortItems(List<JiraProductBacklogItem> items, VelocityForecast velocityForecast) {
        Collections.sort(items, new ProductBacklogItemComparator(velocityForecast));
    }
}
