package com.webservice.controller;

import com.webservice.handler.SystemSettings;
import com.utils.ResourcesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.webservice.model.Customer;
//import com.webservice.repo.CustomerRepository;
import com.webservice.handler.EnvironmentCheck;

@RestController
public class EnvStatusController {
    //	CustomerRepository repository;
    EnvironmentCheck environmentCheck = new EnvironmentCheck();
    SystemSettings systemSettings;

    @CrossOrigin(origins = "http://qaboard.gett.io:7001")
    @RequestMapping("/statusJson")
    public String serverStatusJ(@RequestParam("scrum") String scrum){
        if (scrum.equals(null)) {
            scrum = "scrum12";
        }
//        String response = environmentCheck.getAllServersdetailsJson(scrum);
        String response = environmentCheck.liveServerDetailsHashMap(scrum);
        return response;
    }

}

