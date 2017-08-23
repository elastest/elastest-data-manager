package io.swagger.api.testControllers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;

import io.swagger.Swagger2SpringBoot;
import io.swagger.api.BackupApiController;
import io.swagger.model.InlineResponse200;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration (classes = {
		Swagger2SpringBoot.class, 
		BackupApiController.class})
@WebAppConfiguration
//@TestPropertySource(properties = { "scriptfile.path = C:/Users/ykarav/Desktop/giannis2.bat" })
public class BackupApiControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private MockHttpServletRequest request;

	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@Mock
	private BackupApiController bac;
	
	@Rule 
	public MockitoRule rule = MockitoJUnit.rule();
	

	@Before
	public void setup(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@After
	public void destroy(){
		this.mockMvc = null;
	}
	
	
	//-------------------- Backup Create --------------------//
	
	@Test
	public void createBackup_GET_415() throws Exception{
		mockMvc
			.perform(get("/backup"))
//			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().is(415));
	}

	@Test
	public void createBackup_GET_404() throws Exception{
		mockMvc
			.perform(get("/backup/wrongURL"))
			.andExpect(status().is(404));
	}
	
	@Test
	public void createBackup_GET_200() throws Exception{
		mockMvc
			.perform(get("/backup")
					.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk());
	}
	
	@Test
	public void createBackup_GET_417_FileNotExists() throws Exception {
		ReflectionTestUtils.setField(bac, "filepath", "/tmp/wrongFileName.sh");

		Mockito.doCallRealMethod().when(bac).createBackup();

		ResponseEntity<InlineResponse200> r = new ResponseEntity<InlineResponse200>(HttpStatus.EXPECTATION_FAILED);
		when(bac.createBackup()).thenReturn(r);
		bac.createBackup();
		
	}
	
	@Test
	public void createBackup_GET_200_FileExists_NotDirectory() throws Exception {
		ReflectionTestUtils.setField(bac, "filepath", "../backup.sh");

		Mockito.doCallRealMethod().when(bac).createBackup();
		
		File file = new File("../backup.sh");
		assertTrue(file.exists());
		assertTrue(!file.isDirectory());

		ResponseEntity<InlineResponse200> r = new ResponseEntity<InlineResponse200>(HttpStatus.OK);
		when(bac.createBackup()).thenReturn(r);
		bac.createBackup();
		
	}

	@Test
	public void createBackup_GET_417_FileExists_IsDirectory() throws Exception {
		ReflectionTestUtils.setField(bac, "filepath", "../deployment");

		Mockito.doCallRealMethod().when(bac).createBackup();
		
		File file = new File("../deployment");
		assertTrue(file.exists());
		assertTrue(file.isDirectory());

		ResponseEntity<InlineResponse200> r = new ResponseEntity<InlineResponse200>(HttpStatus.EXPECTATION_FAILED);
		when(bac.createBackup()).thenReturn(r);
		bac.createBackup();
		
	}
	
	
	//-------------------- Backup Restore --------------------//
	
	@Test
	public void restoreBackup_POST_415() throws Exception{
		mockMvc
			.perform(post("/backup"))
			.andExpect(status().is(415));
	}
	
	@Test
	public void restoreBackup_POST_400() throws Exception{
		mockMvc
			.perform(post("/backup")
					.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().is(400));
	}
	
	@Test
	public void restoreBackup_POST_400_JSON_BAD() throws Exception{
		mockMvc
			.perform(post("/backup")
					.contentType(MediaType.APPLICATION_JSON_UTF8) 
					.content( BackupApiControllerTest.jsonResponse_BAD() ))
			.andExpect(status().is(400));
	}
	
	@Test
	public void restoreBackup_POST_200() throws Exception{
		mockMvc
			.perform(post("/backup")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content( BackupApiControllerTest.jsonResponse_GOOD() ))
			.andExpect(status().isOk());
	}
	

	
	//-------------------- JSON ObjectAsString --------------------//
	
	// JSON Request Body - GOOD
	private static String jsonResponse_GOOD() {
		String requestBody = "{\"bigFileURL\": \"http://example.com/files/file001.dmp\","
				+ "\"content\":\"MyContent\","
				+ "\"filesystem\": \"s3\","
				+ "\"isBigFile\": true,"
				+ "\"location\": \"/MyModule/MyLocation\","
				+ "\"memberOfSuT\": \"string\"}";
		return requestBody;
	}
	
	// JSON Request Body - BAD
	private static String jsonResponse_BAD() {
		String requestBody = "{\"bigFileURL\": \"http://example.com/files/file001.dmp\","
				+ "\"content\":\"MyContent\","
				+ "\"isBigFile\": ERROR_HERE,"
				+ "\"location\": \"/MyModule/MyLocation\","
				+ "\"memberOfSuT\": \"string\"}";
		return requestBody;
	}
	
}
