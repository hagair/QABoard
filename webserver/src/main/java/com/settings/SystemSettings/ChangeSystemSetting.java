package com.settings.SystemSettings;

import com.utils.ResourcesHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hagairevah on 6/25/17.
 */
public class ChangeSystemSetting {
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
}
