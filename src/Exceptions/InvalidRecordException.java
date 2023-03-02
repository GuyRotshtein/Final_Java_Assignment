package Exceptions;

public class InvalidRecordException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public static String INVALID_SUM_MSG = "INVALID SUM";
	
	public InvalidRecordException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvalidRecordException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public InvalidRecordException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidRecordException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidRecordException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
//	public InvalidRecordException(int code) {
//		switch(code):
//			case 123:
//				super(INVALID_SUM_MSG);
//				break;
//	}
	
	

}
