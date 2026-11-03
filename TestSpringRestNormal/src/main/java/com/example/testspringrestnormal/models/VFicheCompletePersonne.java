package com.example.testspringrestnormal.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Setter
@Getter
@Entity
@Immutable
@Table(name="v_fiche_complete_personne")
public class VFicheCompletePersonne  {
	@Id
	@Column(name="id_personne", unique = false, nullable = true)
	private Integer idPersonne;
	@Column(name="nom", unique = false, nullable = true)
	@Size(max = 50)
	private String nom;
	@Column(name="prenom", unique = false, nullable = true)
	@Size(max = 50)
	private String prenom;
	@Column(name="date_naissance", unique = false, nullable = true)
	private java.time.LocalDate dateNaissance;
	@Column(name="age", unique = false, nullable = true)
	private Integer age;
	@Column(name="biographie", unique = false, nullable = true)
	@Size(max = 255)
	private String biographie;
	@Column(name="numero_ss", unique = false, nullable = true)
	private Long numeroSs;
	@Column(name="numero_passeport", unique = false, nullable = true)
	@Size(max = 20)
	private String numeroPasseport;
	@Column(name="date_expiration", unique = false, nullable = true)
	private java.time.LocalDate dateExpiration;
	@Column(name="statut_passeport", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String statutPasseport;
	@Column(name="nombre_adresses", unique = false, nullable = true)
	private Long nombreAdresses;
	@Column(name="ville_principale", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String villePrincipale;
	@Column(name="nombre_projets", unique = false, nullable = true)
	private Long nombreProjets;
	

}
