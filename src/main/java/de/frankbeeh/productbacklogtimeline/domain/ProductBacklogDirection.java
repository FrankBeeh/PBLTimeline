package de.frankbeeh.productbacklogtimeline.domain;

import java.util.ArrayList;
import java.util.List;

public enum ProductBacklogDirection {
    Earlier, Later, Same, New, Changed;

    public static List<String> getAllStyleClasses() {
        final List<String> styles = new ArrayList<String>();
        for (ProductBacklogDirection direction : values()) {
            styles.add(direction.toString());
        }
        return styles;
    }
}
