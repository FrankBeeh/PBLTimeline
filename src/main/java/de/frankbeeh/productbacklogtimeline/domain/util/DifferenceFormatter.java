package de.frankbeeh.productbacklogtimeline.domain.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.google.common.base.Strings;

import de.frankbeeh.productbacklogtimeline.domain.ComparedValue;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogDirection;
import de.frankbeeh.productbacklogtimeline.domain.Sprint;
import de.frankbeeh.productbacklogtimeline.domain.State;

/**
 * Responsibility:
 * <ul>
 * <li>Formats the differences between two values for different types.
 * </ul>
 */
public class DifferenceFormatter {
    private static final DecimalFormat DIFFERENCE_DOUBLE_FORMAT = new DecimalFormat("+0.0;-0.0");
    private static final DecimalFormat DIFFERENCE_LONG_FORMAT = new DecimalFormat("+0;-0");

    public static ComparedValue formatTextualDifference(String value, String referenceValue) {
        if (value == null) {
            return new ComparedValue(ProductBacklogDirection.Same, null);
        }
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(value.toString());
        ProductBacklogDirection direction = ProductBacklogDirection.Same;
        if (referenceValue != null) {
            if (!value.equals(referenceValue)) {
                stringBuilder.append("\n(").append(referenceValue).append(")");
                direction = ProductBacklogDirection.Changed;
            }
        } else {
            direction = ProductBacklogDirection.New;
            stringBuilder.append("\n(NEW)");
        }
        return new ComparedValue(direction, stringBuilder.toString());
    }

    public static ComparedValue formatProductBacklogRankDifference(Integer rank, Integer referenceRank) {
        final StringBuilder stringBuilder = new StringBuilder();
        String formattedEstimate = "";
        ProductBacklogDirection direction = ProductBacklogDirection.Same;
        if (rank != null) {
            formattedEstimate = rank.toString();
            stringBuilder.append(formattedEstimate);
            if (referenceRank != null) {
                final Integer difference = rank - referenceRank;
                if (difference != 0) {
                    final String formattedDifference = "(" + DIFFERENCE_LONG_FORMAT.format((long) difference) + ")";
                    rigthAllign(stringBuilder, formattedEstimate, formattedDifference);
                    if (difference < 0) {
                        direction = ProductBacklogDirection.Earlier;
                    } else {
                        direction = ProductBacklogDirection.Later;
                    }
                }
            } else {
                direction = ProductBacklogDirection.New;
                rigthAllign(stringBuilder, formattedEstimate, "(NEW)");
            }
        } else {
            if (referenceRank != null) {
                direction = ProductBacklogDirection.Changed;
            }
        }
        return new ComparedValue(direction, stringBuilder.toString());
    }

    public static ComparedValue formatDoubleDifference(Double value, Double referenceValue, boolean smallerIsEarlier) {
        if (value == null) {
            return new ComparedValue(ProductBacklogDirection.Same, null);
        }
        final StringBuilder stringBuilder = new StringBuilder();
        final String formattedEstimate = value.toString();
        stringBuilder.append(formattedEstimate);
        ProductBacklogDirection direction = ProductBacklogDirection.Same;
        if (referenceValue != null) {
            final Double difference = value - referenceValue;
            if (difference != 0) {
                final String formattedDifference = "(" + DIFFERENCE_DOUBLE_FORMAT.format(difference) + ")";
                rigthAllign(stringBuilder, formattedEstimate, formattedDifference);
                if (difference < 0) {
                    direction = smallerIsEarlier ? ProductBacklogDirection.Earlier : ProductBacklogDirection.Later;
                } else {
                    direction = smallerIsEarlier ? ProductBacklogDirection.Later : ProductBacklogDirection.Earlier;
                }
            }
        } else {
            rigthAllign(stringBuilder, formattedEstimate, "(NEW)");
            direction = ProductBacklogDirection.New;
        }
        return new ComparedValue(direction, stringBuilder.toString());
    }

