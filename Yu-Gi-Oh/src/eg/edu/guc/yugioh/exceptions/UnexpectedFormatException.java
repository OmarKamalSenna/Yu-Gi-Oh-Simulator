package eg.edu.guc.yugioh.exceptions;

public class UnexpectedFormatException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sourceFile;
	private int sourceLine;
	public UnexpectedFormatException(String sourceFile, int sourceLine){
		super("error");
		this.setSourceFile(sourceFile);
		this.setSourceLine(sourceLine);
	}
	public String getSourceFile() {
		return sourceFile;
	}
	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
	public int getSourceLine() {
		return sourceLine;
	}
	public void setSourceLine(int sourceLine) {
		this.sourceLine = sourceLine;
	}
}