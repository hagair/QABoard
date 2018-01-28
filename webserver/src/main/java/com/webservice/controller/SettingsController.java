package com.webservice.controller;

import com.google.gson.Gson;
import com.settings.SystemSettings.ChangeSystemSetting;
import com.settings.model.SettingKeyValidation;
import com.utils.ToolsMisc;
import com.webservice.handler.SystemSettings;
import com.utils.ResourcesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static com.utils.ToolsMisc.getScrumNumber;

@RestController
public class SettingsController {

	SystemSettings systemSettings;
	Gson gson;
	boolean all=false;
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping("/settings")
	public String settingComperatorHTML(@RequestParam("scrum") String scrum){
		int i = getScrumNumber(scrum);
		systemSettings = new SystemSettings(i);
//		ResourcesHandler.printPath();
		String  html = ResourcesHandler.loadTxtFile("home.html");
		String response = html.replace("##scrum",scrum);
		response = response.replace("##table",systemSettings.getSettingsTableHTML("settings_values_"+scrum+".properties",false));
		return response;
	}
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping("/Allsettings")
	public String AllsettingComperatorHTML(@RequestParam("scrum") String scrum){
		int i = getScrumNumber(scrum);
		systemSettings = new SystemSettings(i);
//		ResourcesHandler.printPath();
		String  html = ResourcesHandler.loadTxtFile("home.html");
		String response = html.replace("##scrum",scrum);
		response = response.replace("##table",systemSettings.getSettingsTableHTML("settings_values_"+scrum+".properties",true));
		return response;
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping("/settingsJ")
	public String settingComperatorJSON(@RequestParam("scrum") String scrum){
		gson = new Gson();
		if (scrum.equals(null)) {
			scrum = "scrum12";
		}
		int i = getScrumNumber(scrum);
		systemSettings = new SystemSettings(i);
		ResourcesHandler.printPath();
		ArrayList<SettingKeyValidation> settingKeyValidationArrayList = systemSettings.getSettingsTableJSON("settings_values_scrum12.properties");
		String response = gson.toJson(settingKeyValidationArrayList);
		return "{\n" +
				"  \"settings\":"+response+"}";
	}
	@RequestMapping("/settings/changeValue")
	public String changeSettingExpectedValue(){
		return ResourcesHandler.loadTxtFile("ChangeSettingForm.html");

	}
	@RequestMapping("/settings/changeExpectedValue")
	public String changeSettingValue(@RequestParam("scrum") String scrum,
									 @RequestParam("key") String key,
									 @RequestParam("value") String value){
		ChangeSystemSetting changeSystemSetting = new ChangeSystemSetting();
		String oldValue = changeSystemSetting.changeSystemSettingValue("settings_values_"+scrum+".properties", key, value);
		return settingComperatorHTML(scrum);

	}
	@RequestMapping("/settings/changeScrumValues")
	public String changeScrumSettingValue(@RequestParam("scrum") String scrum,
									 @RequestParam("key") String key,
									 @RequestParam("value") String value){
		ChangeSystemSetting changeSystemSetting = new ChangeSystemSetting();
		changeSystemSetting.changeValueOnScrumDB(scrum, key, value);
		return settingComperatorHTML(scrum);

	}
	@RequestMapping("/settings/changeAllScrumValues")
	public String changeAllScrumSettingValue(@RequestParam("scrum") String scrum){
		ChangeSystemSetting changeSystemSetting = new ChangeSystemSetting();
		changeSystemSetting.changeAllValuesOnScrumDB(scrum);
		return settingComperatorHTML(scrum);
	}
	@RequestMapping(value="/settings/FileUpload", headers=("content-type=multipart/*"), method = RequestMethod.POST)
	public String uploadsettingfile(@RequestParam("scrum") String scrum,
									@RequestParam("file") MultipartFile file){
		System.out.println("file uploaded:");
		String fileContent;
		String filename = "settings_values_"+scrum+".properties";
		if (!file.isEmpty()) {
			try {

				byte[] bytes = file.getBytes();
				fileContent = new String(bytes, StandardCharsets.UTF_8);
				ResourcesHandler.writeStringToPropertyFile(filename, fileContent);
				return "You successfully created: " + filename;
			} catch (Exception e) {
				return "You failed to upload  => " + e.getMessage();
			}
		} else {
			return "You failed to upload because the file was empty.";
		}
	}
	@RequestMapping("/settings/printFile")
	public String printKeyValueFile(@RequestParam("scrum") String scrum){
		if (scrum.equals(null)) {
			scrum = "scrum12";
		}
		String filename = "settings_values_"+scrum+".properties";
		String filecontent = "<html>\n<body>" +
				"<h1>"+filename+":</h1>"+
				ResourcesHandler.loadTxtFile(filename)+"</body></html>";
		filecontent = filecontent.replaceAll("\n","<br>");
		return filecontent;
	}

}

