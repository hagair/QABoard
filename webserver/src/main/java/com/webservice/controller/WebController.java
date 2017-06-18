package com.webservice.controller;

import com.webservice.handler.SystemSettings;
import com.utils.ResourcesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

	SystemSettings systemSettings;
	@RequestMapping("/settings")
	public String settingComperatorHTML(@RequestParam("scrum") String scrum){
		if (scrum.equals(null)) {
			scrum = "scrum12";
		}
		systemSettings = new SystemSettings(scrum);
		ResourcesHandler.printPath();
		String  html = ResourcesHandler.loadTxtFile("home.html");
		String response = html.replace("##scrum",scrum);
		response = response.replace("##table",systemSettings.getSettingsTableHTML("settings_values.properties"));
		return response;
	}

}

