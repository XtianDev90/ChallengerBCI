package cl.prueba.globalLogic.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import cl.prueba.globalLogic.util.Constantes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2731008251055983641L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
	        name = "UUID",
	        strategy = "org.hibernate.id.UUIDGenerator"
	)
	@ColumnDefault("random_uuid()")
	@Type(type = "uuid-char")
	@Column(name = "id_user")
	private UUID idUser;
	
	@NotEmpty(message = "Debes especificar el nombre del usuario.")
    private String name;
	
	@NotEmpty(message = "Debes especificar el email del usuario.")
	@Email
    @Column(unique = true)
    private String email;
	
	@NotEmpty(message = "Debes especificar la password del usuario.")
    @Pattern(regexp = Constantes.Pattern.PASSWORD_PATTERN)
    private String password;
	
	private LocalDateTime created;
	
	private LocalDateTime lastLogin;
	
	private String token;
	
	private boolean isActive;

	@PrePersist
    private void prePersist(){
        this.created = LocalDateTime.now();
    }
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Phone> phones;

}
