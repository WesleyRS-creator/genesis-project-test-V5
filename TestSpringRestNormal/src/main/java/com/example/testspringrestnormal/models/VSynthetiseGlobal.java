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
@Table(name="v_synthetise_global")
public class VSynthetiseGlobal  {
	@Id
	@Column(name="id_personne", unique = false, nullable = true)
	private Integer idPersonne;
	@Column(name="nom_complet", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String nomComplet;
	@Column(name="tranche_age", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String trancheAge;
	@Column(name="total_participations", unique = false, nullable = true)
	private Long totalParticipations;
	@Column(name="role_principal", unique = false, nullable = true)
	@Size(max = 50)
	private String rolePrincipal;
	@Column(name="budget_moyen_projets", unique = false, nullable = true)
	private Double budgetMoyenProjets;
	@Column(name="budget_global_moyen", unique = false, nullable = true)
	private Double budgetGlobalMoyen;
	@Column(name="position_budget", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String positionBudget;
	@Column(name="nombre_villes", unique = false, nullable = true)
	private Long nombreVilles;
	@Column(name="latitude_moyenne", unique = false, nullable = true)
	private Double latitudeMoyenne;
	

}
