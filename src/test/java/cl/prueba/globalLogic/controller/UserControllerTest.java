package cl.prueba.globalLogic.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import cl.prueba.globalLogic.dto.UserDTO;
import cl.prueba.globalLogic.entity.Phone;
import cl.prueba.globalLogic.entity.User;
import cl.prueba.globalLogic.exception.AlreadyExistException;
import cl.prueba.globalLogic.exception.ResourceNotFoundException;
import cl.prueba.globalLogic.repository.PhoneRepository;
import cl.prueba.globalLogic.repository.UserRepository;
import cl.prueba.globalLogic.service.PasswordServiceImpl;
import cl.prueba.globalLogic.service.UserServicesImpl;

@SpringBootTest
@ActiveProfiles("test")
class UserControllerTest {
	
	
	private UserRepository userRepo = Mockito.mock(UserRepository.class);
	private PhoneRepository phoneRepo = Mockito.mock(PhoneRepository.class);
	private ModelMapper modelMapper = new ModelMapper();
	private PasswordServiceImpl password = new PasswordServiceImpl();
	private UserServicesImpl service = new UserServicesImpl(userRepo, phoneRepo, password,modelMapper);
	
	private UserController userController = new UserController(service);
	
	@BeforeEach
	void setUp() throws Exception {
		User user = createUser();
		
		Optional<User> userDb = Optional.of(user);
		
		Mockito.when(userRepo.findByIdUser(user.getIdUser())).thenReturn(userDb);
	}

	@Test
	void testCreateUser_OK() {
		User user = createUser();
		Mockito.when(userRepo.save(user)).thenReturn(user);
		
		ResponseEntity<UserDTO> response = userController.createUser(user);
		assertEquals(response.getBody().getEmail() , "Prueba@Dominio.cl");
	}
	
	@Test
	void testCreateUser_NOK() {
		User user = createUser();
		try{
			Optional<User> userExist = Optional.of(user);
			Mockito.when(userRepo.findByEmail(user.getEmail())).thenReturn(userExist);
			
			ResponseEntity<UserDTO> response = userController.createUser(user);
			
	    } catch (AlreadyExistException e) {
	    	assertThatExceptionOfType(AlreadyExistException.class)
	           .isThrownBy(() -> { throw new AlreadyExistException("Usuario ya existe con email : " + user.getEmail()); })
	           .withMessage("Usuario ya existe con email : " + user.getEmail());
	    }
	}

	@Test
	void testGetUserById_OK() {
		
		ResponseEntity<UserDTO> response = userController.getUserById("10a9f96b-2f68-4e7e-9463-93da18ef0cc2");
		assertEquals(response.getBody().getEmail() , "Prueba@Dominio.cl");
	}
	
	@Test
	void testGetUserById_NOK() {
		User user = createUser();
		try {
			
			Optional<User> userDb = Optional.empty();
			Mockito.when(userRepo.findByIdUser(user.getIdUser())).thenReturn(userDb);
			
			ResponseEntity<UserDTO> response = userController.getUserById("10a9f96b-2f68-4e7e-9463-93da18ef0cc2");
			
	    } catch (ResourceNotFoundException e) {
	    	assertThatExceptionOfType(ResourceNotFoundException.class)
	           .isThrownBy(() -> { throw new ResourceNotFoundException("Usuario no encontrado con id : " + user.getIdUser()); })
	           .withMessage("Usuario no encontrado con id : " + user.getIdUser());
	    }
	}
	
	private User createUser() {
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
		
		HashSet<Phone> phones = new HashSet<Phone>();
		phones.add(phone);
		
		user.setPhones(phones);
		
		return user;
	}

}
