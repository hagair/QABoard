package com.webservice.controller;

import com.google.gson.Gson;
import com.settings.SystemSettings.ChangeSystemSetting;
import com.settings.model.SettingKeyValidation;
import com.webservice.handler.SystemSettings;
import com.utils.ResourcesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class WebController {

	SystemSettings systemSettings;
	Gson gson;

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping("/settings")
	public String settingComperatorHTML(@RequestParam("scrum") String scrum){
		if (scrum.equals(null)) {
			scrum = "scrum12";
		}
		systemSettings = new SystemSettings(scrum);
//		ResourcesHandler.printPath();
		String  html = ResourcesHandler.loadTxtFile("home.html");
		String response = html.replace("##scrum",scrum);
		response = response.replace("##table",systemSettings.getSettingsTableHTML("settings_values_"+scrum+".properties"));
		return response;
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping("/settingsJ")
	public String settingComperatorJSON(@RequestParam("scrum") String scrum){
		gson = new Gson();
		if (scrum.equals(null)) {
			scrum = "scrum12";
		}
		systemSettings = new SystemSettings(scrum);
		ResourcesHandler.printPath();
		ArrayList<SettingKeyValidation> settingKeyValidationArrayList = systemSettings.getSettingsTableJSON("settings_values_scrum12.properties");
		String response = gson.toJson(settingKeyValidationArrayList);
		return "{\n" +
				"  \"settings\":"+response+"}";
	}
	@RequestMapping("/settings/changeExpectedValue")
	public String changeSettingValue(){
		return ResourcesHandler.loadTxtFile("ChangeSettingForm.html");

	}
}

