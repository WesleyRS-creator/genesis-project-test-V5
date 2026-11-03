package com.example.testspringrestnormal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.testspringrestnormal.models.Participer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import com.example.testspringrestnormal.models.Personne;
import com.example.testspringrestnormal.models.Projet;



public interface ParticiperRepository extends JpaRepository<Participer, Integer>, JpaSpecificationExecutor<Participer> {

List<Participer> findByIdpersonnePersonne(Personne idpersonnePersonne);
List<Participer> findByIdprojetProjet(Projet idprojetProjet);



}
