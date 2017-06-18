package com.webservice.handler;

import com.settings.SystemSettings.SystemSettingDiff;
import com.settings.model.SettingKey;
import com.utils.ResourcesHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by hagairevah on 6/12/17.
 */
public class SystemSettings {
    String scrum;

    public SystemSettings(String scrum) {
        this.scrum = scrum;
    }
    public String getSettingsTableHTML(String filename){
        SystemSettingDiff systemSettingDiff = new SystemSettingDiff();
        ArrayList<HashMap<String,SettingKey>> ss = systemSettingDiff.getDiffsFromAllCountries(scrum);

        return settingTableCreator(ss,filename);
    }
    private String settingTableCreator(ArrayList<HashMap<String,SettingKey>> list, String filename){
        String s = "";
        String v1 = "";
        String v2 = "";
        String v3 = "";
//        HashMap<String,SettingKey> countryIL = list.get(0);
        Map<String,String> keyMap = ResourcesHandler.loadPropertyFileToMap(filename);

        Set<String> set = keyMap.keySet();
        for (String key : set) {
//            System.out.println(key);
            if (list.get(1).get(key) != null){
                v1 = list.get(1).get(key).getValue();
//                System.out.println(v1);
            } else {v1="";}
            if (list.get(2).get(key) != null){
                v2 = list.get(2).get(key).getValue();
//                System.out.println(v2);
            } else {v2="";}
            if (list.get(3).get(key) != null){
                v3 = list.get(3).get(key).getValue();
//                System.out.println(v3);
            } else {v3="";}
                s = s + "<tr><td>" + list.get(0).get(key).getModule() +
                        "</td><td>" + list.get(0).get(key).getKey() +
                        "</td><td>" + keyMap.get(key) +
                        "</td><td>" + list.get(0).get(key).getValue() +
                        "</td><td>" + v1 +
                        "</td><td>" + v2 +
                        "</td><td>" + v3 +
                        "</td><td>" + list.get(0).get(key).getInformation() +
                        "</td></tr>";
        }

        return s;
    }
    private ArrayList<String> loadSettings(String filename){
        return ResourcesHandler.loadFromPropFile(filename);
    }

}
