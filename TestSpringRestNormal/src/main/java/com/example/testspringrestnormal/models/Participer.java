package com.example.testspringrestnormal.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="participer")
public class Participer  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_personne")
	private Personne idpersonnePersonne;
	@ManyToOne
	@JoinColumn(name="id_projet")
	private Projet idprojetProjet;
	@Column(name="date_inscription", unique = false, nullable = true)
	private java.time.LocalDateTime dateInscription;
	@Column(name="role_dans_projet", unique = false, nullable = true)
	@Size(max = 50)
	private String roleDansProjet;
	

}
