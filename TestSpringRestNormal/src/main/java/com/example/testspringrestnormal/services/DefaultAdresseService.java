package com.example.testspringrestnormal.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import com.example.testspringrestnormal.models.Adresse;
import com.example.testspringrestnormal.models.dto.AdresseSearch;
import org.springframework.stereotype.Service;
import com.example.testspringrestnormal.repositories.AdresseRepository;
import java.util.Optional;
import com.example.testspringrestnormal.services.interfaces.AdresseService;
import com.example.testspringrestnormal.specification.AdresseSpecification;
import com.example.testspringrestnormal.exception.ResourceNotFoundException;
import com.example.testspringrestnormal.exception.InternalServerErrorException;
import com.example.testspringrestnormal.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultAdresseService implements AdresseService {
	private final AdresseRepository adresseRepository;

	public DefaultAdresseService(AdresseRepository adresseRepository) {
	   this.adresseRepository = adresseRepository;
	}

	@Override
	public String exportAdresseToCSV(List<Adresse> adresse) {
	   return ExportUtils.generateCsv(adresse);
	}  

	@Override
	public Page<Adresse> getAllAdresse(Pageable pageable) {
	    try {
	        return adresseRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving adresse", ex);
	    }
	}

	@Override
	public Page<Adresse> getAllAdresse(Pageable pageable, AdresseSearch object) {
	    try {
	        Specification<Adresse> spec=AdresseSpecification.filter(object);
	        return adresseRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving adresse", ex);
	    }
	}

	@Override
	public Adresse getAdresseById(Integer id) {
	    Optional<Adresse> adresse = adresseRepository.findById(id);
	    if (adresse.isPresent()) {
	        return adresse.get();
	    } else {
	        throw new ResourceNotFoundException("Adresse not found with id : " + id);
	    }
	}

	@Override
	public Adresse createAdresse(Adresse adresse) {
	    try {
	        return adresseRepository.save(adresse);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating adresse", ex);
	    }
	}

	@Override
	public Adresse updateAdresse(Integer id, Adresse adresse) {
	    Optional<Adresse> existingAdresse = adresseRepository.findById(id);
	    if (existingAdresse.isPresent()) {
	        adresse.setId(id);
	        try {
	            return adresseRepository.save(adresse);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating adresse", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Adresse not found with id : " + id);
	    }
	}

	@Override
	public void deleteAdresse(Integer id) {
	    try {
	        adresseRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting adresse", ex);
	    }
	}



}
