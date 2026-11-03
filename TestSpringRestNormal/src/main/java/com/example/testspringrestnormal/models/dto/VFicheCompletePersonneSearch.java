package com.example.testspringrestnormal.models.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class VFicheCompletePersonneSearch {

	private Integer idPersonne;

	private String nom;
	private String prenom;
	private java.time.LocalDate dateNaissance;
	private java.time.LocalDate dateNaissanceMin;
	private java.time.LocalDate dateNaissanceMax;

	private Integer age;
	private Integer ageMin;
	private Integer ageMax;

	private String biographie;
	private Long numeroSs;
	private Long numeroSsMin;
	private Long numeroSsMax;

	private String numeroPasseport;
	private java.time.LocalDate dateExpiration;
	private java.time.LocalDate dateExpirationMin;
	private java.time.LocalDate dateExpirationMax;

	private String statutPasseport;
	private Long nombreAdresses;
	private Long nombreAdressesMin;
	private Long nombreAdressesMax;

	private String villePrincipale;
	private Long nombreProjets;
	private Long nombreProjetsMin;
	private Long nombreProjetsMax;

	
}
