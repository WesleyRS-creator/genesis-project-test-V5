package com.example.testspringrestnormal.models.dto;

import lombok.Getter;
import lombok.Setter;
import com.example.testspringrestnormal.models.Personne;

import com.example.testspringrestnormal.models.Projet;



@Getter @Setter
public class ParticiperSearch {

	private Integer id;

	private Personne idpersonnePersonne;
	
	private Projet idprojetProjet;
	
	private java.time.LocalDateTime dateInscription;
	private java.time.LocalDateTime dateInscriptionMin;
	private java.time.LocalDateTime dateInscriptionMax;

	private String roleDansProjet;
	
}
