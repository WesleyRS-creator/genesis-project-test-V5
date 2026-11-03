package com.example.testspringrestnormal.services.interfaces;

import com.example.testspringrestnormal.models.Passeport;
import com.example.testspringrestnormal.models.dto.PasseportSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface PasseportService {
    Page<Passeport> getAllPasseport(Pageable pageable);

    Page<Passeport> getAllPasseport(Pageable pageable, PasseportSearch object);

    Passeport getPasseportById(Integer id);

    public String exportPasseportToCSV(List<Passeport> passeport);

    

    Passeport createPasseport(Passeport passeport);

    Passeport updatePasseport(Integer id, Passeport passeport);

    void deletePasseport(Integer id);
    

}
