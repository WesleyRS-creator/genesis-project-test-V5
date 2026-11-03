package com.example.testspringrestnormal.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import com.example.testspringrestnormal.models.VProjetsEtEquipe;
import com.example.testspringrestnormal.models.dto.VProjetsEtEquipeSearch;
import org.springframework.stereotype.Service;
import com.example.testspringrestnormal.repositories.VProjetsEtEquipeRepository;
import java.util.Optional;
import com.example.testspringrestnormal.services.interfaces.VProjetsEtEquipeService;
import com.example.testspringrestnormal.specification.VProjetsEtEquipeSpecification;
import com.example.testspringrestnormal.exception.ResourceNotFoundException;
import com.example.testspringrestnormal.exception.InternalServerErrorException;
import com.example.testspringrestnormal.utils.ExportUtils;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;


@Service
public class DefaultVProjetsEtEquipeService implements VProjetsEtEquipeService {
	private final VProjetsEtEquipeRepository vProjetsEtEquipeRepository;

	public DefaultVProjetsEtEquipeService(VProjetsEtEquipeRepository vProjetsEtEquipeRepository) {
	   this.vProjetsEtEquipeRepository = vProjetsEtEquipeRepository;
	}

	@Override
	public String exportVProjetsEtEquipeToCSV(List<VProjetsEtEquipe> vProjetsEtEquipe) {
	   return ExportUtils.generateCsv(vProjetsEtEquipe);
	}  

	@Override
	public Page<VProjetsEtEquipe> getAllVProjetsEtEquipe(Pageable pageable) {
	    try {
	        return vProjetsEtEquipeRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving v projets et equipe", ex);
	    }
	}

	@Override
	public Page<VProjetsEtEquipe> getAllVProjetsEtEquipe(Pageable pageable, VProjetsEtEquipeSearch object) {
	    try {
	        Specification<VProjetsEtEquipe> spec=VProjetsEtEquipeSpecification.filter(object);
	        return vProjetsEtEquipeRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving v projets et equipe", ex);
	    }
	}

	@Override
	public VProjetsEtEquipe getVProjetsEtEquipeById(Integer idProjet) {
	    Optional<VProjetsEtEquipe> vProjetsEtEquipe = vProjetsEtEquipeRepository.findById(idProjet);
	    if (vProjetsEtEquipe.isPresent()) {
	        return vProjetsEtEquipe.get();
	    } else {
	        throw new ResourceNotFoundException("VProjetsEtEquipe not found with idProjet : " + idProjet);
	    }
	}






}
