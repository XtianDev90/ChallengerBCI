package cl.prueba.globalLogic.service;

import static java.time.ZoneOffset.UTC;
import static java.util.Collections.emptyList;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import cl.prueba.globalLogic.dto.PhoneRequestDTO;
import cl.prueba.globalLogic.dto.UserRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.prueba.globalLogic.dto.UserResponseDTO;
import cl.prueba.globalLogic.entity.Phone;
import cl.prueba.globalLogic.entity.User;
import cl.prueba.globalLogic.exception.AlreadyExistException;
import cl.prueba.globalLogic.exception.ResourceNotFoundException;
import cl.prueba.globalLogic.repository.PhoneRepository;
import cl.prueba.globalLogic.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserServicesImpl implements IUserServices, UserDetailsService{

	protected final UserRepository userRepo;
	protected final PhoneRepository phoneRepo;
	protected final IPasswordService passwordService;
	protected final ModelMapper modelMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserResponseDTO createUser(UserRequestDTO userReq) {
		UserResponseDTO response = null;
		User user = modelMapper.map(userReq, User.class);
		user.setPassword(passwordService.hash(userReq.getPassword()));
		user.setToken(createToken(userReq.getEmail()));
		user.setCreated(LocalDateTime.now());
		user.setLastLogin(LocalDateTime.now());
		user.setActive(true);
		
		Optional<User> existe = this.userRepo.findByEmail(user.getEmail());
		if(existe.isPresent()){
			throw new AlreadyExistException("Usuario ya existe con email : " + userReq.getEmail());
			
		}

		User userDb = this.userRepo.save(user);
		Set<PhoneRequestDTO> phones = userReq.getPhones();
		userDb.getPhones().addAll((phones.stream().map(phone -> {

			Phone telefono = new Phone();
			telefono.setCitycode(phone.getCitycode());
			telefono.setContrycode(phone.getContrycode().toUpperCase().trim());
			telefono.setNumber(phone.getNumber());
			telefono.setUser(userDb);
			phoneRepo.save(telefono);
			return telefono;

		}).collect(Collectors.toList())));

		User userSaved = this.userRepo.save(userDb);
		return modelMapper.map(userSaved, UserResponseDTO.class);
	}

	@Override
	public UserResponseDTO getUserById(String id) {
		UUID uuid = UUID.fromString(id);
		
		Optional<User> userDb = this.userRepo.findByIdUser(uuid);
		if(userDb.isPresent()){
			return modelMapper.map(userDb.get(), UserResponseDTO.class);
		}else {
			throw new ResourceNotFoundException("Usuario no encontrado con id : " + id);
		}
	}

	@Override
    public UserResponseDTO findByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(new User());
        return modelMapper.map(user, UserResponseDTO.class);
    }
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserResponseDTO userResponseDto = findByEmail(email);
		User user = modelMapper.map(userResponseDto, User.class);
		UUID uuid = UUID.fromString(userResponseDto.getIdUser());
		user.setIdUser(uuid);
        if (user.getEmail() == null) {
            throw new UsernameNotFoundException(email);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.isActive(),true,true,true , emptyList());
	}
	
	@Override
    public Map<String, Object> getDetails(String email) {
        Map<String, Object> details = new HashMap<>();
        UserDetails userDetails = loadUserByUsername(email);
        if (userDetails != null) {
            UserResponseDTO userResponseDTO = findByEmail(email);
            details.put("user", userResponseDTO);
            details.put("userDetails", userDetails);
        }
        return details;
    }
	
	@Override
    public UserResponseDTO update(UserResponseDTO userResponseDto) {
		User user = modelMapper.map(userResponseDto, User.class);
		UUID uuid = UUID.fromString(userResponseDto.getIdUser());
		user.setIdUser(uuid);
        return modelMapper.map(userRepo.save(user), UserResponseDTO.class);
    }
	
	@Override
    public Optional<User> findByToken(String token) {
        return userRepo.findByToken(token);
    }
	
	private String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        Date expiration = Date.from(LocalDateTime.now(UTC).plusMinutes(60).toInstant(UTC));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, "secretKey")
                .compact();
    }
}
