package dataparsedemo;

import java.util.ArrayList;

/**
 * This class is a collection of functions used to parse and create a data
 * record or data sample group.
 *
 */
public class DataGroupUtility {

	public static final String DATA_GROUP_START_STR = "*";

	/**
	 * Given a line of text, this function generates a list of String tokens
	 * that are non-null and non-empty.
	 * 
	 * @param line
	 * @return the list of String tokens in the text line
	 */
	public static ArrayList<String> getDataTokens(String line) {
		ArrayList<String> nextData = new ArrayList<String>();

		for (String s : line.split("\\s+")) {
			if (s != null && s.length() > 0) {
				if (s.startsWith(DATA_GROUP_START_STR)) {
					s = s.substring(DATA_GROUP_START_STR.length());
				}
				nextData.add(s);
			}
		}
		return nextData;
	}

	/**
	 * Creates a new data record based on the number of data elements provided
	 * in the line of the text file.
	 * 
	 * @param numElementsPerRecord
	 * @param nextData:
	 *            the data elements for the record retrieved from the text file
	 *            line
	 * @return the new data record
	 * @throws DataGroupParseException
	 */
	public static DataRecord createDataRecord(int numElementsPerRecord, String recordLine) throws DataGroupParseException {
		DataRecord rec = null;

		ArrayList<String> textData = getDataTokens(recordLine);

		switch (numElementsPerRecord) {
		case 2:
			if (textData.size() == 2) {
				rec = new DataRecord(recordLine, textData);
			} else {
				rec = new DataRecord();
			}
			break;
		case 3:
			rec = new DataRecord3Param(recordLine, textData);
			break;
		default:
			rec = new DataRecord();
		}

		return rec;
	}
}
