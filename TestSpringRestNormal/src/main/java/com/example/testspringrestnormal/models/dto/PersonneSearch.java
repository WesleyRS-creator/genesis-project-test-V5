package com.example.testspringrestnormal.models.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PersonneSearch {

	private java.time.LocalDate dateNaissance;
	private java.time.LocalDate dateNaissanceMin;
	private java.time.LocalDate dateNaissanceMax;

	private Integer id;

	private Long numeroSs;
	private Long numeroSsMin;
	private Long numeroSsMax;

	private String nom;
	private String prenom;
	private String biographie;
	
}
