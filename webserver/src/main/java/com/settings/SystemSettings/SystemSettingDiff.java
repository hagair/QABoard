package com.settings.SystemSettings;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.settings.db.*;
import com.settings.model.SettingKey;
import javafx.util.Pair;

/**
 * Created by hagairevah on 6/11/17.
 */
public class SystemSettingDiff {
    //Data members
    private String scrum;
    private String url = "jdbc:mysql://scrum-rds.gett.io:3306/";
    private String replicaCloneProductionUrl = "jdbc:mysql://gt-country-cloned-db.gtforge.com/gettaxi_country_production";
    private String replicaCloneProductionUser = "automation";
    private String replicaCloneProductionPassword = "Auto!@2016";
    private String query = "Select * from ";

    public HashMap<String, SettingKey> getDiffsFromSpecificCountry( String Url, String username, String password, String table) throws Exception {

        //Connecting to the database of the current scrum and getting all the results according to the query
        DBHandler dbHandler = new DBHandler(Url, username, password);
        dbHandler.connect();
        ResultSet resultSet = dbHandler.executeQuery(query + table);
        HashMap<String, SettingKey> settingKeyArrayList = new ResultSetConverter().convertResultSetToList(resultSet);
        return settingKeyArrayList;
    }

    public ArrayList<HashMap<String,SettingKey>> getDiffsFromAllScrumCountries(String scrum) {
        ArrayList<HashMap<String,SettingKey>> countrisMap = new ArrayList<HashMap<String,SettingKey>>();
        try {
            countrisMap.add(getDiffsFromSpecificCountry(this.url + "il" + scrum, "il" + scrum, "il" + scrum, "system_settings"));
            countrisMap.add(getDiffsFromSpecificCountry(this.url + "uk" + scrum, "uk" + scrum, "uk" + scrum, "system_settings"));
            countrisMap.add(getDiffsFromSpecificCountry(this.url + "ru" + scrum, "ru" + scrum, "ru" + scrum, "system_settings"));
            countrisMap.add(getDiffsFromSpecificCountry(this.url + "us" + scrum, "us" + scrum, "us" + scrum, "system_settings"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return countrisMap;
    }
    public ArrayList<HashMap<String,SettingKey>> getDiffsFromAllProdCountries(String scrum) {
        ArrayList<HashMap<String,SettingKey>> countrisMap = new ArrayList<HashMap<String,SettingKey>>();
        try {
            // replicaCloneProductionUrl.replace("country", "il")
            countrisMap.add(getDiffsFromSpecificCountry(replicaCloneProductionUrl.replace("country", "il"), replicaCloneProductionUser, replicaCloneProductionPassword, "system_settings"));
            countrisMap.add(getDiffsFromSpecificCountry(replicaCloneProductionUrl.replace("country", "uk"), replicaCloneProductionUser, replicaCloneProductionPassword, "system_settings"));
            countrisMap.add(getDiffsFromSpecificCountry(replicaCloneProductionUrl.replace("country", "ru"), replicaCloneProductionUser, replicaCloneProductionPassword, "system_settings"));
            countrisMap.add(getDiffsFromSpecificCountry(replicaCloneProductionUrl.replace("country", "us"), replicaCloneProductionUser, replicaCloneProductionPassword, "system_settings"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return countrisMap;
    }
}
