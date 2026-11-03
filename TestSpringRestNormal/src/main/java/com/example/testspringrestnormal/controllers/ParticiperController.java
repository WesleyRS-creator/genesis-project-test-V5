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
import com.example.testspringrestnormal.models.Participer;
import com.example.testspringrestnormal.models.dto.ParticiperSearch;
import org.springframework.web.bind.annotation.*;
import com.example.testspringrestnormal.services.interfaces.ParticiperService;
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
@RequestMapping("/participers")
@Tag(name = "Participer", description = "Participer Management APIs")
public class ParticiperController  {
	private final ParticiperService participerService;

	public ParticiperController(ParticiperService participerService) {
	   this.participerService = participerService;
	}

	@Operation(
	    summary = "Retrieve all participer",
	    description = "Get a paginated and sorted list of participer items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of participer",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No participer found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Participer>>> getAllParticipers(
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
	    Page<Participer> participers = participerService.getAllParticiper(pageable);
	
	    String message = "participer retrieved successfully";
	
	    if(!participers.hasContent()) {
	            message = "No participer found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Participer>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            participers
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all participer",
	    description = "Get a paginated and sorted list of participer items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of participer",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No participer found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Participer>>> getAllParticipers(
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
	    @RequestBody ParticiperSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Participer> participers = participerService.getAllParticiper(pageable, object);
	
	    String message = "participer retrieved successfully";
	
	    if(!participers.hasContent()) {
	            message = "No participer found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Participer>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            participers
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get participer by ID",
	    description = "Retrieve a specific participer item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the participer",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Participer not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Participer>> getParticiperById(
	    @Parameter(description = "ID of the participer to retrieve", required = true)
	    @PathVariable Integer id
	) {
	    Participer participer = participerService.getParticiperById(id);
	    RestResponse<Participer> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "participer retrieved successfully", participer);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export Participer to CSV",
	     description = "Generate a CSV file from a provided list of Participer objects."
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
	public ResponseEntity<byte[]> exportParticiperToCsv(@RequestBody List<Participer> participer) {
	  
	  String csvContent = participerService.exportParticiperToCSV(participer);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}
 

	@Operation(
	    summary = "Create new participer",
 
	    description = "Create a new participer item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Participer created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid participer supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createParticiper(
	    @Parameter(description = "Participer object to be created", required = true)
	    @RequestBody @Valid Participer participer
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
	    Participer newParticiper = participerService.createParticiper(participer);
	    RestResponse<Participer> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "participer created successfully", newParticiper);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing participer",

	    description = "Update an existing participer item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Participer updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Participer not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid participer supplied"
	    )
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updateParticiper(
	    @Parameter(description = "ID of the participer to update", required = true)
	    @PathVariable Integer id,
	    @Parameter(description = "Updated participer object", required = true)
	    @RequestBody @Valid Participer participer
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
	    Participer updateParticiper = participerService.updateParticiper(id, participer);
	    RestResponse<Participer> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "participer updated successfully", updateParticiper);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete participer",

	    description = "Delete a participer item by its ID. Returns success even if participer was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Participer deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteParticiperById(
	    @Parameter(description = "ID of the participer to delete", required = true)
	    @PathVariable Integer id
	) {
		try {
			participerService.getParticiperById(id);
			participerService.deleteParticiper(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"participer deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"participer already deleted or does not exist",
					null
				)
			);
		}
	}



}
