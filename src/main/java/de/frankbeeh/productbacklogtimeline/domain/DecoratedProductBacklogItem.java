package de.frankbeeh.productbacklogtimeline.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Strings;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

/**
 * Responsibility:
 * <ul>
 * <li>Represent the data of an item of a {@link ProductBacklog}:
 * <ul>
 * <li>Data that is independent from the ordering and persisted.
 * <li>Data that is dependent on the the ordering and is computed online.
 * </ul>
 * </ul>
 */
public class DecoratedProductBacklogItem {
	private final Map<String, Sprint> completionForecast;
	private Double accumulatedEstimate;
	private Integer productBacklogRank;
	private final ProductBacklogItem data;

	public DecoratedProductBacklogItem(String id, String title,
			String description, BigDecimal estimate, State state,
			String jiraSprint, String jiraRank, String plannedRelease) {
		this(new ProductBacklogItem(id, title, description, estimate,
				state.toString()));
	}

	public DecoratedProductBacklogItem(ProductBacklogItem productBacklogItem) {
		this.data = productBacklogItem;
		this.completionForecast = new HashMap<String, Sprint>();
	}

	public void setAccumulatedEstimate(Double accumulatedEstimate) {
		this.accumulatedEstimate = accumulatedEstimate;
	}

	public Double getAccumulatedEstimate() {
		return accumulatedEstimate;
	}

	public Sprint getCompletionForecast(String progressForecastName) {
		return completionForecast.get(progressForecastName);
	}

	public void setCompletionForecast(String progressForecastName, Sprint sprint) {
		completionForecast.put(progressForecastName, sprint);
	}

	public void setProductBacklogRank(int productBacklogRank) {
		this.productBacklogRank = productBacklogRank;
	}

	public Integer getProductBacklogRank() {
		return productBacklogRank;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this,
				ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}

	public String getPbiKey() {
		return data.getPbiKey();
	}

	public String getTitle() {
		return data.getTitle();
	}

	public String getDescription() {
		return data.getDescription();
	}

	public BigDecimal getEstimate() {
		return data.getEstimate();
	}

	/**
	 * @return <code>0</code> if state of Product Backlog Item is canceled or
	 *         estimate is <code>null</code>; {@link #getEstimate()} otherwise.
	 */
	public BigDecimal getCleanedEstimate() {
		if (State.Canceled.equals(getState()) || data.getEstimate() == null) {
			return BigDecimal.valueOf(0);
		}
		return data.getEstimate();
	}

	public void setEstimate(BigDecimal estimate) {
		data.setEstimate(estimate);
	}

	public State getState() {
		return State.valueOf(data.getState());
	}

	public String getHash() {
		final HashFunction hashFunction = Hashing.sha1();
		final Hasher hasher = hashFunction.newHasher()
				.putUnencodedChars(Strings.nullToEmpty(getPbiKey()))
				.putUnencodedChars(nullToDefault(getTitle()))
				.putUnencodedChars(nullToDefault(getDescription()));
		if (getEstimate() != null) {
			hasher.putDouble(getEstimate().doubleValue());
		}
		if (getState() != null) {
			hasher.putUnencodedChars(getState().toString());
		}
		// hasher.putUnencodedChars(nullToDefault(jiraSprint)).putUnencodedChars(nullToDefault(jiraRank)).putUnencodedChars(nullToDefault(plannedRelease));
		return hasher.hash().toString();
	}

	private String nullToDefault(String string) {
		if (string == null) {
			return "-";
		}
		return string;
	}
}