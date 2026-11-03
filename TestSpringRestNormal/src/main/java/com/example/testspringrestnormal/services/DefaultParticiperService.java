package com.example.testspringrestnormal.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import com.example.testspringrestnormal.models.Participer;
import com.example.testspringrestnormal.models.dto.ParticiperSearch;
import org.springframework.stereotype.Service;
import com.example.testspringrestnormal.repositories.ParticiperRepository;
import java.util.Optional;
import com.example.testspringrestnormal.services.interfaces.ParticiperService;
import com.example.testspringrestnormal.specification.ParticiperSpecification;
import com.example.testspringrestnormal.exception.ResourceNotFoundException;
import com.example.testspringrestnormal.exception.InternalServerErrorException;
import com.example.testspringrestnormal.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultParticiperService implements ParticiperService {
	private final ParticiperRepository participerRepository;

	public DefaultParticiperService(ParticiperRepository participerRepository) {
	   this.participerRepository = participerRepository;
	}

	@Override
	public String exportParticiperToCSV(List<Participer> participer) {
	   return ExportUtils.generateCsv(participer);
	}  

	@Override
	public Page<Participer> getAllParticiper(Pageable pageable) {
	    try {
	        return participerRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving participer", ex);
	    }
	}

	@Override
	public Page<Participer> getAllParticiper(Pageable pageable, ParticiperSearch object) {
	    try {
	        Specification<Participer> spec=ParticiperSpecification.filter(object);
	        return participerRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving participer", ex);
	    }
	}

	@Override
	public Participer getParticiperById(Integer id) {
	    Optional<Participer> participer = participerRepository.findById(id);
	    if (participer.isPresent()) {
	        return participer.get();
	    } else {
	        throw new ResourceNotFoundException("Participer not found with id : " + id);
	    }
	}

	@Override
	public Participer createParticiper(Participer participer) {
	    try {
	        return participerRepository.save(participer);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating participer", ex);
	    }
	}

	@Override
	public Participer updateParticiper(Integer id, Participer participer) {
	    Optional<Participer> existingParticiper = participerRepository.findById(id);
	    if (existingParticiper.isPresent()) {
	        participer.setId(id);
	        try {
	            return participerRepository.save(participer);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating participer", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Participer not found with id : " + id);
	    }
	}

	@Override
	public void deleteParticiper(Integer id) {
	    try {
	        participerRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting participer", ex);
	    }
	}



}
