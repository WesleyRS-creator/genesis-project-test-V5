package com.example.testspringrestnormal.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="passeport")
public class Passeport  {
	@Column(name="date_expiration", unique = false, nullable = true)
	private java.time.LocalDate dateExpiration;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	@ManyToOne
	@JoinColumn(name="id_personne")
	@NotNull(message = "IdPersonne cannot be null")
	private Personne idpersonnePersonne;
	@Column(name="numero", unique = false, nullable = true)
	@Size(max = 20)
	private String numero;
	

}
