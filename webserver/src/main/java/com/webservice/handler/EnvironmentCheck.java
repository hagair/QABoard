package com.webservice.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.settings.model.CIDetails;
import com.settings.model.LiveServerDetails;
import com.utils.ResourcesHandler;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.utils.ResourcesHandler.getStringFromInputStream;

/**
 * Created by hagairevah on 6/1/17.
 */
public class EnvironmentCheck {
    String charset = "UTF-8";
    Gson gson = new Gson();

    public LiveServerDetails getLiveServerDetailsO(String scrum, String server) {
        String url = "";
        if (server.equalsIgnoreCase("osrm")) {
            url = "http://osrm-qa.gett.io/alive";
        }else {
            if (server.equalsIgnoreCase("pubnub")) {
                url = "http://pubnub-qa.gett.io/alive";
            } else {
                if (server.equalsIgnoreCase("relay")) {
                    url = "http://relayscrum.gett.io/alive";
                } else {
                    url = "http://" + server + "-" + scrum + ".gett.io/alive";
                }
            }
        }
        LiveServerDetails serverDetails;
        try {
            URLConnection connection = new URL(url + "").openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setReadTimeout(1000);
            InputStream response = connection.getInputStream();
//            System.out.print("#");
            serverDetails = gson.fromJson(getStringFromInputStream(response), LiveServerDetails.class);
            serverDetails.setServer(server);
            return serverDetails;
        } catch (Exception e) {
            serverDetails = new LiveServerDetails();
            serverDetails.setServer(server);
            return serverDetails;
        }
    }
    public Map<String, CIDetails> getCIServicesData (String json){
        Map<String, CIDetails> ciDetails;
        try {
            ciDetails = gson.fromJson(json, Map.class);
            return ciDetails;
        } catch (Exception e) {
            return null;
        }
    }
    public String getDataFromJson(String json, String service, String key){
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        return jsonObject.get(service).getAsJsonObject().get(key).getAsString();
    }

public String getCIServicesDataString (){
    String url = "http://ci.gtforge.com/api/v1/services";
    Map<String, CIDetails> ciDetails;
    try {
        URLConnection connection = new URL(url + "").openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream response = connection.getInputStream();
        return ResourcesHandler.getStringFromInputStream(response);
    } catch (Exception e) {
        return null;
    }
}
    public String getAllServersdetailsJson(String scrum){
        String s = "";
        ArrayList<String> serverList = loadAllServers();
        ArrayList<LiveServerDetails> serverListOb = new ArrayList();

        for (int i = 0; i < serverList.size(); i++) {
            String s1 =  serverList.get(i);
            serverListOb.add(getLiveServerDetailsO(scrum, s1));

        }
        return gson.toJson(serverListOb);
    }
    public ArrayList<String> loadAllServers()  {
        String filename = "env.properties";
        return ResourcesHandler.loadFromPropFile(filename);
    }

    public String liveServerDetailsHashMap (String scrum){
        int i = 1;
        Map<String, LiveServerDetails> x = new HashMap<String, LiveServerDetails>();
        Map<String, CIDetails> ciDetailsMap = new HashMap<String, CIDetails>();
        String jsonString = getCIServicesDataString();
        ciDetailsMap = getCIServicesData(jsonString);

        Set<String>  services = ciDetailsMap.keySet();
        for (String service: services) {
            i++;
            System.out.print(i+": "+service);
            LiveServerDetails liveServerDetails = new LiveServerDetails();
            liveServerDetails = getLiveServerDetailsO(scrum,service);
            liveServerDetails.setGit_repo(getDataFromJson(jsonString, service, "git_repo"));
            x.put(service, liveServerDetails);
            System.out.println();
        }
        return gson.toJson(x);

        }

}
