package com.example.testspringrestnormal.models.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class VProjetsEtEquipeSearch {

	private Integer idProjet;

	private String nomProjet;
	private java.time.LocalDate dateDebut;
	private java.time.LocalDate dateDebutMin;
	private java.time.LocalDate dateDebutMax;

	private Double budgetEstime;
	private Double budgetEstimeMin;
	private Double budgetEstimeMax;

	private Integer dureeJours;
	private Integer dureeJoursMin;
	private Integer dureeJoursMax;

	private Long nombreParticipants;
	private Long nombreParticipantsMin;
	private Long nombreParticipantsMax;

	private Double budgetParParticipant;
	private Double budgetParParticipantMin;
	private Double budgetParParticipantMax;

	private String listeEquipe;
	private String rolesDistribues;
	private java.time.LocalDateTime derniereInscription;
	private java.time.LocalDateTime derniereInscriptionMin;
	private java.time.LocalDateTime derniereInscriptionMax;

	
}
