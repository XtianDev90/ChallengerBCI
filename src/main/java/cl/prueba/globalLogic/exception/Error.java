package cl.prueba.globalLogic.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {

	private LocalDateTime timestamp;
	private int codigo;
	private String detail;
}
