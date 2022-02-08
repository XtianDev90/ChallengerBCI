package cl.prueba.globalLogic.service;

import java.util.Map;
import java.util.Optional;

import cl.prueba.globalLogic.dto.UserDTO;
import cl.prueba.globalLogic.entity.User;

public interface IUserServices {

	UserDTO createUser(User user);
	
	UserDTO getUserById(String id);
	
	UserDTO findByEmail(String email);
	
	Map<String, Object> getDetails(String email);
	
	UserDTO update(UserDTO userDTO);
	
	Optional<User> findByToken(String token);
}
