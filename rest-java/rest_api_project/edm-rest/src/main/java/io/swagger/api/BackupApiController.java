package io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.model.Body;
import io.swagger.model.InlineResponse200;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-12T12:44:56.423Z")

@Controller
public class BackupApiController implements BackupApi {
	
	/** Class Logger */
	private static final Logger LOGGER = Logger.getLogger(BackupApiController.class.getName());

	@Value("${scriptfile.path}")
	private String filepath;

    public ResponseEntity<InlineResponse200> createBackup() {
        
        try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(filepath);
			
			proc.waitFor();
			
		} catch (IOException ioex) {
			LOGGER.log(Level.SEVERE, "IOException", ioex);
		} catch (InterruptedException iex) {
			LOGGER.log(Level.SEVERE, "InterruptedException", iex);
		}
        
        return new ResponseEntity<InlineResponse200>(HttpStatus.OK);
    }

    public ResponseEntity<Void> restoreBackup(@ApiParam(value = "Information about compressed tarball, containing the sut data", required=true ) @RequestBody Body body) {
    	System.out.println("Inside: restoreBackup - POST");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
