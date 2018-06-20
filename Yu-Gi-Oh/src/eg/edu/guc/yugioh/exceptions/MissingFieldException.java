package eg.edu.guc.yugioh.exceptions;

public class MissingFieldException extends UnexpectedFormatException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingFieldException(String a, int b){
		super(a,b);
	}
}
