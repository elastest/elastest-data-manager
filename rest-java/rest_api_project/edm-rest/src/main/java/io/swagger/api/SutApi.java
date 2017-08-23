package io.swagger.api;

import io.swagger.model.Body1;
import io.swagger.model.InlineResponse200;
import io.swagger.model.InlineResponseDefault;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-12T12:44:56.423Z")

@Api(value = "sut", description = "the sut API")
public interface SutApi {

    @ApiOperation(value = "Backup all data for a specific SuT", notes = "Backup and get a gzipped tarball with all data for a specific SuT", response = InlineResponse200.class, tags={ "DataIO", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful", response = InlineResponse200.class),
        @ApiResponse(code = 400, message = "Invalid SuT supplied or SuT not found", response = InlineResponse200.class),
        @ApiResponse(code = 405, message = "Invalid input", response = InlineResponse200.class),
        @ApiResponse(code = 520, message = "unexpected error", response = InlineResponse200.class) })
    @RequestMapping(value = "/sut/{sut}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<InlineResponse200> backupSutWithId(@ApiParam(value = "The id code of the SuT",required=true ) @PathVariable("sut") String sut);


    @ApiOperation(value = "Declare as SuT as unecessary and start the cleansing procedure. All SuT data will be erased.", notes = "Declare a SuT as deleted and starting the asynchronous purging procedure.", response = Void.class, tags={ "DataIO", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful", response = Void.class),
        @ApiResponse(code = 520, message = "unexpected error", response = Void.class) })
    @RequestMapping(value = "/sut/{sut}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSutWithId(@ApiParam(value = "The id code of the SuT",required=true ) @PathVariable("sut") String sut);


    @ApiOperation(value = "Restore all data for a specific SuT", notes = "Download and restore the gzipped tarball with all data for the specific SuT. If the SuT does not exist will be created. If exists will be overrided.", response = Void.class, tags={ "DataIO", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful Post", response = Void.class),
        @ApiResponse(code = 405, message = "Invalid input", response = Void.class),
        @ApiResponse(code = 520, message = "unexpected error", response = Void.class) })
    @RequestMapping(value = "/sut/{sut}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> restoreSutWithId(@ApiParam(value = "Information about compressed tarball, containing the sut data" ,required=true ) @RequestBody Body1 body);


    @ApiOperation(value = "Retrieve a list with installed SuTs on EDM", notes = "Retrieve a list with Systems Under Test, installed on the EDM. This call will return all SuTs that have been created on the persistence layer", response = String.class, responseContainer = "List", tags={ "DataIO", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = String.class),
        @ApiResponse(code = 520, message = "unexpected error", response = String.class) })
    @RequestMapping(value = "/sut",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<String>> retrieveAllSut();

}
