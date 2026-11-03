package com.example.testspringrestnormal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.testspringrestnormal.models.VProjetsEtEquipe;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VProjetsEtEquipeRepository extends JpaRepository<VProjetsEtEquipe, Integer>, JpaSpecificationExecutor<VProjetsEtEquipe> {

}
