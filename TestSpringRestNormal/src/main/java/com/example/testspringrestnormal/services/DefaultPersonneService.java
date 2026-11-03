package com.example.testspringrestnormal.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import com.example.testspringrestnormal.models.Personne;
import com.example.testspringrestnormal.models.dto.PersonneSearch;
import org.springframework.stereotype.Service;
import com.example.testspringrestnormal.repositories.PersonneRepository;
import java.util.Optional;
import com.example.testspringrestnormal.services.interfaces.PersonneService;
import com.example.testspringrestnormal.specification.PersonneSpecification;
import com.example.testspringrestnormal.exception.ResourceNotFoundException;
import com.example.testspringrestnormal.exception.InternalServerErrorException;
import com.example.testspringrestnormal.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultPersonneService implements PersonneService {
	private final PersonneRepository personneRepository;

	public DefaultPersonneService(PersonneRepository personneRepository) {
	   this.personneRepository = personneRepository;
	}

	@Override
	public String exportPersonneToCSV(List<Personne> personne) {
	   return ExportUtils.generateCsv(personne);
	}  

	@Override
	public Page<Personne> getAllPersonne(Pageable pageable) {
	    try {
	        return personneRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving personne", ex);
	    }
	}

	@Override
	public Page<Personne> getAllPersonne(Pageable pageable, PersonneSearch object) {
	    try {
	        Specification<Personne> spec=PersonneSpecification.filter(object);
	        return personneRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving personne", ex);
	    }
	}

	@Override
	public Personne getPersonneById(Integer id) {
	    Optional<Personne> personne = personneRepository.findById(id);
	    if (personne.isPresent()) {
	        return personne.get();
	    } else {
	        throw new ResourceNotFoundException("Personne not found with id : " + id);
	    }
	}

	@Override
	public Personne createPersonne(Personne personne) {
	    try {
	        return personneRepository.save(personne);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating personne", ex);
	    }
	}

	@Override
	public Personne updatePersonne(Integer id, Personne personne) {
	    Optional<Personne> existingPersonne = personneRepository.findById(id);
	    if (existingPersonne.isPresent()) {
	        personne.setId(id);
	        try {
	            return personneRepository.save(personne);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating personne", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Personne not found with id : " + id);
	    }
	}

	@Override
	public void deletePersonne(Integer id) {
	    try {
	        personneRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting personne", ex);
	    }
	}



}
