package cl.prueba.globalLogic.service;

import static java.time.ZoneOffset.UTC;
import static java.util.Collections.emptyList;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.prueba.globalLogic.dto.UserDTO;
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
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserDTO createUser(User userReq) {
		UserDTO response = null;
		
		userReq.setPassword(passwordService.hash(userReq.getPassword()));
		userReq.setToken(createToken(userReq.getEmail()));
        
		userReq.setLastLogin(LocalDateTime.now());
		userReq.setActive(true);
		
		Optional<User> existe = this.userRepo.findByEmail(userReq.getEmail());
		if(existe.isPresent()){
			throw new AlreadyExistException("Usuario ya existe con email : " + userReq.getEmail());
			
		}

		User userDb = this.userRepo.save(userReq);
		Set<Phone> phones = userReq.getPhones();
		userReq.getPhones().addAll((phones.stream().map(phone -> {

			Phone telefono = new Phone();
			telefono.setCitycode(phone.getCitycode());
			telefono.setContrycode(phone.getContrycode().toUpperCase().trim());
			telefono.setNumber(phone.getNumber());
			telefono.setUser(userReq);
			phoneRepo.save(telefono);
			return telefono;

		}).collect(Collectors.toList())));

		User userSaved = this.userRepo.save(userReq);
		if(userDb != null) {
			response = modelMapper.map(userSaved, UserDTO.class);
		}
		return response;
	}

	@Override
	public UserDTO getUserById(String id) {
		UUID uuid = UUID.fromString(id);
		
		Optional<User> userDb = this.userRepo.findByIdUser(uuid);
		if(userDb.isPresent()){
			UserDTO response = modelMapper.map(userDb.get(), UserDTO.class);
			return response;
		}else {
			throw new ResourceNotFoundException("Usuario no encontrado con id : " + id);
		}
	}

	@Override
    public UserDTO findByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(new User());
        return modelMapper.map(user, UserDTO.class);
    }
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserDTO userDto = findByEmail(email);
		User user = modelMapper.map(userDto, User.class);
		UUID uuid = UUID.fromString(userDto.getIdUser());
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
            UserDTO userDTO = findByEmail(email);
            details.put("user", userDTO);
            details.put("userDetails", userDetails);
        }
        return details;
    }
	
	@Override
    public UserDTO update(UserDTO userDto) {
		User user = modelMapper.map(userDto, User.class);
		UUID uuid = UUID.fromString(userDto.getIdUser());
		user.setIdUser(uuid);
        return modelMapper.map(userRepo.save(user), UserDTO.class);
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
