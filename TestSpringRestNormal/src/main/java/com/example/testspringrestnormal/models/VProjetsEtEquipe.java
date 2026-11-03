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
@Table(name="v_projets_et_equipes")
public class VProjetsEtEquipe  {
	@Id
	@Column(name="id_projet", unique = false, nullable = true)
	private Integer idProjet;
	@Column(name="nom_projet", unique = false, nullable = true)
	@Size(max = 100)
	private String nomProjet;
	@Column(name="date_debut", unique = false, nullable = true)
	private java.time.LocalDate dateDebut;
	@Column(name="budget_estime", unique = false, nullable = true)
	private Double budgetEstime;
	@Column(name="duree_jours", unique = false, nullable = true)
	private Integer dureeJours;
	@Column(name="nombre_participants", unique = false, nullable = true)
	private Long nombreParticipants;
	@Column(name="budget_par_participant", unique = false, nullable = true)
	private Double budgetParParticipant;
	@Column(name="liste_equipe", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String listeEquipe;
	@Column(name="roles_distribues", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String rolesDistribues;
	@Column(name="derniere_inscription", unique = false, nullable = true)
	private java.time.LocalDateTime derniereInscription;
	

}
