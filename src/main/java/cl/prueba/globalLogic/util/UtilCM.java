package cl.prueba.globalLogic.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class UtilCM {

	public static String formatoLocalDateTime(LocalDateTime date) {
		DateTimeFormatter formateador =  DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy 'a las' hh:mm:ss")
			      .withLocale(new Locale("es", "ES"));
		return LocalDateTime.now().format(formateador);
	}
	
}
