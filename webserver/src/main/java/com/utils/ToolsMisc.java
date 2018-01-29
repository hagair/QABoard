package com.utils;

/**
 * Created by hagairevah on 1/28/18.
 */
public class ToolsMisc {

    public static int getScrumNumber(String scrum){
         String s = scrum.substring(5);
        int i = Integer.parseInt(s);
        return i;
    }
}
