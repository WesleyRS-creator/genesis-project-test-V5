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
import com.example.testspringrestnormal.models.Adresse;
import com.example.testspringrestnormal.models.dto.AdresseSearch;
import org.springframework.web.bind.annotation.*;
import com.example.testspringrestnormal.services.interfaces.AdresseService;
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
@RequestMapping("/adresses")
@Tag(name = "Adresse", description = "Adresse Management APIs")
public class AdresseController  {
	private final AdresseService adresseService;

	public AdresseController(AdresseService adresseService) {
	   this.adresseService = adresseService;
	}

	@Operation(
	    summary = "Retrieve all adresse",
	    description = "Get a paginated and sorted list of adresse items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of adresse",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No adresse found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Adresse>>> getAllAdresses(
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
	    Page<Adresse> adresses = adresseService.getAllAdresse(pageable);
	
	    String message = "adresse retrieved successfully";
	
	    if(!adresses.hasContent()) {
	            message = "No adresse found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Adresse>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            adresses
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all adresse",
	    description = "Get a paginated and sorted list of adresse items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of adresse",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No adresse found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Adresse>>> getAllAdresses(
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
	    @RequestBody AdresseSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Adresse> adresses = adresseService.getAllAdresse(pageable, object);
	
	    String message = "adresse retrieved successfully";
	
	    if(!adresses.hasContent()) {
	            message = "No adresse found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Adresse>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            adresses
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get adresse by ID",
	    description = "Retrieve a specific adresse item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the adresse",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Adresse not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Adresse>> getAdresseById(
	    @Parameter(description = "ID of the adresse to retrieve", required = true)
	    @PathVariable Integer id
	) {
	    Adresse adresse = adresseService.getAdresseById(id);
	    RestResponse<Adresse> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "adresse retrieved successfully", adresse);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export Adresse to CSV",
	     description = "Generate a CSV file from a provided list of Adresse objects."
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
	public ResponseEntity<byte[]> exportAdresseToCsv(@RequestBody List<Adresse> adresse) {
	  
	  String csvContent = adresseService.exportAdresseToCSV(adresse);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}
 

	@Operation(
	    summary = "Create new adresse",
 
	    description = "Create a new adresse item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Adresse created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid adresse supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createAdresse(
	    @Parameter(description = "Adresse object to be created", required = true)
	    @RequestBody @Valid Adresse adresse
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
	    Adresse newAdresse = adresseService.createAdresse(adresse);
	    RestResponse<Adresse> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "adresse created successfully", newAdresse);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing adresse",

	    description = "Update an existing adresse item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Adresse updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Adresse not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid adresse supplied"
	    )
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updateAdresse(
	    @Parameter(description = "ID of the adresse to update", required = true)
	    @PathVariable Integer id,
	    @Parameter(description = "Updated adresse object", required = true)
	    @RequestBody @Valid Adresse adresse
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
	    Adresse updateAdresse = adresseService.updateAdresse(id, adresse);
	    RestResponse<Adresse> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "adresse updated successfully", updateAdresse);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete adresse",

	    description = "Delete a adresse item by its ID. Returns success even if adresse was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Adresse deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteAdresseById(
	    @Parameter(description = "ID of the adresse to delete", required = true)
	    @PathVariable Integer id
	) {
		try {
			adresseService.getAdresseById(id);
			adresseService.deleteAdresse(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"adresse deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"adresse already deleted or does not exist",
					null
				)
			);
		}
	}



}
