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
    int scrum;
    ArrayList<HashMap<String,SettingKey>> prodSettings;
    SystemSettingDiff systemSettingDiff;
    public SystemSettings(int scrum) {

        this.scrum = scrum;
        this.systemSettingDiff = new SystemSettingDiff();
        this.prodSettings = systemSettingDiff.getDiffsFromAllCountries(0);

    }

    public String getSettingsTableHTML(String filename, boolean all){
        SystemSettingDiff systemSettingDiff = new SystemSettingDiff();
        ArrayList<HashMap<String,SettingKey>> scrumSettings = systemSettingDiff.getDiffsFromAllCountries(scrum);
        ArrayList<SettingKeyValidation> settingKeyValidationArrayList = getSettingKeyValidationList(scrumSettings,filename);
        if (all){
            settingKeyValidationArrayList.addAll(settingKeyValidationArrayList.size(),getSettingKeynonValidationList(scrumSettings,filename));
        }
        return settingTableCreatorByList(settingKeyValidationArrayList);
    }

    public ArrayList<SettingKeyValidation> getSettingsTableJSON(String filename){
        SystemSettingDiff systemSettingDiff = new SystemSettingDiff();
        ArrayList<HashMap<String,SettingKey>> ss = systemSettingDiff.getDiffsFromAllCountries(scrum);

        return getSettingKeyValidationList(ss,filename);
    }
    private ArrayList<SettingKeyValidation> getSettingKeynonValidationList(ArrayList<HashMap<String,SettingKey>> list, String filename) {
        String s = "";
        String v1 = "";
        String v2 = "";
        String v3 = "";
        ArrayList<SettingKeyValidation> settingKeyValidationArrayList = new ArrayList<SettingKeyValidation>();
        Map<String,String> keyMap = ResourcesHandler.loadPropertyFileToMap(filename);
        Set<String> set = list.get(0).keySet();
        System.out.println(set.size());
        for (String key : set) {
            if (keyMap.get(key)==null) {
                SettingKeyValidation settingKeyValidation = new SettingKeyValidation();
                settingKeyValidation.setModule(list.get(0).get(key).getModule());
                settingKeyValidation.setKey(list.get(0).get(key).getKey());
                settingKeyValidation.setExpected_value(keyMap.get(key));
                settingKeyValidation.setInfo(list.get(0).get(key).getInformation());

                if (list.get(1).get(key) != null) {
                    v1 = list.get(1).get(key).getValue();
                } else {
                    v1 = "";
                }
                if (list.get(2).get(key) != null) {
                    v2 = list.get(2).get(key).getValue();
                } else {
                    v2 = "";
                }
                if (list.get(3).get(key) != null) {
                    v3 = list.get(3).get(key).getValue();
                } else {
                    v3 = "";
                }

                settingKeyValidation.setCountry("il", list.get(0).get(key).getValue());
                settingKeyValidation.setCountry("uk", v1);
                settingKeyValidation.setCountry("ru", v2);
                settingKeyValidation.setCountry("us", v3);
                settingKeyValidation.setEqual(setValidation(settingKeyValidation.getExpected_value(), settingKeyValidation.getCountries()));
                settingKeyValidationArrayList.add(settingKeyValidation);
            }
        }
        return settingKeyValidationArrayList;

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
            System.out.println(i);
            SettingKeyValidation settingKeyValidation = new SettingKeyValidation();
            try {
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
//                if (list.get(3).get(key) != null){
//                    v3 = list.get(3).get(key).getValue();
//                } else {v3="";}

                settingKeyValidation.setCountry("il",list.get(0).get(key).getValue());
                settingKeyValidation.setCountry("uk",v1);
                settingKeyValidation.setCountry("ru",v2);
//                settingKeyValidation.setCountry("us",v3);
                settingKeyValidation.setEqual(setValidation(settingKeyValidation.getExpected_value(),settingKeyValidation.getCountries()));
                settingKeyValidationArrayList.add(settingKeyValidation);
            } catch (Exception e){
                System.out.println(key +" was not found");
            }

        }
        return settingKeyValidationArrayList;
    }
    private String settingTableCreatorByList(ArrayList<SettingKeyValidation> settingKeyValidationList){
        String tableString = "";
        settingKeyValidationList = sortSettingArrayList(settingKeyValidationList);
        for (int i = 0; i < settingKeyValidationList.size(); i++) {
            SettingKeyValidation settingKeyValidation = settingKeyValidationList.get(i);

            if (settingKeyValidation.isEqual()){
                try {
                    System.out.println(settingKeyValidation.getKey());
                tableString = tableString + "<tr bgcolor=\"#BDFA99\">" +
                        "<td>" + settingKeyValidation.getModule() +
                        "</td><td>" + settingKeyValidation.getKey() +
                        "</td><td>" + settingKeyValidation.getExpected_value() +
                        "</td><td>" + settingKeyValidation.getCountry("il") +
                        "</td><td>" + settingKeyValidation.getCountry("uk") +
                        "</td><td>" + settingKeyValidation.getCountry("ru");
                if (prodSettings.size()>=3){
                        //"</td><td>" + settingKeyValidation.getCountry("us") +
                    tableString = tableString + "</td><td>"+prodSettings.get(0).get(settingKeyValidation.getKey()).getValue() +
                        "</td><td>" + prodSettings.get(1).get(settingKeyValidation.getKey()).getValue() +
                        "</td><td>" + prodSettings.get(2).get(settingKeyValidation.getKey()).getValue() +
                        //"</td><td>" + prodSettings.get(3).get(settingKeyValidation.getKey()).getValue() +
                        "</td><td>" + settingKeyValidation.getInfo() +
                        "</td></tr>";}
                        else {
                    System.out.println(prodSettings.size());
                    tableString = tableString + "</td><td></td><td></td><td>"+
                            "</td><td>" + settingKeyValidation.getInfo() +
                            "</td></tr>";}

                } catch (Exception e){
                    e.printStackTrace();
                }

            } else {
                try {
                    tableString = tableString + "<tr bgcolor=\"#FF3636\">" +
                            "<td>" + settingKeyValidation.getModule() +
                            "</td><td>" + settingKeyValidation.getKey() +
                            "</td><td>" + settingKeyValidation.getExpected_value() +
                            "<br><a href=http://qaboard.gett.io:8080/settings/changeScrumValues?scrum=" + scrum + "&key=" + settingKeyValidation.getKey() + "&value=" + settingKeyValidation.getExpected_value() + ">Align Values</a>" +
                            "</td><td>" + settingKeyValidation.getCountry("il") +
                            "</td><td>" + settingKeyValidation.getCountry("uk") +
                            "</td><td>" + settingKeyValidation.getCountry("ru");
                            //"</td><td>" + settingKeyValidation.getCountry("us") +
                    if (prodSettings.size()>=3){
                        tableString = tableString +"</td><td>" + prodSettings.get(0).get(settingKeyValidation.getKey()).getValue() +
                            "</td><td>" + prodSettings.get(1).get(settingKeyValidation.getKey()).getValue() +
                            "</td><td>" + prodSettings.get(2).get(settingKeyValidation.getKey()).getValue() +
                            //"</td><td>" + prodSettings.get(3).get(settingKeyValidation.getKey()).getValue() +
                            "</td><td>" + settingKeyValidation.getInfo() +
                            "</td></tr>";}
                    else {
                        System.out.println(prodSettings.size());
                        tableString = tableString + "</td><td></td><td></td><td>"+
                                "</td><td>" + settingKeyValidation.getInfo() +
                                "</td></tr>";}
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
        return tableString;
    }
    private boolean setValidation(String expected, ArrayList<String> countries){
        String[] values = new String[3];

        if ((expected.substring(0,1).contentEquals("["))&&(expected.substring(expected.length()-1,expected.length()).contentEquals("]"))){
            values = expected.substring(1,expected.length()-1).split(",");
        } else {
            values[0]=expected;
            values[1]=expected;
            values[2]=expected;
        }
        boolean flag = true;
        for (int i = 0; i <3; i++) {
            if (values[i].equals(countries.get(i))){

            } else {
                flag=false;
            }
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
