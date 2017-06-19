package com.webservice;

import com.google.gson.Gson;
import com.settings.model.SettingKeyValidation;
import com.webservice.handler.SystemSettings;
import com.utils.ResourcesHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringJpaPostgreSqlApplicationTests {

		Gson gson;
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
	@Test
	public void testSettingKeyValidationObject(){
		SettingKeyValidation settingKeyValidation = new SettingKeyValidation();
		gson = new Gson();
		//set values
		settingKeyValidation.setModule("security");
		settingKeyValidation.setKey("use_https");
		settingKeyValidation.setExpected_value("1");
		settingKeyValidation.setEqual(true);
		settingKeyValidation.setInfo("https is working");
		settingKeyValidation.setCountry("il","1");
		settingKeyValidation.setCountry("uk","1");
		settingKeyValidation.setCountry("ru","1");
		settingKeyValidation.setCountry("us","0");

		//to json
		System.out.println(gson.toJson(settingKeyValidation));

	}




}
