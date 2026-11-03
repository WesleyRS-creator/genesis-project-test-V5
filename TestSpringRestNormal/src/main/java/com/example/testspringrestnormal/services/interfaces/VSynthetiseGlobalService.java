package com.example.testspringrestnormal.services.interfaces;

import com.example.testspringrestnormal.models.VSynthetiseGlobal;
import com.example.testspringrestnormal.models.dto.VSynthetiseGlobalSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface VSynthetiseGlobalService {
    Page<VSynthetiseGlobal> getAllVSynthetiseGlobal(Pageable pageable);

    Page<VSynthetiseGlobal> getAllVSynthetiseGlobal(Pageable pageable, VSynthetiseGlobalSearch object);

    VSynthetiseGlobal getVSynthetiseGlobalById(Integer idPersonne);

    public String exportVSynthetiseGlobalToCSV(List<VSynthetiseGlobal> vSynthetiseGlobal);


}
