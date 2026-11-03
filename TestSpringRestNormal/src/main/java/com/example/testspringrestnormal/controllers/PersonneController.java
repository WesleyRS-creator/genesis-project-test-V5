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
import com.example.testspringrestnormal.models.Personne;
import com.example.testspringrestnormal.models.dto.PersonneSearch;
import org.springframework.web.bind.annotation.*;
import com.example.testspringrestnormal.services.interfaces.PersonneService;
import com.example.testspringrestnormal.services.interfaces.AdresseService;
import com.example.testspringrestnormal.models.dto.AdresseSearch;
import com.example.testspringrestnormal.models.Adresse;
import com.example.testspringrestnormal.services.interfaces.ParticiperService;
import com.example.testspringrestnormal.models.dto.ParticiperSearch;
import com.example.testspringrestnormal.models.Participer;
import com.example.testspringrestnormal.services.interfaces.PasseportService;
import com.example.testspringrestnormal.models.dto.PasseportSearch;
import com.example.testspringrestnormal.models.Passeport;

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
@RequestMapping("/personnes")
@Tag(name = "Personne", description = "Personne Management APIs")
public class PersonneController  {
	private final PersonneService personneService;
private final AdresseService adresseService;
private final ParticiperService participerService;
private final PasseportService passeportService;


	public PersonneController(PersonneService personneService, AdresseService adresseService, ParticiperService participerService, PasseportService passeportService) {
	   this.personneService = personneService;
	this.adresseService = adresseService;
	this.participerService = participerService;
	this.passeportService = passeportService;
	
	}

