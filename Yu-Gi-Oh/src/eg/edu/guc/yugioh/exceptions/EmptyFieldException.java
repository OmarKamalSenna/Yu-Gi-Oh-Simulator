package eg.edu.guc.yugioh.exceptions;

public class EmptyFieldException extends UnexpectedFormatException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int sourceField;
	public EmptyFieldException(String a, int b, int c){
		super(a,b);
		setSourceField(c);
	}
	public int getSourceField() {
		return sourceField;
	}
	public void setSourceField(int sourceField) {
		this.sourceField = sourceField;
	}
	

}
