package cl.prueba.globalLogic.service;

public interface IPasswordService {

	String hash(String password);
    boolean verifyHash(String password, String hash);
}
