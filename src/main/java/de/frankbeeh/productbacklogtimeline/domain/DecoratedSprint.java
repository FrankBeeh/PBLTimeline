package de.frankbeeh.productbacklogtimeline.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.LocalDate;

import de.frankbeeh.productbacklogtimeline.service.visitor.SprintDataVisitor;

/**
 * Responsibility:
 * <ul>
 * <li>Represent the data a sprint:
 * <ul>
 * <li>Data that is independent from previous sprints.
 * <li>Data that is dependent of other sprints.
 * </ul>
 * </ul>
 */
public class DecoratedSprint {
    private final Sprint data;
    private final Map<String, BigDecimal> progressForecastBasedOnHistory;
    private final Map<String, BigDecimal> accumulatedProgressForecastBasedOnHistory;
    private BigDecimal accumulatedEffortDone;

    
    public DecoratedSprint(String name, LocalDate startDate, LocalDate endDate, BigDecimal capacityForecast, BigDecimal effortForecast, BigDecimal capacityDone, BigDecimal effortDone) {
        this(new Sprint(name, startDate, endDate, capacityForecast, effortForecast, capacityDone, effortDone));
    }

    public DecoratedSprint(Sprint sprint) {
    	this.data = sprint;
        this.progressForecastBasedOnHistory = new HashMap<String, BigDecimal>();
        this.accumulatedProgressForecastBasedOnHistory = new HashMap<String, BigDecimal>();
	}

	public String getName() {
        return data.getName();
    }

    public LocalDate getStartDate() {
        return data.getStartDate();
    }

    public LocalDate getEndDate() {
        return data.getEndDate();
    }

    public BigDecimal getCapacityForecast() {
        return data.getCapacityForecast();
    }

    public BigDecimal getEffortForecast() {
        return data.getEffortForecast();
    }

    public BigDecimal getCapacityDone() {
        return data.getCapacityDone();
    }

    public BigDecimal getEffortDone() {
        return data.getEffortDone();
    }

    public void setAccumulatedEffortDone(BigDecimal accumulatedEffortDone2) {
        this.accumulatedEffortDone = accumulatedEffortDone2;
    }

    public BigDecimal getAccumulatedEffortDone() {
        return accumulatedEffortDone;
    }

    public void setProgressForecastBasedOnHistory(String progressForecastName, BigDecimal progressForecast) {
        progressForecastBasedOnHistory.put(progressForecastName, roundToOneDecimal(progressForecast));
    }

    public BigDecimal getProgressForecastBasedOnHistory(String progressForecastName) {
        return progressForecastBasedOnHistory.get(progressForecastName);
    }

    public void setAccumulatedProgressForecastBasedOnHistory(String progressForecastName, BigDecimal progressForecast) {
        accumulatedProgressForecastBasedOnHistory.put(progressForecastName, roundToOneDecimal(progressForecast));
    }

    public BigDecimal getAccumulatedProgressForecastBasedOnHistory(String progressForecastName) {
        return accumulatedProgressForecastBasedOnHistory.get(progressForecastName);
    }

    public void accept(SprintDataVisitor visitor) {
        visitor.visit(this);
    }

    public BigDecimal getAccumulatedEffortDoneOrProgressForcast(String progressForecastName) {
        final BigDecimal accumulatedEffortDone = getAccumulatedEffortDone();
        if (accumulatedEffortDone != null) {
            return accumulatedEffortDone;
        }
        return getAccumulatedProgressForecastBasedOnHistory(progressForecastName);
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    private BigDecimal roundToOneDecimal(BigDecimal value) {
        if (value == null) {
            return value;
        }
        return value.setScale(1, RoundingMode.HALF_UP);
    }

    public State getState() {
        if (getEndDate() == null) {
            return State.Missing;
        }
        if (getEffortDone() != null) {
            return State.Done;
        }
        if (getEffortForecast() != null) {
            return State.InProgress;
        }
        return State.Todo;
    }

//    public String getHash() {
//        final HashFunction hashFunction = Hashing.sha1();
//        final Hasher hasher = hashFunction.newHasher().putUnencodedChars(Strings.nullToEmpty(getName()));
//        hashDate(hasher, getStartDate());
//        hashDate(hasher, getEndDate());
//        hashBigDecimal(hasher, getCapacityForecast());
//        hashBigDecimal(hasher, getEffortForecast());
//        hashBigDecimal(hasher, getCapacityDone());
//        hashBigDecimal(hasher, getEffortDone());
//        return hasher.hash().toString();
//    }
//
//    private void hashBigDecimal(final Hasher hasher, BigDecimal value) {
//        if (value == null) {
//            hasher.putChar('-');
//        } else {
//            hasher.putString(value.toString());
//        }
//    }
//
//    private void hashDate(final Hasher hasher, final LocalDate localDate) {
//        if (localDate == null) {
//            hasher.putChar('-');
//        } else {
//            hasher.putLong(localDate.toEpochDay());
//        }
//    }
}
