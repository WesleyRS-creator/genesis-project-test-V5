package com.example.testspringrestnormal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.testspringrestnormal.models.Adresse;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import com.example.testspringrestnormal.models.Personne;



public interface AdresseRepository extends JpaRepository<Adresse, Integer>, JpaSpecificationExecutor<Adresse> {

List<Adresse> findByIdpersonnePersonne(Personne idpersonnePersonne);



}