    public static ComparedValue formatSprintDifference(Sprint sprint, Sprint referenceSprint) {
    	// TODO reactivate
//        if (sprint == null) {
//            return new ComparedValue(ProductBacklogDirection.Same, null);
//        }
//        final StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(formatTextualDifference(sprint.getName(), referenceSprint == null ? sprint.getName() : referenceSprint.getName()).getComparedValue());
//        if (sprint.getEndDate() != null) {
//            stringBuilder.append("\n");
//        }
//        final ComparedValue comparedLocalDate = formatJodaDateDifference(sprint.getEndDate(), referenceSprint == null ? null : referenceSprint.getEndDate());
//        stringBuilder.append(comparedLocalDate.getComparedValue());
//        return new ComparedValue(comparedLocalDate.getDirection(), stringBuilder.toString());
    	return new ComparedValue(ProductBacklogDirection.Same, "");
    }

	public static ComparedValue formatLocalDateDifference(final LocalDate endDate, final LocalDate referenceEndDate) {
        final StringBuilder stringBuilder = new StringBuilder();
        ProductBacklogDirection direction = ProductBacklogDirection.Same;
        if (endDate != null) {
            stringBuilder.append(DateConverter.formatLocalDate(endDate));
            if (referenceEndDate != null) {
                final long diffDays = ChronoUnit.DAYS.between(referenceEndDate, endDate);
                if (diffDays != 0) {
                    stringBuilder.append("\n(").append(DIFFERENCE_LONG_FORMAT.format(diffDays)).append("d)");
                    if (endDate.isAfter(referenceEndDate)) {
                        direction = ProductBacklogDirection.Later;
                    } else if (endDate.isBefore(referenceEndDate)) {
                        direction = ProductBacklogDirection.Earlier;
                    }
                }
            } else {
                stringBuilder.append("\n(NEW)");
                direction = ProductBacklogDirection.New;
            }
        }
        return new ComparedValue(direction, stringBuilder.toString());
    }

    public static ComparedValue formatStateDifference(State state, State referenceState) {
        return formatTextualDifference(state == null ? null : state.toString(), State.New.equals(referenceState)  ? null : referenceState.toString());
    }

	// TODO reactivate
	//    public static ComparedValue formatReleaseCriteriaDifference(ReleaseCriteria releaseCriteria, ReleaseCriteria referenceReleaseCriteria) {
//        return formatTextualDifference(releaseCriteria == null ? null : releaseCriteria.toString(), referenceReleaseCriteria == null ? null : referenceReleaseCriteria.toString());
//    }

    private static void rigthAllign(final StringBuilder stringBuilder, final String formattedEstimate, final String formattedDifference) {
        int count = formattedDifference.length() - formattedEstimate.length() + 1;
        if (count > 0) {
            stringBuilder.insert(0, Strings.repeat(" ", count));
            stringBuilder.append("\n");
        } else {
            stringBuilder.append("\n").append(Strings.repeat(" ", 3 - count));
        }
        stringBuilder.append(formattedDifference);
    }

	public static ComparedValue formatBigDecimalDifference(BigDecimal value,
			BigDecimal referenceValue, boolean smallerIsEarlier) {
        if (value == null) {
            return new ComparedValue(ProductBacklogDirection.Same, null);
        }
        final StringBuilder stringBuilder = new StringBuilder();
        final String formattedEstimate = value.toString();
        stringBuilder.append(formattedEstimate);
        ProductBacklogDirection direction = ProductBacklogDirection.Same;
        if (referenceValue != null) {
            final BigDecimal difference = value.subtract(referenceValue);
            if (difference.compareTo(BigDecimal.ZERO) == 0) {
                final String formattedDifference = "(" + DIFFERENCE_DOUBLE_FORMAT.format(difference) + ")";
                rigthAllign(stringBuilder, formattedEstimate, formattedDifference);
                if (difference.compareTo(BigDecimal.ZERO) < 0) {
                    direction = smallerIsEarlier ? ProductBacklogDirection.Earlier : ProductBacklogDirection.Later;
                } else {
                    direction = smallerIsEarlier ? ProductBacklogDirection.Later : ProductBacklogDirection.Earlier;
                }
            }
        } else {
            rigthAllign(stringBuilder, formattedEstimate, "(NEW)");
            direction = ProductBacklogDirection.New;
        }
        return new ComparedValue(direction, stringBuilder.toString());
	}
}
