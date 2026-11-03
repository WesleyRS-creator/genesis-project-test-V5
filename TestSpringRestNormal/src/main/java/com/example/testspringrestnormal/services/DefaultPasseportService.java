package com.example.testspringrestnormal.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import com.example.testspringrestnormal.models.Passeport;
import com.example.testspringrestnormal.models.dto.PasseportSearch;
import org.springframework.stereotype.Service;
import com.example.testspringrestnormal.repositories.PasseportRepository;
import java.util.Optional;
import com.example.testspringrestnormal.services.interfaces.PasseportService;
import com.example.testspringrestnormal.specification.PasseportSpecification;
import com.example.testspringrestnormal.exception.ResourceNotFoundException;
import com.example.testspringrestnormal.exception.InternalServerErrorException;
import com.example.testspringrestnormal.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultPasseportService implements PasseportService {
	private final PasseportRepository passeportRepository;

	public DefaultPasseportService(PasseportRepository passeportRepository) {
	   this.passeportRepository = passeportRepository;
	}

	@Override
	public String exportPasseportToCSV(List<Passeport> passeport) {
	   return ExportUtils.generateCsv(passeport);
	}  

	@Override
	public Page<Passeport> getAllPasseport(Pageable pageable) {
	    try {
	        return passeportRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving passeport", ex);
	    }
	}

	@Override
	public Page<Passeport> getAllPasseport(Pageable pageable, PasseportSearch object) {
	    try {
	        Specification<Passeport> spec=PasseportSpecification.filter(object);
	        return passeportRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving passeport", ex);
	    }
	}

	@Override
	public Passeport getPasseportById(Integer id) {
	    Optional<Passeport> passeport = passeportRepository.findById(id);
	    if (passeport.isPresent()) {
	        return passeport.get();
	    } else {
	        throw new ResourceNotFoundException("Passeport not found with id : " + id);
	    }
	}

	@Override
	public Passeport createPasseport(Passeport passeport) {
	    try {
	        return passeportRepository.save(passeport);
	    } catch (DataIntegrityViolationException ex) {
	        throw ex;
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating passeport", ex);
	    }
	}

	@Override
	public Passeport updatePasseport(Integer id, Passeport passeport) {
	    Optional<Passeport> existingPasseport = passeportRepository.findById(id);
	    if (existingPasseport.isPresent()) {
	        passeport.setId(id);
	        try {
	            return passeportRepository.save(passeport);
	    } catch (DataIntegrityViolationException ex) {
	            throw ex;    
	    } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating passeport", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Passeport not found with id : " + id);
	    }
	}

	@Override
	public void deletePasseport(Integer id) {
	    try {
	        passeportRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting passeport", ex);
	    }
	}



}
