package com.settings.SystemSettings;

import com.settings.db.*;
import com.settings.model.SettingKey;
import com.utils.ResourcesHandler;
import com.utils.ToolsMisc;

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
    DBController dbController;

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

        dbController = dbSelector(ToolsMisc.getScrumNumber(scrum),country);
        dbController.connect();
        int resultSet = dbController.updateQuery(querystatemane);
        dbController.disconnect();
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

    private DBController dbSelector(int scrum, String country){
        if (scrum>25){
            return new KubeDB(scrum,country);
        } else if ((scrum==13)||(scrum==14)||(scrum==16)){
            return new DockerDB(scrum,country);
        } else if (scrum==0) {
            return new ProductionDB(scrum,country);

        } else {
            return new ScrumDB(scrum,country);
        }
    }

}
