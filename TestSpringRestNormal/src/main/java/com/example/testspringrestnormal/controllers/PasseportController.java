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
import com.example.testspringrestnormal.models.Passeport;
import com.example.testspringrestnormal.models.dto.PasseportSearch;
import org.springframework.web.bind.annotation.*;
import com.example.testspringrestnormal.services.interfaces.PasseportService;
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
@RequestMapping("/passeports")
@Tag(name = "Passeport", description = "Passeport Management APIs")
public class PasseportController  {
	private final PasseportService passeportService;

	public PasseportController(PasseportService passeportService) {
	   this.passeportService = passeportService;
	}

	@Operation(
	    summary = "Retrieve all passeport",
	    description = "Get a paginated and sorted list of passeport items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of passeport",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No passeport found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Passeport>>> getAllPasseports(
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
	    Page<Passeport> passeports = passeportService.getAllPasseport(pageable);
	
	    String message = "passeport retrieved successfully";
	
	    if(!passeports.hasContent()) {
	            message = "No passeport found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Passeport>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            passeports
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all passeport",
	    description = "Get a paginated and sorted list of passeport items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of passeport",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No passeport found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Passeport>>> getAllPasseports(
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
	    @RequestBody PasseportSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Passeport> passeports = passeportService.getAllPasseport(pageable, object);
	
	    String message = "passeport retrieved successfully";
	
	    if(!passeports.hasContent()) {
	            message = "No passeport found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Passeport>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            passeports
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get passeport by ID",
	    description = "Retrieve a specific passeport item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the passeport",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Passeport not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Passeport>> getPasseportById(
	    @Parameter(description = "ID of the passeport to retrieve", required = true)
	    @PathVariable Integer id
	) {
	    Passeport passeport = passeportService.getPasseportById(id);
	    RestResponse<Passeport> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "passeport retrieved successfully", passeport);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export Passeport to CSV",
	     description = "Generate a CSV file from a provided list of Passeport objects."
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
	public ResponseEntity<byte[]> exportPasseportToCsv(@RequestBody List<Passeport> passeport) {
	  
	  String csvContent = passeportService.exportPasseportToCSV(passeport);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}
 

	@Operation(
	    summary = "Create new passeport",
 
	    description = "Create a new passeport item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Passeport created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid passeport supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createPasseport(
	    @Parameter(description = "Passeport object to be created", required = true)
	    @RequestBody @Valid Passeport passeport
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
	    Passeport newPasseport = passeportService.createPasseport(passeport);
	    RestResponse<Passeport> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "passeport created successfully", newPasseport);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing passeport",

	    description = "Update an existing passeport item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Passeport updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Passeport not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid passeport supplied"
	    )
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePasseport(
	    @Parameter(description = "ID of the passeport to update", required = true)
	    @PathVariable Integer id,
	    @Parameter(description = "Updated passeport object", required = true)
	    @RequestBody @Valid Passeport passeport
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
	    Passeport updatePasseport = passeportService.updatePasseport(id, passeport);
	    RestResponse<Passeport> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "passeport updated successfully", updatePasseport);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete passeport",

	    description = "Delete a passeport item by its ID. Returns success even if passeport was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Passeport deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deletePasseportById(
	    @Parameter(description = "ID of the passeport to delete", required = true)
	    @PathVariable Integer id
	) {
		try {
			passeportService.getPasseportById(id);
			passeportService.deletePasseport(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"passeport deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"passeport already deleted or does not exist",
					null
				)
			);
		}
	}



}
