package eg.edu.guc.yugioh.exceptions;

public class UnknownCardTypeException extends UnexpectedFormatException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String unknownType;
	public UnknownCardTypeException(String a, int b, String c){
		super(a,b);
		this.unknownType=c;
		
	}
	public String getUnknownType() {
		return unknownType;
	}
	public void setUnknownType(String unknownType) {
		this.unknownType = unknownType;
	}
}
