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
import com.example.testspringrestnormal.models.Projet;
import com.example.testspringrestnormal.models.dto.ProjetSearch;
import org.springframework.web.bind.annotation.*;
import com.example.testspringrestnormal.services.interfaces.ProjetService;
import com.example.testspringrestnormal.services.interfaces.ParticiperService;
import com.example.testspringrestnormal.models.dto.ParticiperSearch;
import com.example.testspringrestnormal.models.Participer;

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
@RequestMapping("/projets")
@Tag(name = "Projet", description = "Projet Management APIs")
public class ProjetController  {
	private final ProjetService projetService;
private final ParticiperService participerService;


	public ProjetController(ProjetService projetService, ParticiperService participerService) {
	   this.projetService = projetService;
	this.participerService = participerService;
	
	}

	@Operation(
	    summary = "Retrieve all projet",
	    description = "Get a paginated and sorted list of projet items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of projet",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No projet found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Projet>>> getAllProjets(
	    @Parameter(description = "Page number (0-indexed)", example = "0")
	    @RequestParam(defaultValue = "0") int page,
	
	    @Parameter(description = "Size of the page", example = "10")
	    @RequestParam(defaultValue = "10") int size,
	
	    @Parameter(
	        description = "Sorting criteria in the format: property,(asc|desc). "
	                + "Multiple sort criteria can be passed using semicolon separator. "
	                + "Example: id,asc;name,desc",
	        example = "id,asc"
	    )
	    @RequestParam(defaultValue = "id,asc") String sortParam) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Projet> projets = projetService.getAllProjet(pageable);
	
	    String message = "projet retrieved successfully";
	
	    if(!projets.hasContent()) {
	            message = "No projet found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Projet>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            projets
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all projet",
	    description = "Get a paginated and sorted list of projet items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of projet",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No projet found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Projet>>> getAllProjets(
	    @Parameter(description = "Page number (0-indexed)", example = "0")
	    @RequestParam(defaultValue = "0") int page,
	
	    @Parameter(description = "Size of the page", example = "10")
	    @RequestParam(defaultValue = "10") int size,
	
	    @Parameter(
	        description = "Sorting criteria in the format: property,(asc|desc). "
	                + "Multiple sort criteria can be passed using semicolon separator. "
	                + "Example: id,asc;name,desc",
	        example = "id,asc"
	    )
	    @RequestParam(defaultValue = "id,asc") String sortParam,
	    @RequestBody ProjetSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Projet> projets = projetService.getAllProjet(pageable, object);
	
	    String message = "projet retrieved successfully";
	
	    if(!projets.hasContent()) {
	            message = "No projet found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Projet>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            projets
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get projet by ID",
	    description = "Retrieve a specific projet item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the projet",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Projet not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Projet>> getProjetById(
	    @Parameter(description = "ID of the projet to retrieve", required = true)
	    @PathVariable Integer id
	) {
	    Projet projet = projetService.getProjetById(id);
	    RestResponse<Projet> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "projet retrieved successfully", projet);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export Projet to CSV",
	     description = "Generate a CSV file from a provided list of Projet objects."
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
	public ResponseEntity<byte[]> exportProjetToCsv(@RequestBody List<Projet> projet) {
	  
	  String csvContent = projetService.exportProjetToCSV(projet);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}
 

	@Operation(
	    summary = "Create new projet",
 
	    description = "Create a new projet item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Projet created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid projet supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createProjet(
	    @Parameter(description = "Projet object to be created", required = true)
	    @RequestBody @Valid Projet projet
	    ,BindingResult bindingResult
	) {
	    if (bindingResult.hasErrors()) {
	       HashMap<String, String> errors = new HashMap<>();
	       bindingResult.getFieldErrors().forEach(error -> {
	           errors.put(error.getField(), error.getDefaultMessage());
	       });
	       RestResponse<HashMap<String, String>> errorResponse =
	       RestResponse.buildErrorResponse(HttpStatus.BAD_REQUEST,
	       "Validation failed", errors);
	
	       return ResponseEntity.badRequest().body(errorResponse);
	    }
	    Projet newProjet = projetService.createProjet(projet);
	    RestResponse<Projet> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "projet created successfully", newProjet);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing projet",

	    description = "Update an existing projet item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Projet updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Projet not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid projet supplied"
	    )
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProjet(
	    @Parameter(description = "ID of the projet to update", required = true)
	    @PathVariable Integer id,
	    @Parameter(description = "Updated projet object", required = true)
	    @RequestBody @Valid Projet projet
	    ,BindingResult bindingResult
	) {
	    if (bindingResult.hasErrors()) {
	       HashMap<String, String> errors = new HashMap<>();
	       bindingResult.getFieldErrors().forEach(error -> {
	           errors.put(error.getField(), error.getDefaultMessage());
	       });
	       RestResponse<HashMap<String, String>> errorResponse =
	       RestResponse.buildErrorResponse(HttpStatus.BAD_REQUEST,
	       "Validation failed", errors);
	
	       return ResponseEntity.badRequest().body(errorResponse);
	    }
	    Projet updateProjet = projetService.updateProjet(id, projet);
	    RestResponse<Projet> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "projet updated successfully", updateProjet);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete projet",

	    description = "Delete a projet item by its ID. Returns success even if projet was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Projet deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteProjetById(
	    @Parameter(description = "ID of the projet to delete", required = true)
	    @PathVariable Integer id
	) {
		try {
			projetService.getProjetById(id);
			projetService.deleteProjet(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"projet deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"projet already deleted or does not exist",
					null
				)
			);
		}
	}

@Operation(
  summary = "Get participers attached with a projet ID",
  description = "Retrieve a list of participers from a projet item ID"
)
@ApiResponses({
  @ApiResponse(
    responseCode = "200",
    description = "Participers retrieved successfully",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
  ),
  @ApiResponse(
    responseCode = "404",
    description = "Projet not found with the provided ID"
  ),
  @ApiResponse(
    responseCode = "500",
    description = "Internal server error"
  )
})
@GetMapping("/{id}/participers")
public ResponseEntity<?> getParticipersByProjet(
  @Parameter(description = "ID of the projet to retrieve", required = true)
  @PathVariable Integer id,
  @Parameter(description = "Page number (0-indexed)", required = false)
  @RequestParam(defaultValue = "0") int page,
  @Parameter(description = "Size of the page", required = false)
  @RequestParam(defaultValue = "10") int size,
  @Parameter(description = "Sort order", required = false)
  @RequestParam(defaultValue = "id,asc", required = false) String sortParam
) {
try {
  Projet projet = projetService.getProjetById(id);
  if (projet == null) {
    RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
      HttpStatus.NOT_FOUND,
      String.format("Projet not found with id: %s", id),
      null
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
  Sort sortObj = WebUtils.createSortObject(sortParam);
  Pageable pageable = PageRequest.of(page, size, sortObj);
  ParticiperSearch object = new ParticiperSearch();
  object.setIdprojetProjet(projet);
  
  Page<Participer> participersData = participerService.getAllParticiper(pageable, object);
  
  RestResponse<Page<Participer>> response = RestResponse.buildSuccessResponse(
    HttpStatus.OK,
    "Participers retrieved successfully",
    participersData
  );
  return ResponseEntity.ok(response);
} catch (ResourceNotFoundException ex) {
  RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
    HttpStatus.NOT_FOUND,
    ex.getMessage(),
    null
  );
  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
} catch (Exception ex) {
  RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
    HttpStatus.INTERNAL_SERVER_ERROR,
    String.format("Error while retrieving projet: %s", ex.getMessage()),
    null
  );
  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
}
}


}
