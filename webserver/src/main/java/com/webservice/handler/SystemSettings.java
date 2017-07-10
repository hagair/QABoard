package com.webservice.handler;

import com.settings.SystemSettings.SystemSettingDiff;
import com.settings.model.SettingKey;
import com.settings.model.SettingKeyValidation;
import com.utils.ResourcesHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by hagairevah on 6/12/17.
 */
public class SystemSettings {
    @Autowired
    String scrum;
    public SystemSettings(String scrum) {

        this.scrum = scrum;
    }
    public String getSettingsTableHTML(String filename){
        SystemSettingDiff systemSettingDiff = new SystemSettingDiff();
        ArrayList<HashMap<String,SettingKey>> ss = systemSettingDiff.getDiffsFromAllScrumCountries(scrum);
        ArrayList<SettingKeyValidation> settingKeyValidationArrayList = getSettingKeyValidationList(ss,filename);

        return settingTableCreatorByList(settingKeyValidationArrayList);
    }

    public ArrayList<SettingKeyValidation> getSettingsTableJSON(String filename){
        SystemSettingDiff systemSettingDiff = new SystemSettingDiff();
        ArrayList<HashMap<String,SettingKey>> ss = systemSettingDiff.getDiffsFromAllScrumCountries(scrum);

        return getSettingKeyValidationList(ss,filename);
    }
    private ArrayList<SettingKeyValidation> getSettingKeyValidationList(ArrayList<HashMap<String,SettingKey>> list, String filename){
        String s = "";
        String v1 = "";
        String v2 = "";
        String v3 = "";
        ArrayList<SettingKeyValidation> settingKeyValidationArrayList = new ArrayList<SettingKeyValidation>();
        Map<String,String> keyMap = ResourcesHandler.loadPropertyFileToMap(filename);
        Set<String> set = keyMap.keySet();
        System.out.println(set.size());
        int i = 0;
        for (String key : set) {
            i++;
//            System.out.println(i);
            SettingKeyValidation settingKeyValidation = new SettingKeyValidation();
            settingKeyValidation.setModule(list.get(0).get(key).getModule());
            settingKeyValidation.setKey(list.get(0).get(key).getKey());
            settingKeyValidation.setExpected_value(keyMap.get(key));
            settingKeyValidation.setInfo(list.get(0).get(key).getInformation());

            if (list.get(1).get(key) != null){
                v1 = list.get(1).get(key).getValue();
            } else {v1="";}
            if (list.get(2).get(key) != null){
                v2 = list.get(2).get(key).getValue();
            } else {v2="";}
            if (list.get(3).get(key) != null){
                v3 = list.get(3).get(key).getValue();
            } else {v3="";}

            settingKeyValidation.setCountry("il",list.get(0).get(key).getValue());
            settingKeyValidation.setCountry("uk",v1);
            settingKeyValidation.setCountry("ru",v2);
            settingKeyValidation.setCountry("us",v3);
            settingKeyValidation.setEqual(setValidation(settingKeyValidation.getExpected_value(),settingKeyValidation.getCountries()));
            settingKeyValidationArrayList.add(settingKeyValidation);
        }
        return settingKeyValidationArrayList;
    }
    private String settingTableCreatorByList(ArrayList<SettingKeyValidation> settingKeyValidationList){
        String tableString = "";
        settingKeyValidationList = sortSettingArrayList(settingKeyValidationList);
        for (int i = 0; i < settingKeyValidationList.size(); i++) {
            SettingKeyValidation settingKeyValidation = settingKeyValidationList.get(i);
            String trcolor;
            if (settingKeyValidation.isEqual()){
                trcolor = "<tr>";

            } else {
                trcolor = "<tr bgcolor=\"#FFA500\">";
            }
            tableString = tableString + trcolor +
                    "<td>" + settingKeyValidation.getModule() +
                    "</td><td>" + settingKeyValidation.getKey() +
                    "</td><td>" + settingKeyValidation.getExpected_value() +
                    "</td><td>" + settingKeyValidation.getCountry("il") +
                    "</td><td>" + settingKeyValidation.getCountry("uk") +
                    "</td><td>" + settingKeyValidation.getCountry("ru") +
                    "</td><td>" + settingKeyValidation.getCountry("us") +
                    "</td><td>" + settingKeyValidation.getInfo() +
                    "</td></tr>";
        }
        return tableString;
    }
    private boolean setValidation(String expected, ArrayList<String> countries){
        boolean flag = true;
        for (int i = 0; i <4; i++) {
            if (expected.equals(countries.get(i))){ } else {flag=false;}
        }
        return flag;
    }
    private ArrayList<String> loadSettings(String filename){

        return ResourcesHandler.loadFromPropFile(filename);
    }

    private ArrayList<SettingKeyValidation> sortSettingArrayList(ArrayList<SettingKeyValidation> settingKeyValidationArrayList){

        ArrayList<SettingKeyValidation> equallist = new ArrayList<SettingKeyValidation>();
        ArrayList<SettingKeyValidation> notequallist = new ArrayList<SettingKeyValidation>();

        for (int i=0; i < settingKeyValidationArrayList.size(); i++) {
            SettingKeyValidation settingKeyValidation =  settingKeyValidationArrayList.get(i);
            if (settingKeyValidation.isEqual()) {
                equallist.add(settingKeyValidation);
            } else {
                if (!settingKeyValidation.isEqual()){
                    notequallist.add(settingKeyValidation);

                }
            }
        }
        ArrayList<SettingKeyValidation> all = new ArrayList<SettingKeyValidation>();
        all.addAll(notequallist);
        all.addAll(equallist);
        return all;


    }
}
