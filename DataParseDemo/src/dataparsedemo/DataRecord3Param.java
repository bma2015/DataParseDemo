package dataparsedemo;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class stores data for a record with three elements and contains
 * functions for determining average and result output.
 *
 */
public class DataRecord3Param extends DataRecord {

	/**
	 * The number of acceptable parameters for a DataRecord3Param object
	 */
	public static final int NUM_ACCEPTABLE_PARAMS_DR3 = 3;
	
	public static final double FORMULA_ZETA = 3.2;

	protected int thirdValue;

	public DataRecord3Param(String textLine, ArrayList<String> textData) {
		super(textLine, textData);

		isValidRecord = false;
		if (textData.size() == NUM_ACCEPTABLE_PARAMS_DR3) {
			char thirdItem = textData.get(NUM_ACCEPTABLE_PARAMS_DR3 - 1).charAt(0);

			switch (thirdItem) {
			case 'b':
				this.thirdValue = 4;
				break;
			case 'c':
				this.thirdValue = 6;
				break;
			default:
				this.thirdValue = 3;
			}

			isValidRecord = true;
		}
	}

	public String outputAverage() {
		double average = (firstValue + secondValue + thirdValue) / 3.0;
		String output = new DecimalFormat(DEC_FORMAT_STR).format(average);
		return "AVG 3 = " + output;
	}

	/**
	 * Calculates the result using the 3-parameter data record formula and
	 * returns a string giving the result in the desired format.
	 * 
	 * @return a string giving the result in the desired format
	 */
	public String outputResult() {
		result = FORMULA_PI * firstValue + FORMULA_PSI * secondValue * secondValue
				+ Math.pow(FORMULA_ZETA, thirdValue) / secondValue;

		String output = new DecimalFormat(DEC_FORMAT_STR).format(result);
		return "FORM 3 = " + output;
	}
}
