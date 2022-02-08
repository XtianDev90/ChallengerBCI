package cl.prueba.globalLogic.util;

public class Constantes {

	public static class Request{
		private Request() {}
		/**Datos de entrada**/
		public static final String USER_ID = "id";
		public static final String USER_NAME = "name";
		public static final String USER_EMAIL = "email";
		public static final String USER_PASSWORD = "password";
		public static final String USER_PHONES = "phones";
		public static final String PHONE_NUMBER = "number";
		public static final String PHONE_CITYCODE = "citycode";
		public static final String PHONE_CONTRYCODE = "contrycode";
	}
	
	public static class Pattern{
		private Pattern() {
			
		}
		/** Expresion regular para validar el correo electronico **/
		public static final String EMAIL_PATTERN = "/[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,4}/";
		/** Expresion regular para validar la password **/
		public static final String PASSWORD_PATTERN = "^(?=.{2,}[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,12}$";
		
	}
	
	public static class CodigoError{
        private CodigoError() {}
        
        public static final int BAD_REQUEST = 400;
        public static final int FORBIDDEN = 403;
        public static final int BAD_GATEWAY = 502;
        public static final int NOT_FOUND = 404;
        public static final int INTERNAL_SERVER_ERROR = 500;
    }
}
