package de.frankbeeh.productbacklogtimeline.service.importer;

import java.math.BigDecimal;

import de.frankbeeh.productbacklogtimeline.domain.DecoratedProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.State;

public class JiraProductBacklogItem extends DecoratedProductBacklogItem {
	private final String jiraRank;
	private final String jiraSprint;

	public JiraProductBacklogItem(String id, String title, String description,
			BigDecimal estimate, State state, String jiraSprint, String jiraRank,
			String plannedRelease) {
		super(id, title, description, estimate, state, 0l);
		this.jiraSprint = jiraSprint;
		this.jiraRank = jiraRank;
	}
	
	public String getJiraRank() {
		return jiraRank;
	}
	
	public String getJiraSprint() {
		return jiraSprint;
	}
}
