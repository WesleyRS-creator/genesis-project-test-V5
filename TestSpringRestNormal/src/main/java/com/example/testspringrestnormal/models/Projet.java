package com.example.testspringrestnormal.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="projet")
public class Projet  {
	@Column(name="budget_estime", unique = false, nullable = true)
	private Double budgetEstime;
	@Column(name="date_debut", unique = false, nullable = true)
	private java.time.LocalDate dateDebut;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	@Column(name="nom_projet", unique = false, nullable = true)
	@Size(max = 100)
	private String nomProjet;
	

}
