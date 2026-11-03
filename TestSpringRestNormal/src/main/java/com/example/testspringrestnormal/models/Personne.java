package com.example.testspringrestnormal.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="personne")
public class Personne  {
	@Column(name="date_naissance", unique = false, nullable = true)
	private java.time.LocalDate dateNaissance;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	@Column(name="numero_ss", unique = false, nullable = true)
	private Long numeroSs;
	@Column(name="nom", unique = false, nullable = true)
	@Size(max = 50)
	private String nom;
	@Column(name="prenom", unique = false, nullable = true)
	@Size(max = 50)
	private String prenom;
	@Column(name="biographie", unique = false, nullable = true)
	@Size(max = 255)
	private String biographie;
	

}
