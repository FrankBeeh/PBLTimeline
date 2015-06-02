package de.frankbeeh.productbacklogtimeline.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class Comparison<L, I, C> {
    private L reference;
    private L selected;
    private final List<C> comparisons = new ArrayList<C>();

    public List<C> getComparisons() {
        return comparisons;
    }

    public void setSelected(L selected) {
        this.selected = selected;
        comparisons.clear();
        updateComparisons();
    }

    public void setReference(L reference) {
        this.reference = reference;
        comparisons.clear();
        updateComparisons();
    }

    protected L getSelected() {
        return selected;
    }

    protected L getReference() {
        return reference;
    }

    protected void addComparison(C comparison) {
        comparisons.add(comparison);
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    private void updateComparisons() {
        for (I item : getSelectedItems()) {
            if (getReference() == null) {
                addComparison(createComparisonWithSelf(item));
            } else {
                addComparison(createComparison(item));
            }
        }
    }

    protected abstract List<I> getSelectedItems();

    protected abstract C createComparison(I item);

    protected abstract C createComparisonWithSelf(I item);
}
