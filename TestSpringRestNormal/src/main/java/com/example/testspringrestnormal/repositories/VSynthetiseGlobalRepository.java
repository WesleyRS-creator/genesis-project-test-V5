package com.example.testspringrestnormal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.testspringrestnormal.models.VSynthetiseGlobal;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VSynthetiseGlobalRepository extends JpaRepository<VSynthetiseGlobal, Integer>, JpaSpecificationExecutor<VSynthetiseGlobal> {

}
