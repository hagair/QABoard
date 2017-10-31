package com.settings.SystemSettings;

import com.settings.db.DBHandler;
import com.settings.db.ResultSetConverter;
import com.settings.model.SettingKey;
import com.utils.ResourcesHandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by hagairevah on 6/25/17.
 */
public class ChangeSystemSetting {
    //Data members
    private String urlDocker = "jdbc:mysql://Db-##.gett.io:3306/";
    private String url = "jdbc:mysql://scrum-rds.gett.io:3306/";
    private String query = "UPDATE system_settings SET `value` = '#value' WHERE `key` = '#key';";

    public String changeSystemSettingValue(String filename, String key, String value){
        Map<String, String> keyMap = ResourcesHandler.loadPropertyFileToMap(filename);
        String oldValue = keyMap.get(key);
        keyMap.put(key,value);
        try {
            ResourcesHandler.writeMapToPropertyFile(filename,keyMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return oldValue;

    }

    public void updateQuery(String scrum,String country, String querystatemane) throws Exception {
        String username="";
        String password="";
        if ((scrum.equals("scrum13"))||(scrum.equals("scrum14"))||(scrum.equals("scrum16"))){
            url = "jdbc:mysql://Db-"+scrum+".gett.io:3306/";
            username=country+"scrum0";
            password=country+"scrum0";
        } else {
            username=country+scrum;
            password=country+scrum;
        }
        System.out.println("DBURL: "+url);
        System.out.println("User: "+username);
        System.out.println("Password: "+password);
        DBHandler dbHandler = new DBHandler(url+username, username, password);
        dbHandler.connect();
        int resultSet = dbHandler.updateQuery(querystatemane);

    }
    public void  changeValueOnScrumDB(String scrum, String key, String value){
        String[] values = new String[3];

        if ((value.substring(0,1).contentEquals("["))&&(value.substring(value.length()-1,value.length()).contentEquals("]"))){
            values = value.substring(1,value.length()-1).split(",");
        } else {
            values[0]=value;
            values[1]=value;
            values[2]=value;
        }

        String queryString = "";
        try {
            queryString=query.replace("#value", values[0]);
            queryString=queryString.replace("#key", key);
            System.out.println(queryString);
            updateQuery(scrum,"il", queryString);
            queryString=query.replace("#value", values[1]);
            queryString=queryString.replace("#key", key);
            System.out.println(queryString);
            updateQuery(scrum,"uk", queryString);
            queryString=query.replace("#value", values[2]);
            queryString=queryString.replace("#key", key);
            System.out.println(queryString);
            updateQuery(scrum,"ru", queryString);
//            updateQuery("us"+scrum,"us"+scrum, queryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeAllValuesOnScrumDB(String scrum){
        Map<String,String> keyMap = ResourcesHandler.loadPropertyFileToMap("settings_values_"+scrum+".properties");
        Set<String> set = keyMap.keySet();
        System.out.println(set.size());

        for (String key : set) {
            changeValueOnScrumDB(scrum,key,keyMap.get(key));
            System.out.println("key="+key+" Changed");
        }

    }
}
