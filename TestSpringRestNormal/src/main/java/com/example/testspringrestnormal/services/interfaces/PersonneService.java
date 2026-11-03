package com.example.testspringrestnormal.services.interfaces;

import com.example.testspringrestnormal.models.Personne;
import com.example.testspringrestnormal.models.dto.PersonneSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    
import java.util.List;


public interface PersonneService {
    Page<Personne> getAllPersonne(Pageable pageable);

    Page<Personne> getAllPersonne(Pageable pageable, PersonneSearch object);

    Personne getPersonneById(Integer id);

    public String exportPersonneToCSV(List<Personne> personne);

    

    Personne createPersonne(Personne personne);

    Personne updatePersonne(Integer id, Personne personne);

    void deletePersonne(Integer id);
    

}
