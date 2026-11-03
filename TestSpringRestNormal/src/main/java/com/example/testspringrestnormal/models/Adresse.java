package com.example.testspringrestnormal.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="adresse")
public class Adresse  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_personne")
	private Personne idpersonnePersonne;
	@Column(name="latitude", unique = false, nullable = true)
	private Double latitude;
	@Column(name="code_postal", unique = false, nullable = true)
	@Size(max = 10)
	private String codePostal;
	@Column(name="ville", unique = false, nullable = true)
	@Size(max = 50)
	private String ville;
	@Column(name="rue", unique = false, nullable = true)
	@Size(max = 100)
	private String rue;
	

}
