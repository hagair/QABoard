package com.webservice.handler;

import com.google.gson.Gson;
import com.webservice.model.ServerDetails;
import com.utils.ResourcesHandler;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by hagairevah on 6/1/17.
 */
public class EnvironmentCheck {
    String charset = "UTF-8";
    Gson gson = new Gson();

    public String getServerDetails(String scrum, String server,int index){
        String url = "http://"+server+"-"+scrum+".gett.io/alive";
        try {
            URLConnection connection = new URL(url + "").openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            ServerDetails serverDetails = gson.fromJson(getStringFromInputStream(response), ServerDetails.class);
            System.out.print("#");
            return "<tr><td>"+serverDetails.getServer()+"</td>" +
                    "<td>"+serverDetails.getNow()+"</td>" +
                    "<td>"+serverDetails.getStatus()+"</td>" +
                    "<td>"+serverDetails.isAlive()+"</td>" +
                    "<td>"+serverDetails.getGit_revision()+"</td>" +
                    "<td><a href="+url+">"+url+"</a></td>" +
                    "</tr>";
        } catch (Exception e){
            return "<tr><td>"+server+"</td>" +
                    "<td>null</td>" +
                    "<td>null</td>" +
                    "<td>null</td>" +
                    "<td>null</td>" +
                    "<td>null</td>" +
                    "</tr>";
        }



    }
    public ServerDetails getServerDetailsO (String scrum, String server) {
        String url = "http://" + server + "-" + scrum + ".gett.io/alive";
        try {
            URLConnection connection = new URL(url + "").openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            System.out.print("#");
            ServerDetails serverDetails = gson.fromJson(getStringFromInputStream(response), ServerDetails.class);
            serverDetails.setServer(server);
            return serverDetails;
        } catch (Exception e) {
            return new ServerDetails();
        }
    }
    public String getAllServersdetailsHTML(String scrum){
        String s = "";
        ArrayList<String> serverList = loadAllServers();
        for (int i = 0; i < serverList.size(); i++) {
            String o = serverList.get(i);
            s = s + getServerDetails(scrum, o,i+1);
        }
        return s;
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

    private String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}
