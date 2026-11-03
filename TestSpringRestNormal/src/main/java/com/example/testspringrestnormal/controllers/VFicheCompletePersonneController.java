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
import com.example.testspringrestnormal.models.VFicheCompletePersonne;
import com.example.testspringrestnormal.models.dto.VFicheCompletePersonneSearch;
import org.springframework.web.bind.annotation.*;
import com.example.testspringrestnormal.services.interfaces.VFicheCompletePersonneService;
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
@RequestMapping("/vfichecompletepersonnes")
@Tag(name = "V fiche complete personne", description = "V fiche complete personne Management APIs")
public class VFicheCompletePersonneController  {
	private final VFicheCompletePersonneService vfichecompletepersonneService;

	public VFicheCompletePersonneController(VFicheCompletePersonneService vfichecompletepersonneService) {
	   this.vfichecompletepersonneService = vfichecompletepersonneService;
	}

	@Operation(
	    summary = "Retrieve all v fiche complete personne",
	    description = "Get a paginated and sorted list of v fiche complete personne items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of v fiche complete personne",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No v fiche complete personne found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<VFicheCompletePersonne>>> getAllVFicheCompletePersonnes(
	    @Parameter(description = "Page number (0-indexed)", example = "0")
	    @RequestParam(defaultValue = "0") int page,
	
	    @Parameter(description = "Size of the page", example = "10")
	    @RequestParam(defaultValue = "10") int size,
	
	    @Parameter(
	        description = "Sorting criteria in the format: property,(asc|desc). "
	                + "Multiple sort criteria can be passed using semicolon separator. "
	                + "Example: id,asc;name,desc",
	        example = "idPersonne,asc"
	    )
	    @RequestParam(defaultValue = "idPersonne,asc") String sortParam) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<VFicheCompletePersonne> vfichecompletepersonnes = vfichecompletepersonneService.getAllVFicheCompletePersonne(pageable);
	
	    String message = "v fiche complete personne retrieved successfully";
	
	    if(!vfichecompletepersonnes.hasContent()) {
	            message = "No v fiche complete personne found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<VFicheCompletePersonne>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vfichecompletepersonnes
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all v fiche complete personne",
	    description = "Get a paginated and sorted list of v fiche complete personne items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of v fiche complete personne",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No v fiche complete personne found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<VFicheCompletePersonne>>> getAllVFicheCompletePersonnes(
	    @Parameter(description = "Page number (0-indexed)", example = "0")
	    @RequestParam(defaultValue = "0") int page,
	
	    @Parameter(description = "Size of the page", example = "10")
	    @RequestParam(defaultValue = "10") int size,
	
	    @Parameter(
	        description = "Sorting criteria in the format: property,(asc|desc). "
	                + "Multiple sort criteria can be passed using semicolon separator. "
	                + "Example: idPersonne,asc;name,desc",
	        example = "idPersonne,asc"
	    )
	    @RequestParam(defaultValue = "idPersonne,asc") String sortParam,
	    @RequestBody VFicheCompletePersonneSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<VFicheCompletePersonne> vfichecompletepersonnes = vfichecompletepersonneService.getAllVFicheCompletePersonne(pageable, object);
	
	    String message = "v fiche complete personne retrieved successfully";
	
	    if(!vfichecompletepersonnes.hasContent()) {
	            message = "No v fiche complete personne found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<VFicheCompletePersonne>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vfichecompletepersonnes
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get v fiche complete personne by ID",
	    description = "Retrieve a specific v fiche complete personne item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the v fiche complete personne",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "V fiche complete personne not found with the provided ID"
	    )
	})
	@GetMapping("/{idPersonne}")
	public ResponseEntity<RestResponse<VFicheCompletePersonne>> getVFicheCompletePersonneById(
	    @Parameter(description = "ID of the v fiche complete personne to retrieve", required = true)
	    @PathVariable Integer idPersonne
	) {
	    VFicheCompletePersonne vfichecompletepersonne = vfichecompletepersonneService.getVFicheCompletePersonneById(idPersonne);
	    RestResponse<VFicheCompletePersonne> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "v fiche complete personne retrieved successfully", vfichecompletepersonne);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export V fiche complete personne to CSV",
	     description = "Generate a CSV file from a provided list of V fiche complete personne objects."
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
	public ResponseEntity<byte[]> exportVFicheCompletePersonneToCsv(@RequestBody List<VFicheCompletePersonne> vfichecompletepersonne) {
	  
	  String csvContent = vfichecompletepersonneService.exportVFicheCompletePersonneToCSV(vfichecompletepersonne);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}





}
