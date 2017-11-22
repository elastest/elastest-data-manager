package io.swagger.api;

import io.swagger.model.FileObject;
import io.swagger.model.MessageResponse;
import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.IOException;

import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-08-31T09:23:06.055Z")

@Controller
public class RestoreApiController implements RestoreApi {

	@Value("${scriptfile.restore.file}")
	private String restoreFilePath;

	public ResponseEntity<MessageResponse> restoreBackup(
			@ApiParam(value = "Define the backup id to be restored", required = true) @PathVariable("backupId") String backupId,
			@ApiParam(value = "Information about compressed tarball, containing backup data", required = true) @Valid @RequestBody FileObject body)
			throws IOException, InterruptedException {

		MessageResponse msg = new MessageResponse();
		
		File file = new File(restoreFilePath);

		if (file.exists() && !file.isDirectory()) {
			runScript(restoreFilePath);
			msg.setMsg("Restore Completed");
			return new ResponseEntity<MessageResponse>(msg, HttpStatus.OK);
		}

		msg.setMsg("Cannot perform restore");
		return new ResponseEntity<MessageResponse>(HttpStatus.EXPECTATION_FAILED);

	}

	private void runScript(String filepath) throws IOException, InterruptedException {
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec(filepath);
		proc.waitFor();
	}

}
