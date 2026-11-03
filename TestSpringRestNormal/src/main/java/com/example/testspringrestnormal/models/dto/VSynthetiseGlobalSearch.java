package com.example.testspringrestnormal.models.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class VSynthetiseGlobalSearch {

	private Integer idPersonne;

	private String nomComplet;
	private String trancheAge;
	private Long totalParticipations;
	private Long totalParticipationsMin;
	private Long totalParticipationsMax;

	private String rolePrincipal;
	private Double budgetMoyenProjets;
	private Double budgetMoyenProjetsMin;
	private Double budgetMoyenProjetsMax;

	private Double budgetGlobalMoyen;
	private Double budgetGlobalMoyenMin;
	private Double budgetGlobalMoyenMax;

	private String positionBudget;
	private Long nombreVilles;
	private Long nombreVillesMin;
	private Long nombreVillesMax;

	private Double latitudeMoyenne;
	private Double latitudeMoyenneMin;
	private Double latitudeMoyenneMax;

	
}
