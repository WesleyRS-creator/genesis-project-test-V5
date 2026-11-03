package com.example.testspringrestnormal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.testspringrestnormal.models.Projet;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjetRepository extends JpaRepository<Projet, Integer>, JpaSpecificationExecutor<Projet> {

}
