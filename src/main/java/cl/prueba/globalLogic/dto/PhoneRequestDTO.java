package cl.prueba.globalLogic.dto;

import cl.prueba.globalLogic.util.Constantes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneRequestDTO {

    @Schema(name = Constantes.Request.PHONE_NUMBER, description = "Numero de telefono.", required = false)
    private long number;
    @Schema(name = Constantes.Request.PHONE_CITYCODE, description = "Codigo de ciudad.", required = false)
    private int citycode;
    @Schema(name = Constantes.Request.PHONE_CONTRYCODE, description = "Codigo de pais.", required = false)
    private String contrycode;

}


