package dataparsedemo;

/**
 * Exception class for handling DataGroup and DataRecord parsing errors.
 *
 */
public class DataGroupParseException extends Throwable {
	private static final long serialVersionUID = 1L;
	private String message;

	public DataGroupParseException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
