package cl.prueba.globalLogic.exception;

public class AlreadyExistException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AlreadyExistException(String mensaje) {
		super(mensaje);
	}
	
	public AlreadyExistException(String mensaje, Throwable throwable) {
		super(mensaje, throwable);
	}
}
