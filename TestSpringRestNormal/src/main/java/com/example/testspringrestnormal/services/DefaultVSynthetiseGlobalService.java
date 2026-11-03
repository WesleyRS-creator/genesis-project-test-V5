package com.example.testspringrestnormal.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import com.example.testspringrestnormal.models.VSynthetiseGlobal;
import com.example.testspringrestnormal.models.dto.VSynthetiseGlobalSearch;
import org.springframework.stereotype.Service;
import com.example.testspringrestnormal.repositories.VSynthetiseGlobalRepository;
import java.util.Optional;
import com.example.testspringrestnormal.services.interfaces.VSynthetiseGlobalService;
import com.example.testspringrestnormal.specification.VSynthetiseGlobalSpecification;
import com.example.testspringrestnormal.exception.ResourceNotFoundException;
import com.example.testspringrestnormal.exception.InternalServerErrorException;
import com.example.testspringrestnormal.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultVSynthetiseGlobalService implements VSynthetiseGlobalService {
	private final VSynthetiseGlobalRepository vSynthetiseGlobalRepository;

	public DefaultVSynthetiseGlobalService(VSynthetiseGlobalRepository vSynthetiseGlobalRepository) {
	   this.vSynthetiseGlobalRepository = vSynthetiseGlobalRepository;
	}

	@Override
	public String exportVSynthetiseGlobalToCSV(List<VSynthetiseGlobal> vSynthetiseGlobal) {
	   return ExportUtils.generateCsv(vSynthetiseGlobal);
	}  

	@Override
	public Page<VSynthetiseGlobal> getAllVSynthetiseGlobal(Pageable pageable) {
	    try {
	        return vSynthetiseGlobalRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving v synthetise global", ex);
	    }
	}

	@Override
	public Page<VSynthetiseGlobal> getAllVSynthetiseGlobal(Pageable pageable, VSynthetiseGlobalSearch object) {
	    try {
	        Specification<VSynthetiseGlobal> spec=VSynthetiseGlobalSpecification.filter(object);
	        return vSynthetiseGlobalRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving v synthetise global", ex);
	    }
	}

	@Override
	public VSynthetiseGlobal getVSynthetiseGlobalById(Integer idPersonne) {
	    Optional<VSynthetiseGlobal> vSynthetiseGlobal = vSynthetiseGlobalRepository.findById(idPersonne);
	    if (vSynthetiseGlobal.isPresent()) {
	        return vSynthetiseGlobal.get();
	    } else {
	        throw new ResourceNotFoundException("VSynthetiseGlobal not found with idPersonne : " + idPersonne);
	    }
	}






}
