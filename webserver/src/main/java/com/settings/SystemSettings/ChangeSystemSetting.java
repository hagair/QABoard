package com.settings.SystemSettings;

import com.settings.db.DBHandler;
import com.settings.db.ResultSetConverter;
import com.settings.model.SettingKey;
import com.utils.ResourcesHandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hagairevah on 6/25/17.
 */
public class ChangeSystemSetting {
    //Data members
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

    public void updateQuery(String username, String password, String querystatemane) throws Exception {
        DBHandler dbHandler = new DBHandler(url+username, username, password);
        dbHandler.connect();
        int resultSet = dbHandler.updateQuery(querystatemane);

    }
    public void  changeValueOnScrumDB(String scrum, String key, String value){

        String queryString = query;
        queryString=queryString.replace("#value", value);
        queryString=queryString.replace("#key", key);
        try {
            updateQuery("il"+scrum,"il"+scrum, queryString);
            updateQuery("uk"+scrum,"uk"+scrum, queryString);
            updateQuery("ru"+scrum,"ru"+scrum, queryString);
            updateQuery("us"+scrum,"us"+scrum, queryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
