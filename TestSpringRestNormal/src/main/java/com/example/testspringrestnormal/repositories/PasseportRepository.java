package com.example.testspringrestnormal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.testspringrestnormal.models.Passeport;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import com.example.testspringrestnormal.models.Personne;



public interface PasseportRepository extends JpaRepository<Passeport, Integer>, JpaSpecificationExecutor<Passeport> {

List<Passeport> findByIdpersonnePersonne(Personne idpersonnePersonne);



}
