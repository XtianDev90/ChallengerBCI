package cl.prueba.globalLogic.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Phone implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7277308678044231790L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_phone")
	private long idPhone;
	
    private long number;
	
    private int citycode;
	
    private String contrycode;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user", foreignKey = @ForeignKey(name = "fk_user_id_user"))
	@JsonIgnore
	private User user;
}
