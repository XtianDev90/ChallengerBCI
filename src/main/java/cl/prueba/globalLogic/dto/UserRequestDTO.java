package cl.prueba.globalLogic.dto;

import java.util.List;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import cl.prueba.globalLogic.util.Constantes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @Schema(name = Constantes.Request.USER_NAME, description = "Nombre de usuario.")
    private String name;

    @Schema(name = Constantes.Request.USER_EMAIL, description = "Email del usuario.", required = true)
    @Email
    @Pattern(regexp = Constantes.Pattern.EMAIL_PATTERN)
    @NotNull(message = "El Email es requerido.")
    @NotBlank(message = "El Email es requerido.")
    private String email;

    @Schema(name = Constantes.Request.USER_PASSWORD, description = "Password del usuario.", required = true)
    @Pattern(regexp = Constantes.Pattern.PASSWORD_PATTERN)
    @NotNull(message = "La password es requerido.")
    @NotBlank(message = "La password es requerido.")
    private String password;

    @Schema(name = Constantes.Request.USER_PHONES, description = "Lista de numeros telefonicos del usuario.")
    private Set<PhoneRequestDTO> phones;

}