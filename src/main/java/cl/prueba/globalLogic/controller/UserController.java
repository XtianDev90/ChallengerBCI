package cl.prueba.globalLogic.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.prueba.globalLogic.dto.UserDTO;
import cl.prueba.globalLogic.entity.User;
import cl.prueba.globalLogic.service.IUserServices;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class UserController {

	protected final IUserServices userService;
	
	@PostMapping("/sign-up")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody User user){
		return ResponseEntity.ok().body(this.userService.createUser(user));
	}
	
	@GetMapping("/login/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable String id){
		return ResponseEntity.ok().body(this.userService.getUserById(id));
	}
	
}
