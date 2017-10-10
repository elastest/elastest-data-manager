package io.swagger.api;

import io.swagger.model.MessageResponse;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-12T12:44:56.423Z")

@Controller
public class BackupApiController implements BackupApi {
	
	private static final Logger LOGGER = Logger.getLogger(BackupApiController.class.getName());

	@Value("${scriptfile.backup.path}")
	private String backupFilePath;
	
	
    public ResponseEntity<MessageResponse> createBackup() throws IOException, InterruptedException{
    	
    	File file = new File( backupFilePath );
    	
    	if(file.exists() && !file.isDirectory()) { 
    		runScript( backupFilePath );
    		return new ResponseEntity<MessageResponse>(HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<MessageResponse>(HttpStatus.EXPECTATION_FAILED);
    	
    }
   
    
	private void runScript(String filepath) throws IOException, InterruptedException {
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec(filepath);
		proc.waitFor();

	}
    
    
}
