package dataparsedemo;

import java.util.ArrayList;

/**
 * This class stores information specifying a data sample group and its data
 * records.
 *
 */
public class DataGroup {

	private DataRecord[] dataRecords;

	/**
	 * Number of expected data records in group
	 */
	private int capacity;

	/**
	 * Used to add a new data record to the array
	 */
	private int arrayIndexPointer;

	/**
	 * Number of elements per data record: can be 2 or 3
	 */
	private int elementsPerRecord;

	/**
	 * This is the line of text that is used to parse a data record in a text
	 * file. It is used in the toString override.
	 */
	private String textLine;

	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @return the elementsPerRecord
	 */
	public int getElementsPerRecord() {
		return elementsPerRecord;
	}

	public DataGroup(String textLine) throws DataGroupParseException {
		if (textLine.startsWith(DataGroupUtility.DATA_GROUP_START_STR)) {
			this.textLine = textLine;
			ArrayList<String> nextData = DataGroupUtility.getDataTokens(textLine);
			parseDataGroup(nextData);
			dataRecords = new DataRecord[capacity];
			arrayIndexPointer = 0;
		} else {
			capacity = 0;
		}
	}

	/**
	 * This function parses a data group. If the sample is improperly formatted,
	 * this function outputs an error message.
	 * 
	 * @param nextData
	 * @throws DataGroupParseException
	 */
	private void parseDataGroup(ArrayList<String> nextData) throws DataGroupParseException {
		if (nextData.size() > 0) {
			capacity = Integer.parseInt(nextData.get(0));

			if (nextData.size() > 1) {
				elementsPerRecord = Integer.parseInt(nextData.get(1));
			} else {
				String message = "Error: Line \"" + textLine + "\" is an invalid data group sample format.";
				throw new DataGroupParseException(message);
			}
		} else {
			String message = "Error: Line \"" + textLine + "\" is an invalid data group sample format.";
			throw new DataGroupParseException(message);
		}
	}

	/**
	 * Retrieves the data record at the given index from the data records array
	 * if the index is within bounds.
	 * 
	 * @param index
	 * @return the data record at the given index
	 */
	public DataRecord get(int index) {
		if (index >= 0 && index < capacity) {
			return dataRecords[index];
		} else {
			throw new ArrayIndexOutOfBoundsException("Index " + index + " not in range.");
		}
	}

	/**
	 * Adds a record to the data records array, provided that there is still
	 * capacity available.
	 * 
	 * @param record
	 * @throws DataGroupParseException
	 */
	public void add(DataRecord record) throws DataGroupParseException {
		if (arrayIndexPointer < capacity) {
			dataRecords[arrayIndexPointer] = record;
			arrayIndexPointer++;
		} else {
			throw new DataGroupParseException("Error: number of records exceeds expected record size.");
		}
	}

	public void printDataRecordResults() {
		for (DataRecord r : dataRecords) {
			if (r.isValidRecord()) {
				r.printProcessedData();
			}
		}

		DataRecord.printResultAverage(dataRecords);
	}

	@Override
	public String toString() {
		String output = "DataGroup " + textLine + System.lineSeparator();
		for (DataRecord r : dataRecords) {
			if (r != null) {
				output += r + System.lineSeparator();
			}
		}

		return output;
	}
}
