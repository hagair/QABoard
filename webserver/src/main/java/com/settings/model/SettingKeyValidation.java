package com.settings.model;

import java.util.ArrayList;

/**
 * Created by hagairevah on 6/18/17.
 */
public class SettingKeyValidation {
    public SettingKeyValidation() {
        countries = new ArrayList<String>(4);
    }

    String module;
    String key;
    String expected_value;
    boolean equal;
    ArrayList<String> countries;
    String info;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getExpected_value() {
        return expected_value;
    }

    public void setExpected_value(String expected_value) {
        this.expected_value = expected_value;
    }

    public boolean isEqual() {
        return equal;
    }

    public void setEqual(boolean equal) {
        this.equal = equal;
    }

    public ArrayList<String> getCountries() {
        return countries;
    }
    public String getCountry(String country) {
        switch (country){
            case "il":
                return countries.get(0);
            case "uk":
                return countries.get(1);
            case "ru":
                return countries.get(2);
            case "us":
                return countries.get(3);
            default:
                return "";
        }
    }
    public void setCountry(String country, String value) {
        switch (country){
            case "il":
                countries.add(0,value);
                break;
            case "uk":
                countries.add(1,value);
                break;
            case "ru":
                countries.add(2,value);
                break;
            case "us":
                countries.add(3,value);
                break;
            default:
                break;
        }
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
