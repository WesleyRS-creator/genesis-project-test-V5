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
import com.example.testspringrestnormal.models.VSynthetiseGlobal;
import com.example.testspringrestnormal.models.dto.VSynthetiseGlobalSearch;
import org.springframework.web.bind.annotation.*;
import com.example.testspringrestnormal.services.interfaces.VSynthetiseGlobalService;
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
@RequestMapping("/vsynthetiseglobals")
@Tag(name = "V synthetise global", description = "V synthetise global Management APIs")
public class VSynthetiseGlobalController  {
	private final VSynthetiseGlobalService vsynthetiseglobalService;

	public VSynthetiseGlobalController(VSynthetiseGlobalService vsynthetiseglobalService) {
	   this.vsynthetiseglobalService = vsynthetiseglobalService;
	}

	@Operation(
	    summary = "Retrieve all v synthetise global",
	    description = "Get a paginated and sorted list of v synthetise global items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of v synthetise global",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No v synthetise global found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<VSynthetiseGlobal>>> getAllVSynthetiseGlobals(
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
	    Page<VSynthetiseGlobal> vsynthetiseglobals = vsynthetiseglobalService.getAllVSynthetiseGlobal(pageable);
	
	    String message = "v synthetise global retrieved successfully";
	
	    if(!vsynthetiseglobals.hasContent()) {
	            message = "No v synthetise global found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<VSynthetiseGlobal>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vsynthetiseglobals
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all v synthetise global",
	    description = "Get a paginated and sorted list of v synthetise global items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of v synthetise global",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No v synthetise global found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<VSynthetiseGlobal>>> getAllVSynthetiseGlobals(
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
	    @RequestBody VSynthetiseGlobalSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<VSynthetiseGlobal> vsynthetiseglobals = vsynthetiseglobalService.getAllVSynthetiseGlobal(pageable, object);
	
	    String message = "v synthetise global retrieved successfully";
	
	    if(!vsynthetiseglobals.hasContent()) {
	            message = "No v synthetise global found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<VSynthetiseGlobal>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vsynthetiseglobals
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get v synthetise global by ID",
	    description = "Retrieve a specific v synthetise global item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the v synthetise global",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "V synthetise global not found with the provided ID"
	    )
	})
	@GetMapping("/{idPersonne}")
	public ResponseEntity<RestResponse<VSynthetiseGlobal>> getVSynthetiseGlobalById(
	    @Parameter(description = "ID of the v synthetise global to retrieve", required = true)
	    @PathVariable Integer idPersonne
	) {
	    VSynthetiseGlobal vsynthetiseglobal = vsynthetiseglobalService.getVSynthetiseGlobalById(idPersonne);
	    RestResponse<VSynthetiseGlobal> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "v synthetise global retrieved successfully", vsynthetiseglobal);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export V synthetise global to CSV",
	     description = "Generate a CSV file from a provided list of V synthetise global objects."
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
	public ResponseEntity<byte[]> exportVSynthetiseGlobalToCsv(@RequestBody List<VSynthetiseGlobal> vsynthetiseglobal) {
	  
	  String csvContent = vsynthetiseglobalService.exportVSynthetiseGlobalToCSV(vsynthetiseglobal);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}





}