	@Operation(
	    summary = "Retrieve all personne",
	    description = "Get a paginated and sorted list of personne items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of personne",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No personne found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Personne>>> getAllPersonnes(
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
	    Page<Personne> personnes = personneService.getAllPersonne(pageable);
	
	    String message = "personne retrieved successfully";
	
	    if(!personnes.hasContent()) {
	            message = "No personne found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Personne>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            personnes
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all personne",
	    description = "Get a paginated and sorted list of personne items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of personne",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No personne found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Personne>>> getAllPersonnes(
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
	    @RequestBody PersonneSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Personne> personnes = personneService.getAllPersonne(pageable, object);
	
	    String message = "personne retrieved successfully";
	
	    if(!personnes.hasContent()) {
	            message = "No personne found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Personne>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            personnes
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get personne by ID",
	    description = "Retrieve a specific personne item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the personne",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Personne not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Personne>> getPersonneById(
	    @Parameter(description = "ID of the personne to retrieve", required = true)
	    @PathVariable Integer id
	) {
	    Personne personne = personneService.getPersonneById(id);
	    RestResponse<Personne> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "personne retrieved successfully", personne);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export Personne to CSV",
	     description = "Generate a CSV file from a provided list of Personne objects."
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
	public ResponseEntity<byte[]> exportPersonneToCsv(@RequestBody List<Personne> personne) {
	  
	  String csvContent = personneService.exportPersonneToCSV(personne);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}
 

	@Operation(
	    summary = "Create new personne",
 
	    description = "Create a new personne item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Personne created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid personne supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createPersonne(
	    @Parameter(description = "Personne object to be created", required = true)
	    @RequestBody @Valid Personne personne
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
	    Personne newPersonne = personneService.createPersonne(personne);
	    RestResponse<Personne> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "personne created successfully", newPersonne);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing personne",

	    description = "Update an existing personne item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Personne updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Personne not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid personne supplied"
	    )
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePersonne(
	    @Parameter(description = "ID of the personne to update", required = true)
	    @PathVariable Integer id,
	    @Parameter(description = "Updated personne object", required = true)
	    @RequestBody @Valid Personne personne
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
	    Personne updatePersonne = personneService.updatePersonne(id, personne);
	    RestResponse<Personne> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "personne updated successfully", updatePersonne);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete personne",

	    description = "Delete a personne item by its ID. Returns success even if personne was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Personne deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deletePersonneById(
	    @Parameter(description = "ID of the personne to delete", required = true)
	    @PathVariable Integer id
	) {
		try {
			personneService.getPersonneById(id);
			personneService.deletePersonne(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"personne deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"personne already deleted or does not exist",
					null
				)
			);
		}
	}

@Operation(
  summary = "Get adresses attached with a personne ID",
  description = "Retrieve a list of adresses from a personne item ID"
)
@ApiResponses({
  @ApiResponse(
    responseCode = "200",
    description = "Adresses retrieved successfully",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
  ),
  @ApiResponse(
    responseCode = "404",
    description = "Personne not found with the provided ID"
  ),
  @ApiResponse(
    responseCode = "500",
    description = "Internal server error"
  )
})
@GetMapping("/{id}/adresses")
public ResponseEntity<?> getAdressesByPersonne(
  @Parameter(description = "ID of the personne to retrieve", required = true)
  @PathVariable Integer id,
  @Parameter(description = "Page number (0-indexed)", required = false)
  @RequestParam(defaultValue = "0") int page,
  @Parameter(description = "Size of the page", required = false)
  @RequestParam(defaultValue = "10") int size,
  @Parameter(description = "Sort order", required = false)
  @RequestParam(defaultValue = "id,asc", required = false) String sortParam
) {
try {
  Personne personne = personneService.getPersonneById(id);
  if (personne == null) {
    RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
      HttpStatus.NOT_FOUND,
      String.format("Personne not found with id: %s", id),
      null
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
  Sort sortObj = WebUtils.createSortObject(sortParam);
  Pageable pageable = PageRequest.of(page, size, sortObj);
  AdresseSearch object = new AdresseSearch();
  object.setIdpersonnePersonne(personne);
  
  Page<Adresse> adressesData = adresseService.getAllAdresse(pageable, object);
  
  RestResponse<Page<Adresse>> response = RestResponse.buildSuccessResponse(
    HttpStatus.OK,
    "Adresses retrieved successfully",
    adressesData
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
    String.format("Error while retrieving personne: %s", ex.getMessage()),
    null
  );
  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
}
}
@Operation(
  summary = "Get participers attached with a personne ID",
  description = "Retrieve a list of participers from a personne item ID"
)
@ApiResponses({
  @ApiResponse(
    responseCode = "200",
    description = "Participers retrieved successfully",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
  ),
  @ApiResponse(
    responseCode = "404",
    description = "Personne not found with the provided ID"
  ),
  @ApiResponse(
    responseCode = "500",
    description = "Internal server error"
  )
})
@GetMapping("/{id}/participers")
public ResponseEntity<?> getParticipersByPersonne(
  @Parameter(description = "ID of the personne to retrieve", required = true)
  @PathVariable Integer id,
  @Parameter(description = "Page number (0-indexed)", required = false)
  @RequestParam(defaultValue = "0") int page,
  @Parameter(description = "Size of the page", required = false)
  @RequestParam(defaultValue = "10") int size,
  @Parameter(description = "Sort order", required = false)
  @RequestParam(defaultValue = "id,asc", required = false) String sortParam
) {
try {
  Personne personne = personneService.getPersonneById(id);
  if (personne == null) {
    RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
      HttpStatus.NOT_FOUND,
      String.format("Personne not found with id: %s", id),
      null
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
  Sort sortObj = WebUtils.createSortObject(sortParam);
  Pageable pageable = PageRequest.of(page, size, sortObj);
  ParticiperSearch object = new ParticiperSearch();
  object.setIdpersonnePersonne(personne);
  
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
    String.format("Error while retrieving personne: %s", ex.getMessage()),
    null
  );
  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
}
}
@Operation(
  summary = "Get passeports attached with a personne ID",
  description = "Retrieve a list of passeports from a personne item ID"
)
@ApiResponses({
  @ApiResponse(
    responseCode = "200",
    description = "Passeports retrieved successfully",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
  ),
  @ApiResponse(
    responseCode = "404",
    description = "Personne not found with the provided ID"
  ),
  @ApiResponse(
    responseCode = "500",
    description = "Internal server error"
  )
})
@GetMapping("/{id}/passeports")
public ResponseEntity<?> getPasseportsByPersonne(
  @Parameter(description = "ID of the personne to retrieve", required = true)
  @PathVariable Integer id,
  @Parameter(description = "Page number (0-indexed)", required = false)
  @RequestParam(defaultValue = "0") int page,
  @Parameter(description = "Size of the page", required = false)
  @RequestParam(defaultValue = "10") int size,
  @Parameter(description = "Sort order", required = false)
  @RequestParam(defaultValue = "id,asc", required = false) String sortParam
) {
try {
  Personne personne = personneService.getPersonneById(id);
  if (personne == null) {
    RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
      HttpStatus.NOT_FOUND,
      String.format("Personne not found with id: %s", id),
      null
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
  Sort sortObj = WebUtils.createSortObject(sortParam);
  Pageable pageable = PageRequest.of(page, size, sortObj);
  PasseportSearch object = new PasseportSearch();
  object.setIdpersonnePersonne(personne);
  
  Page<Passeport> passeportsData = passeportService.getAllPasseport(pageable, object);
  
  RestResponse<Page<Passeport>> response = RestResponse.buildSuccessResponse(
    HttpStatus.OK,
    "Passeports retrieved successfully",
    passeportsData
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
    String.format("Error while retrieving personne: %s", ex.getMessage()),
    null
  );
  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
}
}


}
