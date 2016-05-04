package dataparsedemo;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class stores data for a record with two elements and contains functions
 * for determining average and result output.
 *
 */
public class DataRecord {

	public static final double FORMULA_PI = 3.14;
	public static final double FORMULA_PSI = 6.39485;

	protected static final String DEC_FORMAT_STR = "####.000";
	protected static final DecimalFormat DEC_FORMAT = new DecimalFormat("####.000");

	protected int firstValue;
	protected int secondValue;

	protected boolean isValidRecord;

	/**
	 * This is the line of text that is used to parse a data record in a text
	 * file. It is used in the toString override.
	 */
	protected String textLine;
	protected double result;

	/**
	 * @return the isValidRecord
	 */
	public boolean isValidRecord() {
		return isValidRecord;
	}

	public DataRecord() {
		this.isValidRecord = false;
	}
	
	public DataRecord(String textLine, ArrayList<String> textData) throws NumberFormatException {
		this.textLine = textLine;
		this.isValidRecord = false;

		if (textData.size() > 0) {
			this.firstValue = Integer.parseInt(textData.get(0));

			if (textData.size() > 1) {
				this.secondValue = Integer.parseInt(textData.get(1));
				this.isValidRecord = true;
			}
		}
	}

	/**
	 * Once the raw data for the record is parsed, this function should be
	 * called to calculate and print the processed data in the desired format.
	 */
	public void printProcessedData() {
		System.out.println(this.outputAverage());
		System.out.println(this.outputResult());
	}

	/**
	 * Calculates the average of the data record values and returns a string
	 * giving the average in the desired format.
	 * 
	 * @return a string giving the average in the desired format
	 */
	public String outputAverage() {
		double average = (firstValue + secondValue) / 2.0;
		String output = new DecimalFormat(DEC_FORMAT_STR).format(average);
		return "AVG = " + output;
	}

	/**
	 * Calculates the result using the 2-param data record formula and returns a
	 * string giving the result in the desired format.
	 * 
	 * @return a string giving the result in the desired format
	 */
	public String outputResult() {
		result = FORMULA_PI * firstValue + FORMULA_PSI * secondValue * secondValue;

		String output = new DecimalFormat(DEC_FORMAT_STR).format(result);
		return "FORM = " + output;
	}

	/**
	 * Calculates the average of the results stored in the given array of data
	 * records, and prints this average to the console.
	 * 
	 * @param dataRecords.length
	 */
	public static void printResultAverage(DataRecord[] dataRecords) {
		double sum = 0.0;

		for (DataRecord r : dataRecords) {
			sum += r.result;
		}

		double resultAvg = sum / dataRecords.length;
		String output = new DecimalFormat(DEC_FORMAT_STR).format(resultAvg);

		System.out.println("RESULT AVG = " + output);
	}

	@Override
	public String toString() {
		return "DataRecord " + textLine;
	}
}