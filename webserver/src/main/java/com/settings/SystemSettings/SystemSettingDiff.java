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
    private String url = "jdbc:mysql://scrum-rds.gett.qa:3306/";
    private String replicaCloneProductionUrl = "jdbc:mysql://gt-country-cloned-com.settings.db.gtforge.com/gettaxi_country_production";
    private String replicaCloneProductionUser = "automation";
    private String replicaCloneProductionPassword = "Auto!@2016";
    private String query = "Select * from ";

    public HashMap<String, SettingKey> getDiffsFromSpecificCountry(String country, String productionUrl, String scrumUrl, String username, String password, String table) throws Exception {

//        System.out.println(scrumUrl);
//        System.out.println(username);
//        System.out.println(password);


        //Connecting to the database of the current scrum and getting all the results according to the query
        DBHandler dbHandler = new DBHandler(scrumUrl, username, password);
        dbHandler.connect();
        ResultSet resultSet = dbHandler.executeQuery(query + table);
        HashMap<String, SettingKey> settingKeyArrayList = new ResultSetConverter().convertResultSetToList(resultSet);

//        Connecting to the database of the production and getting all the results according to the query
//        dbHandler = new DBHandler(productionUrl, replicaCloneProductionUser, replicaCloneProductionPassword);
//        dbHandler.connect();
//        resultSet = dbHandler.executeQuery(query + table);
//        Object[][] productionObjMatrix = new ResultSetConverter().convertRStoMatrix(resultSet);
        return settingKeyArrayList;
    }

    public ArrayList<HashMap<String,SettingKey>> getDiffsFromAllCountries(String scrum) {
        ArrayList<HashMap<String,SettingKey>> countrisMap = new ArrayList<HashMap<String,SettingKey>>();
        try {
            countrisMap.add(getDiffsFromSpecificCountry("il", replicaCloneProductionUrl.replace("country", "il"), this.url + "il" + scrum, "il" + scrum, "il" + scrum, "system_settings"));
            countrisMap.add(getDiffsFromSpecificCountry("uk", replicaCloneProductionUrl.replace("country", "uk"), this.url + "uk" + scrum, "uk" + scrum, "uk" + scrum, "system_settings"));
            countrisMap.add(getDiffsFromSpecificCountry("ru", replicaCloneProductionUrl.replace("country", "ru"), this.url + "ru" + scrum, "ru" + scrum, "ru" + scrum, "system_settings"));
            countrisMap.add(getDiffsFromSpecificCountry("us", replicaCloneProductionUrl.replace("country", "us"), this.url + "us" + scrum, "us" + scrum, "us" + scrum, "system_settings"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return countrisMap;
    }
}
