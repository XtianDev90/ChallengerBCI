package cl.prueba.globalLogic.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String mensaje) {
		super(mensaje);
	}
	
	public ResourceNotFoundException(String mensaje, Throwable throwable) {
		super(mensaje, throwable);
	}
}
