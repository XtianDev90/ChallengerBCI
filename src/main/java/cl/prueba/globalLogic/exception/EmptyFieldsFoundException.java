package cl.prueba.globalLogic.exception;

public class EmptyFieldsFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public EmptyFieldsFoundException(String mensaje) {
		super(mensaje);
	}
	
	public EmptyFieldsFoundException(String mensaje, Throwable throwable) {
		super(mensaje, throwable);
	}
}
