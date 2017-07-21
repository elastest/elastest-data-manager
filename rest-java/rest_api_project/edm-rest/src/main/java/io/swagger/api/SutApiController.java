package io.swagger.api;

import io.swagger.model.Body1;
import io.swagger.model.InlineResponse200;
import io.swagger.model.InlineResponseDefault;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-12T12:44:56.423Z")

@Controller
public class SutApiController implements SutApi {



    public ResponseEntity<InlineResponse200> backupSutWithId(@ApiParam(value = "The id code of the SuT",required=true ) @PathVariable("sut") String sut) {
    	System.out.println("Inside: SUT -> backupSutWithId");
        return new ResponseEntity<InlineResponse200>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteSutWithId(@ApiParam(value = "The id code of the SuT",required=true ) @PathVariable("sut") String sut) {
    	System.out.println("Inside: SUT -> deleteSutWithId");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> restoreSutWithId(@ApiParam(value = "Information about compressed tarball, containing the sut data" ,required=true ) @RequestBody Body1 body) {
    	System.out.println("Inside: SUT -> restoreSutWithId");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<List<String>> retrieveAllSut() {
        System.out.println("Inside: SUT -> retrieveAllSut");
        return new ResponseEntity<List<String>>(HttpStatus.OK);
    }

}
