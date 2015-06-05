package de.frankbeeh.productbacklogtimeline.service.importer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import au.com.bytecode.opencsv.CSVReader;
import de.frankbeeh.productbacklogtimeline.domain.ProductBacklog;
import de.frankbeeh.productbacklogtimeline.domain.State;
import de.frankbeeh.productbacklogtimeline.domain.VelocityForecast;
import de.frankbeeh.productbacklogtimeline.domain.util.DateConverter;

public class ProductBacklogFromCsvImporter {
    private static final JiraProductBacklogItemsSortingStrategy SORTING_STRATEGY = new JiraProductBacklogItemsSortingStrategy();
	private static final String NAME_OF_RELEASE_COLUMN = "Fix Version/s";
    private static final String NAME_OF_ID_COLUMN = "Key";
    private static final String NAME_OF_TITLE_COLUMN = "Summary";
    private static final String NAME_OF_DESCRIPTION_COLUMN = "Description";
    private static final String NAME_OF_ESTIMATE_COLUMN = "Story Points";
    private static final String NAME_OF_RESOLUTION_COLUMN = "Resolution";
    private static final String NAME_OF_SPRINT_COLUMN = "Sprint";
    private static final String NAME_OF_RANK_COLUMN = "Rank";
	private static final String RESOLUTION_FIXED = "Fixed";
	private static final String RESOLUTION_UNRESOLVED = "Unresolved";
	private static final String RESOLUTION_WONT_FIX = "Won't Fix";
	private static final String RESOLUTION_DUPLICATE = "Duplicate";
	private static final char SPLIT_BY = ';';
	private static final Map<String, State> STATE_MAP = new HashMap<String, State>();

	private Map<String, Integer> mapColumnNameToColumnIndex;
	private String[] values;

	static {
		STATE_MAP.put(RESOLUTION_FIXED, State.Done);
		STATE_MAP.put(RESOLUTION_UNRESOLVED, State.Todo);
		STATE_MAP.put(RESOLUTION_WONT_FIX, State.Canceled);
		STATE_MAP.put(RESOLUTION_DUPLICATE, State.Canceled);
	}

	public final ProductBacklog importData(Reader reader, VelocityForecast velocityForecast) throws IOException {
		final List<JiraProductBacklogItem> list = new ArrayList<JiraProductBacklogItem>();
		final BufferedReader bufferedReader = new BufferedReader(reader);
		final CSVReader csvReader = new CSVReader(bufferedReader, SPLIT_BY);
		try {
			final String[] columnNames = csvReader.readNext();
			if (columnNames != null) {
				mapColumnNameToColumnIndex = mapColumnNameToColumnIndex(columnNames);
				while ((values = csvReader.readNext()) != null) {
					list.add(createItem());
				}
			}
		} finally {
			csvReader.close();
		}
		SORTING_STRATEGY.sortItems(list, velocityForecast);
		return new ProductBacklog(list);
	}

	protected final String getString(String columnName) {
		final Integer columnIndex = mapColumnNameToColumnIndex.get(columnName);
		if (columnIndex == null) {
			throw new RuntimeException("Column '" + columnName + "' missing!");
		}
		return values[columnIndex];
	}

	protected final BigDecimal getBigDecimal(String columnName) {
		final String value = getString(columnName);
		if (value.isEmpty()) {
			return null;
		}
		return new BigDecimal(value);
	}

	protected Integer getInteger(String columnName) {
		final String value = getString(columnName);
		if (value.isEmpty()) {
			return null;
		}
		return Integer.parseInt(value);
	}

	protected final LocalDate getLocalDate(String columnName) {
		final String value = getString(columnName);
		if (value.isEmpty()) {
			return null;
		}
		return DateConverter.parseLocalDate(value);
	}

	protected final State getState(String columnName) {
		return parseState(getString(columnName));
	}

	public static State parseState(final String string) {
		return STATE_MAP.get(string);
	}
    
    private JiraProductBacklogItem createItem() {
        final String id = getString(NAME_OF_ID_COLUMN);
        final String title = getString(NAME_OF_TITLE_COLUMN);
        final String description = getString(NAME_OF_DESCRIPTION_COLUMN);
        final BigDecimal estimate = getBigDecimal(NAME_OF_ESTIMATE_COLUMN);
        final State state = getState(NAME_OF_RESOLUTION_COLUMN);
        final String sprint = getString(NAME_OF_SPRINT_COLUMN);
        final String rank = getString(NAME_OF_RANK_COLUMN);
        final String plannedRelease = getString(NAME_OF_RELEASE_COLUMN);
        return new JiraProductBacklogItem(id, title, description, estimate, state, sprint, rank, plannedRelease);
    }
    
	private Map<String, Integer> mapColumnNameToColumnIndex(String[] columnNames) {
		final Map<String, Integer> map = new HashMap<String, Integer>();
		for (int columnIndex = 0; columnIndex < columnNames.length; columnIndex++) {
			final String columnName = columnNames[columnIndex];
			map.put(columnName, columnIndex);
		}
		return map;
	}
}
