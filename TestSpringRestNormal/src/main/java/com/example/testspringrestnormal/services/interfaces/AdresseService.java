package com.example.testspringrestnormal.services.interfaces;

import com.example.testspringrestnormal.models.Adresse;
import com.example.testspringrestnormal.models.dto.AdresseSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface AdresseService {
    Page<Adresse> getAllAdresse(Pageable pageable);

    Page<Adresse> getAllAdresse(Pageable pageable, AdresseSearch object);

    Adresse getAdresseById(Integer id);

    public String exportAdresseToCSV(List<Adresse> adresse);

    

    Adresse createAdresse(Adresse adresse);

    Adresse updateAdresse(Integer id, Adresse adresse);

    void deleteAdresse(Integer id);
    

}
