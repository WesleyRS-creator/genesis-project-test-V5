package com.example.testspringrestnormal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.testspringrestnormal.models.Personne;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonneRepository extends JpaRepository<Personne, Integer>, JpaSpecificationExecutor<Personne> {

}
