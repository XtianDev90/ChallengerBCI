package cl.prueba.globalLogic.controller;

import cl.prueba.globalLogic.dto.UserRequestDTO;
import cl.prueba.globalLogic.dto.UserResponseDTO;
import cl.prueba.globalLogic.entity.Phone;
import cl.prueba.globalLogic.entity.User;
import cl.prueba.globalLogic.exception.AlreadyExistException;
import cl.prueba.globalLogic.exception.ResourceNotFoundException;
import cl.prueba.globalLogic.repository.PhoneRepository;
import cl.prueba.globalLogic.repository.UserRepository;
import cl.prueba.globalLogic.service.PasswordServiceImpl;
import cl.prueba.globalLogic.service.UserServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
class UserControllerTest {
	
	
	private final UserRepository userRepo = Mockito.mock(UserRepository.class);
	private final PhoneRepository phoneRepo = Mockito.mock(PhoneRepository.class);
	private final ModelMapper modelMapper = new ModelMapper();
	private final PasswordServiceImpl password = new PasswordServiceImpl();
	private final UserServicesImpl service = new UserServicesImpl(userRepo, phoneRepo, password,modelMapper);
	private final UserController userController = new UserController(service);

	@BeforeEach
	void setUp() {
		User validUser = createValidUser();
		Optional<User> userDb = Optional.of(validUser);
		Mockito.when(userRepo.findByIdUser(validUser.getIdUser())).thenReturn(userDb);
	}

	@Test
	void testCreateUser_OK() {
		User validUser = createValidUser();
		UserRequestDTO userRequest = modelMapper.map(validUser, UserRequestDTO.class);

		Mockito.when(userRepo.save(any(User.class))).thenReturn(validUser);

		ResponseEntity<UserResponseDTO> response = userController.createUser(userRequest);
		assertEquals(response.getBody().getEmail() , "Prueba@Dominio.cl");
	}
	
	@Test
	void testCreateUser_Exist() {
		User user = createValidUser();
		try{
			Optional<User> userExist = Optional.of(user);
			UserRequestDTO userRequest = modelMapper.map(user, UserRequestDTO.class);
			Mockito.when(userRepo.findByEmail(user.getEmail())).thenReturn(userExist);
			
			ResponseEntity<UserResponseDTO> response = userController.createUser(userRequest);
			
	    } catch (AlreadyExistException e) {
	    	assertThatExceptionOfType(AlreadyExistException.class)
	           .isThrownBy(() -> { throw new AlreadyExistException("Usuario ya existe con email : " + user.getEmail()); })
	           .withMessage("Usuario ya existe con email : " + user.getEmail());
	    }
	}

	@Test
	void testCreateUser_NOK() {
		User user = createInvalidUser();
		try{
			Optional<User> userExist = Optional.of(user);
			UserRequestDTO userRequest = modelMapper.map(user, UserRequestDTO.class);
			Mockito.when(userRepo.findByEmail(user.getEmail())).thenReturn(userExist);

			ResponseEntity<UserResponseDTO> response = userController.createUser(userRequest);

		} catch (Exception e) {
			assertThatExceptionOfType(NullPointerException.class)
					.isThrownBy(() -> { throw new NullPointerException("Campos en request incompletos."); })
					.withMessage("Campos en request incompletos.");
		}
	}
	@Test
	void testGetUserById_OK() {
		
		ResponseEntity<UserResponseDTO> response = userController.getUserById("10a9f96b-2f68-4e7e-9463-93da18ef0cc2");
		assertEquals(Objects.requireNonNull(response.getBody()).getEmail() , "Prueba@Dominio.cl");
	}
	
	@Test
	void testGetUserById_NOK() {
		User user = createValidUser();
		try {
			
			Optional<User> userDb = Optional.empty();
			Mockito.when(userRepo.findByIdUser(user.getIdUser())).thenReturn(userDb);
			
			ResponseEntity<UserResponseDTO> response = userController.getUserById("10a9f96b-2f68-4e7e-9463-93da18ef0cc2");
			
	    } catch (ResourceNotFoundException e) {
	    	assertThatExceptionOfType(ResourceNotFoundException.class)
	           .isThrownBy(() -> { throw new ResourceNotFoundException("Usuario no encontrado con id : " + user.getIdUser()); })
	           .withMessage("Usuario no encontrado con id : " + user.getIdUser());
	    }
	}
	
	private User createValidUser() {
		User user = new User();
		UUID uuid = UUID.fromString("10a9f96b-2f68-4e7e-9463-93da18ef0cc2");
		user.setIdUser(uuid);
		user.setName("Admin");
		user.setEmail("Prueba@Dominio.cl");
		user.setPassword("Admin");
		user.setToken("Falta implementar generador de token JWT");
		user.setCreated(LocalDateTime.now());
		user.setLastLogin(LocalDateTime.now());
		user.setActive(true);
		
		Phone phone = new Phone();
		phone.setCitycode(10);
		phone.setContrycode("RM");
		phone.setNumber(22545432);
		phone.setUser(user);
		
		HashSet<Phone> phones = new HashSet<>();
		phones.add(phone);
		
		user.setPhones(phones);
		
		return user;
	}

	private User createInvalidUser() {
		User user = new User();
		user.setName("Admin");
		user.setEmail(null);
		user.setPassword(null);
		user.setActive(true);

		Phone phone = new Phone();
		phone.setCitycode(10);
		phone.setContrycode("RM");
		phone.setNumber(22545432);
		phone.setUser(null);
		user.setPhones(null);

		return user;
	}

}
