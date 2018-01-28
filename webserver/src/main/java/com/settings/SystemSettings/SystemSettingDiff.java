package com.settings.SystemSettings;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.settings.db.*;
import com.settings.model.SettingKey;
import javafx.util.Pair;

/**
 * Created by hagairevah on 6/11/17.
 */
public class SystemSettingDiff {
    //Data members
    DBController dbController;
    private String query = "Select * from ";

    public ArrayList<HashMap<String,SettingKey>> getDiffsFromAllCountries(int scrum) {

        ArrayList<HashMap<String,SettingKey>> countrisMap = new ArrayList<HashMap<String,SettingKey>>();
        try {
            countrisMap.add(getDiffsFromSpecificCountry(scrum, "il", "system_settings"));
            countrisMap.add(getDiffsFromSpecificCountry(scrum, "uk", "system_settings"));
            countrisMap.add(getDiffsFromSpecificCountry(scrum, "ru", "system_settings"));
            //countrisMap.add(getDiffsFromSpecificCountry(scrum, "us", "system_settings"));

        } catch (Exception e) {

            e.printStackTrace();
            dbController.disconnect();
        }
        return countrisMap;
    }

    public HashMap<String, SettingKey> getDiffsFromSpecificCountry(int scrum, String coutry, String table) throws Exception {

        //Connecting to the database of the current scrum and getting all the results according to the query
        dbController = dbSelector(scrum,coutry);
        dbController.connect();
        ResultSet resultSet = dbController.executeQuery(query + table);
        HashMap<String, SettingKey> settingKeyArrayList = new ResultSetConverter().convertResultSetToList(resultSet);
        dbController.disconnect();
        return settingKeyArrayList;
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


