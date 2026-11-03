package com.example.testspringrestnormal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.testspringrestnormal.models.VFicheCompletePersonne;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VFicheCompletePersonneRepository extends JpaRepository<VFicheCompletePersonne, Integer>, JpaSpecificationExecutor<VFicheCompletePersonne> {

}
