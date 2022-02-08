package cl.prueba.globalLogic.exception;

import java.io.Serializable;

public class MissingPathVariableException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;

	public MissingPathVariableException(String mensaje) {
		super(mensaje);
	}
	
	public MissingPathVariableException(String mensaje, Throwable throwable) {
		super(mensaje, throwable);
	}
}
