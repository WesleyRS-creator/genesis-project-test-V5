package com.example.testspringrestnormal.models.dto;

import lombok.Getter;
import lombok.Setter;
import com.example.testspringrestnormal.models.Personne;



@Getter @Setter
public class AdresseSearch {

	private Integer id;

	private Personne idpersonnePersonne;
	
	private Double latitude;
	private Double latitudeMin;
	private Double latitudeMax;

	private String codePostal;
	private String ville;
	private String rue;
	
}
