package duel.exception;

public class DeadDuelistException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;
	
	public DeadDuelistException(String message) {
		super(message);
	}

}
