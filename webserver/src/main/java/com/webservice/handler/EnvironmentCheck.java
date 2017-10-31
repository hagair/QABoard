package com.webservice.handler;

import com.google.gson.Gson;
import com.settings.model.ServerDetails;
import com.utils.ResourcesHandler;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.utils.ResourcesHandler.getStringFromInputStream;

/**
 * Created by hagairevah on 6/1/17.
 */
public class EnvironmentCheck {
    String charset = "UTF-8";
    Gson gson = new Gson();

    public ServerDetails getServerDetailsO (String scrum, String server) {
        String url = "http://" + server + "-" + scrum + ".gett.io/alive";
        ServerDetails serverDetails;
        try {
            URLConnection connection = new URL(url + "").openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
//            System.out.print("#");
            System.out.print(server+"  ");
            serverDetails = gson.fromJson(getStringFromInputStream(response), ServerDetails.class);
            serverDetails.setServer(server);
            return serverDetails;
        } catch (Exception e) {
            serverDetails = new ServerDetails();
            serverDetails.setServer(server);
            return serverDetails;
        }
    }

    public String getAllServersdetailsJson(String scrum){
        String s = "";
        ArrayList<String> serverList = loadAllServers();
        ArrayList<ServerDetails> serverListOb = new ArrayList();

        for (int i = 0; i < serverList.size(); i++) {
            String s1 =  serverList.get(i);
            serverListOb.add(getServerDetailsO(scrum, s1));

        }
        return gson.toJson(serverListOb);
    }
    public ArrayList<String> loadAllServers()  {
        String filename = "env.properties";
        return ResourcesHandler.loadFromPropFile(filename);
    }


}
