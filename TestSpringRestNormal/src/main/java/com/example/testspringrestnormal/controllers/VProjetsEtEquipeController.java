package com.example.testspringrestnormal.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.testspringrestnormal.models.VProjetsEtEquipe;
import com.example.testspringrestnormal.models.dto.VProjetsEtEquipeSearch;
import org.springframework.web.bind.annotation.*;
import com.example.testspringrestnormal.services.interfaces.VProjetsEtEquipeService;
import com.example.testspringrestnormal.utils.WebUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;

import com.example.testspringrestnormal.dto.RestResponse;
import com.example.testspringrestnormal.exception.ResourceNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/vprojetsetequipes")
@Tag(name = "V projets et equipe", description = "V projets et equipe Management APIs")
public class VProjetsEtEquipeController  {
	private final VProjetsEtEquipeService vprojetsetequipeService;

	public VProjetsEtEquipeController(VProjetsEtEquipeService vprojetsetequipeService) {
	   this.vprojetsetequipeService = vprojetsetequipeService;
	}

	@Operation(
	    summary = "Retrieve all v projets et equipe",
	    description = "Get a paginated and sorted list of v projets et equipe items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of v projets et equipe",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No v projets et equipe found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<VProjetsEtEquipe>>> getAllVProjetsEtEquipes(
	    @Parameter(description = "Page number (0-indexed)", example = "0")
	    @RequestParam(defaultValue = "0") int page,
	
	    @Parameter(description = "Size of the page", example = "10")
	    @RequestParam(defaultValue = "10") int size,
	
	    @Parameter(
	        description = "Sorting criteria in the format: property,(asc|desc). "
	                + "Multiple sort criteria can be passed using semicolon separator. "
	                + "Example: id,asc;name,desc",
	        example = "idProjet,asc"
	    )
	    @RequestParam(defaultValue = "idProjet,asc") String sortParam) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<VProjetsEtEquipe> vprojetsetequipes = vprojetsetequipeService.getAllVProjetsEtEquipe(pageable);
	
	    String message = "v projets et equipe retrieved successfully";
	
	    if(!vprojetsetequipes.hasContent()) {
	            message = "No v projets et equipe found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<VProjetsEtEquipe>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vprojetsetequipes
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all v projets et equipe",
	    description = "Get a paginated and sorted list of v projets et equipe items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of v projets et equipe",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No v projets et equipe found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<VProjetsEtEquipe>>> getAllVProjetsEtEquipes(
	    @Parameter(description = "Page number (0-indexed)", example = "0")
	    @RequestParam(defaultValue = "0") int page,
	
	    @Parameter(description = "Size of the page", example = "10")
	    @RequestParam(defaultValue = "10") int size,
	
	    @Parameter(
	        description = "Sorting criteria in the format: property,(asc|desc). "
	                + "Multiple sort criteria can be passed using semicolon separator. "
	                + "Example: idProjet,asc;name,desc",
	        example = "idProjet,asc"
	    )
	    @RequestParam(defaultValue = "idProjet,asc") String sortParam,
	    @RequestBody VProjetsEtEquipeSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<VProjetsEtEquipe> vprojetsetequipes = vprojetsetequipeService.getAllVProjetsEtEquipe(pageable, object);
	
	    String message = "v projets et equipe retrieved successfully";
	
	    if(!vprojetsetequipes.hasContent()) {
	            message = "No v projets et equipe found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<VProjetsEtEquipe>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vprojetsetequipes
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get v projets et equipe by ID",
	    description = "Retrieve a specific v projets et equipe item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the v projets et equipe",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "V projets et equipe not found with the provided ID"
	    )
	})
	@GetMapping("/{idProjet}")
	public ResponseEntity<RestResponse<VProjetsEtEquipe>> getVProjetsEtEquipeById(
	    @Parameter(description = "ID of the v projets et equipe to retrieve", required = true)
	    @PathVariable Integer idProjet
	) {
	    VProjetsEtEquipe vprojetsetequipe = vprojetsetequipeService.getVProjetsEtEquipeById(idProjet);
	    RestResponse<VProjetsEtEquipe> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "v projets et equipe retrieved successfully", vprojetsetequipe);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export V projets et equipe to CSV",
	     description = "Generate a CSV file from a provided list of V projets et equipe objects."
	)
	@ApiResponses({
	   @ApiResponse(
	   responseCode = "200",
	   description = "CSV file successfully generated",
	   content = @Content(mediaType = "text/csv")
	   ),
	   @ApiResponse(
	   responseCode = "400",
	   description = "Invalid or empty list provided"
	   )
	})
	@PostMapping(value = "/export/csv", consumes = "application/json", produces = "text/csv")
	public ResponseEntity<byte[]> exportVProjetsEtEquipeToCsv(@RequestBody List<VProjetsEtEquipe> vprojetsetequipe) {
	  
	  String csvContent = vprojetsetequipeService.exportVProjetsEtEquipeToCSV(vprojetsetequipe);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}





}
