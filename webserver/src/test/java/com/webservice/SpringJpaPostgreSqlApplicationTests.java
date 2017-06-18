package com.webservice;

import com.webservice.handler.SystemSettings;
import com.utils.ResourcesHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringJpaPostgreSqlApplicationTests {

	@Test
	public void runDBConnection() {
		String scrum = "scrum12";
//		ResourcesHandler.setPath("debug");
		SystemSettings systemSettings = new SystemSettings(scrum);
		String  html = ResourcesHandler.loadTxtFile("home.html");
		String response = html.replace("##scrum",scrum);
		response = response.replace("##table",systemSettings.getSettingsTableHTML("settings.properties"));
		System.out.println(response);
	}
	@Test
	public void loadfrompropfile(){
//		ResourcesHandler.setPath("debug");
		ResourcesHandler.loadPropertyFileToMap("settings_values.properties");
	}



}
