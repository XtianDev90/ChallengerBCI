package cl.prueba.globalLogic.service;

import java.util.Map;
import java.util.Optional;

import cl.prueba.globalLogic.dto.UserRequestDTO;
import cl.prueba.globalLogic.dto.UserResponseDTO;
import cl.prueba.globalLogic.entity.User;

public interface IUserServices {

	UserResponseDTO createUser(UserRequestDTO user);
	
	UserResponseDTO getUserById(String id);
	
	UserResponseDTO findByEmail(String email);
	
	Map<String, Object> getDetails(String email);
	
	UserResponseDTO update(UserResponseDTO userResponseDTO);
	
	Optional<User> findByToken(String token);
}
