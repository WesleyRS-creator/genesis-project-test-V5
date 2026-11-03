package com.example.testspringrestnormal.models.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ProjetSearch {

	private Double budgetEstime;
	private Double budgetEstimeMin;
	private Double budgetEstimeMax;

	private java.time.LocalDate dateDebut;
	private java.time.LocalDate dateDebutMin;
	private java.time.LocalDate dateDebutMax;

	private Integer id;

	private String nomProjet;
	
}
