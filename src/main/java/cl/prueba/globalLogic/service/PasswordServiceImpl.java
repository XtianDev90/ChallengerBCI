package cl.prueba.globalLogic.service;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements IPasswordService{

	@Override
	public String hash(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	@Override
	public boolean verifyHash(String password, String hash) {
		return BCrypt.checkpw(password, hash);
	}
}
