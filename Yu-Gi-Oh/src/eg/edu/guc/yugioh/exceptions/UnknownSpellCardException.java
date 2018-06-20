package eg.edu.guc.yugioh.exceptions;

public class UnknownSpellCardException extends UnexpectedFormatException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String unknownSpell;
	
	public UnknownSpellCardException(String sourceFile, int sourceLine, String c) {
		super(sourceFile, sourceLine);
		setUnknownSpell(c);
		
	}

	public String getUnknownSpell() {
		return unknownSpell;
	}

	public void setUnknownSpell(String unknownSpell) {
		this.unknownSpell = unknownSpell;
	}


	
}
