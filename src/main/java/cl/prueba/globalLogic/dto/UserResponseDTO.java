package cl.prueba.globalLogic.dto;

import java.time.LocalDateTime;
import java.util.Set;

import cl.prueba.globalLogic.entity.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

	private String idUser;
    private String name;
    private String email;
    private String password;
    private Set<Phone> phones;
	private LocalDateTime created;
	private LocalDateTime lastLogin;
	private String token;
	private boolean isActive;
}
