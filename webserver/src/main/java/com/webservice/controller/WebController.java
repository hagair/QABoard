package com.webservice.controller;

import com.webservice.handler.SystemSettings;
import com.utils.ResourcesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webservice.model.Customer;
import com.webservice.repo.CustomerRepository;
import com.webservice.handler.EnvironmentCheck;

@RestController
public class WebController {
	@Autowired
	CustomerRepository repository;
	EnvironmentCheck environmentCheck = new EnvironmentCheck();
	SystemSettings systemSettings;

	@RequestMapping("/statusJson")
	public String serverStatusJ(@RequestParam("scrum") String scrum){
		if (scrum.equals(null)) {
			scrum = "scrum12";
		}
		String response = environmentCheck.getAllServersdetailsJson(scrum);
		return response;
	}
	@RequestMapping("/statusHtml")
	public String serverStatusH(@RequestParam("scrum") String scrum){
		if (scrum.equals(null)) {
			scrum = "scrum12";
		}
		String response = environmentCheck.getAllServersdetailsHTML(scrum);
		return "<html>" +
				"<table>" +
				"<tr>" +
				"<b><td>Server</td><td>Time</td><td>Status</td><td>Alive</td><td>Git_Revision</td><td>Alive_URL</td></b>" +
				"</tr>" +
				""+response+"" +
				"</table>" +
				"</html>";
	}
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
	@RequestMapping("/test")
	public String testAPI(){
		System.out.println("test API called");
		ResourcesHandler.setPath("debug");
		String response = ResourcesHandler.loadTxtFile("sample.json");
		return response;
	}
//	@RequestMapping("/save")
//	public String process(){
//		repository.save(new Customer("Jack", "Smith"));
//		repository.save(new Customer("Adam", "Johnson"));
//		repository.save(new Customer("Kim", "Smith"));
//		repository.save(new Customer("David", "Williams"));
//		repository.save(new Customer("Peter", "Davis"));
//		return "Done";
//	}
//
//
//	@RequestMapping("/findall")
//	public String findAll(){
//		String result = "<html>";
//
//		for(Customer cust : repository.findAll()){
//			result += "<div>" + cust.toString() + "</div>";
//		}
//
//		return result + "</html>";
//	}
//
//	@RequestMapping("/findbyid")
//	public String findById(@RequestParam("id") long id){
//		String result = "";
//		result = repository.findOne(id).toString();
//		return result;
//	}
//
//	@RequestMapping("/findbylastname")
//	public String fetchDataByLastName(@RequestParam("lastname") String lastName){
//		String result = "<html>";
//
//		for(Customer cust: repository.findByLastName(lastName)){
//			result += "<div>" + cust.toString() + "</div>";
//		}
//
//		return result + "</html>";
//	}
}

