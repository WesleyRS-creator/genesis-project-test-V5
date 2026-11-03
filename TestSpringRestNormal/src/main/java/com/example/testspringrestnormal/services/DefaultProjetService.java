package com.example.testspringrestnormal.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import com.example.testspringrestnormal.models.Projet;
import com.example.testspringrestnormal.models.dto.ProjetSearch;
import org.springframework.stereotype.Service;
import com.example.testspringrestnormal.repositories.ProjetRepository;
import java.util.Optional;
import com.example.testspringrestnormal.services.interfaces.ProjetService;
import com.example.testspringrestnormal.specification.ProjetSpecification;
import com.example.testspringrestnormal.exception.ResourceNotFoundException;
import com.example.testspringrestnormal.exception.InternalServerErrorException;
import com.example.testspringrestnormal.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultProjetService implements ProjetService {
	private final ProjetRepository projetRepository;

	public DefaultProjetService(ProjetRepository projetRepository) {
	   this.projetRepository = projetRepository;
	}

	@Override
	public String exportProjetToCSV(List<Projet> projet) {
	   return ExportUtils.generateCsv(projet);
	}  

	@Override
	public Page<Projet> getAllProjet(Pageable pageable) {
	    try {
	        return projetRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving projet", ex);
	    }
	}

	@Override
	public Page<Projet> getAllProjet(Pageable pageable, ProjetSearch object) {
	    try {
	        Specification<Projet> spec=ProjetSpecification.filter(object);
	        return projetRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving projet", ex);
	    }
	}

	@Override
	public Projet getProjetById(Integer id) {
	    Optional<Projet> projet = projetRepository.findById(id);
	    if (projet.isPresent()) {
	        return projet.get();
	    } else {
	        throw new ResourceNotFoundException("Projet not found with id : " + id);
	    }
	}

	@Override
	public Projet createProjet(Projet projet) {
	    try {
	        return projetRepository.save(projet);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating projet", ex);
	    }
	}

	@Override
	public Projet updateProjet(Integer id, Projet projet) {
	    Optional<Projet> existingProjet = projetRepository.findById(id);
	    if (existingProjet.isPresent()) {
	        projet.setId(id);
	        try {
	            return projetRepository.save(projet);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating projet", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Projet not found with id : " + id);
	    }
	}

	@Override
	public void deleteProjet(Integer id) {
	    try {
	        projetRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting projet", ex);
	    }
	}



}
