package dataparsedemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataParseDemo {

	public static void main(String[] args) {
		if (args.length > 0) {
			try {
				/*
				 * File file = new File(args[0]);
				 * System.out.println(file.exists()); List<String> lines =
				 * Files.readAllLines(Paths.get(args[0]),
				 * StandardCharsets.US_ASCII); for (String line : lines) {
				 * System.out.println(line); }
				 */
				Scanner sc = new Scanner(new FileInputStream(args[0]));

				if (sc.hasNext()) {
					ArrayList<String> nextData = DataGroupUtility.getDataTokens(sc.nextLine());
					if (nextData.size() == 1) {
						int numSamples = Integer.parseInt(nextData.get(0));

						int i = 0;
						while (sc.hasNext() && i < numSamples) {
							try {
								getDataSampleGroup(sc);
								i++;
							} catch (DataGroupParseException e) {
								System.err.println(e.getMessage() + System.lineSeparator());
							}
						}
					}
				}
			} catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
				System.err.println(e.getMessage() + System.lineSeparator());
			}
		}
	}

	/**
	 * Reads each data sample format, then calls a function to parse each data
	 * record from the text file
	 * 
	 * @param sc
	 * @throws IOException
	 * @throws DataGroupParseException
	 */
	private static void getDataSampleGroup(Scanner sc) throws IOException, DataGroupParseException {
		if (sc.hasNext()) {
			DataGroup group = new DataGroup(sc.nextLine());

			if (group.getCapacity() > 0) {
				group = getDataRecords(sc, group);

				// Output only successful data group parses
				System.out.println("Results for group" + System.lineSeparator() + group);
				group.printDataRecordResults();

				String totalRecordsOutput = "";

				if (group.getCapacity() > 1) {
					totalRecordsOutput += group.getCapacity() + " records processed.";
				} else {
					totalRecordsOutput += "1 record processed.";
				}

				System.out.println(totalRecordsOutput + System.lineSeparator());
			}
		}
	}

	/**
	 * Parses all the data records in one data sample group from the text file,
	 * and stores the data in the records parameter.
	 * 
	 * @param sc
	 * @param group
	 * @throws DataGroupParseException
	 */
	private static DataGroup getDataRecords(Scanner sc, DataGroup group) throws DataGroupParseException {
		int numRecordsParsed = 0;

		while (sc.hasNext() && numRecordsParsed < group.getCapacity()) {
			String textLine = sc.nextLine();
			DataRecord rec = DataGroupUtility.createDataRecord(group.getElementsPerRecord(), textLine);

			if (rec.isValidRecord()) {
				group.add(rec);
				numRecordsParsed++;
			} else {
				// Error occurred in creating data record: reject data group
				throw new DataGroupParseException(
						"Error in creating data record: Reject the data group starting as follows:"
								+ System.lineSeparator() + group + "The record given by the text \"" + textLine
								+ "\" is in an invalid format.");
			}
		}

		if (group.getCapacity() != numRecordsParsed) {
			// Discrepancy in data counts: reject data group
			throw new DataGroupParseException(
					"Error in creating data record: Reject the following data group:" + System.lineSeparator() + group
							+ "This data group does not contain the number of expected data records.");
		}

		return group;
	}
}
