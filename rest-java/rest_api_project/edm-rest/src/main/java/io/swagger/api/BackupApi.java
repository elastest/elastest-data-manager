package io.swagger.api;

import io.swagger.model.Body;
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

@Api(value = "backup", description = "the backup API")
public interface BackupApi {

    @ApiOperation(value = "Backup all data from the EDM and EBS modules", notes = "Backup and create a gzipped tarball with all data for both EDM and EBS modules. Please, notice that EDM and EBS modules are containing all platforms data.", response = InlineResponse200.class, tags={ "DataIO", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful", response = InlineResponse200.class),
        @ApiResponse(code = 405, message = "Invalid input", response = InlineResponse200.class),
        @ApiResponse(code = 200, message = "unexpected error", response = InlineResponse200.class) })
    @RequestMapping(value = "/backup",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<InlineResponse200> createBackup();


    @ApiOperation(value = "Restore all data for the platform", notes = "Download and restore the gzipped tarball with all data for the platform. All existing will be overrided.", response = Void.class, tags={ "DataIO", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful Post", response = Void.class),
        @ApiResponse(code = 405, message = "Invalid input", response = Void.class),
        @ApiResponse(code = 200, message = "unexpected error", response = Void.class) })
    @RequestMapping(value = "/backup",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> restoreBackup(@ApiParam(value = "Information about compressed tarball, containing the sut data" ,required=true ) @RequestBody Body body);

}
