package com.utils;

import java.io.*;
import java.util.*;

/**
 * Created by hagairevah on 6/13/17.
 */
public class ResourcesHandler {
    private static String path = System.getProperty("user.dir");
    private static String innerpath = "/classes/static/";

    public static void printPath(){
        System.out.println(path+innerpath);
    }
    public static void setPath(String condition){
        if (condition.equals("debug")){
            innerpath = "/src/main/resources/static/";
        }
    }
    public static ArrayList<String> loadFromPropFile(String filename){
        ArrayList list = new ArrayList();

        BufferedReader br;
        try {
            FileReader file = new FileReader(path+innerpath+filename);
            br = new BufferedReader(file);
            try {
                String x;
                while ( (x = br.readLine()) != null ) {
                    // Printing out each line in the file
                    //System.out.println(x);
                    list.add(x);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return list;
    }
    public static String loadTxtFile(String filename) {
        System.out.println("loading test file from: "+path+innerpath+filename);
        File file = new File(path+innerpath+filename);
        StringBuilder fileContents = new StringBuilder((int)file.length());
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            String lineSeparator = System.getProperty("line.separator");


            while (scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } catch (IOException e){

        return "";
        }
    }

    public static void writeMapToPropertyFile(String filename, Map<String,String> keyMap) throws IOException {
        BufferedWriter output = null;
        File file = new File(path+innerpath+filename);
        output = new BufferedWriter(new FileWriter(file));
        Set<String> mapSet = keyMap.keySet();
        for (String s : mapSet) {
            output.write(s+"="+keyMap.get(s));
            output.newLine();
        }
        output.close();

    }
    public static Map<String,String> loadPropertyFileToMap(String filename) {
        FileInputStream fis;
        try {
            System.out.printf("load property from: "+path+innerpath+filename);
            fis = new FileInputStream(path+innerpath+filename);
            ResourceBundle resources = new PropertyResourceBundle(fis);
            Map<String,String> map = new HashMap<String,String>();

            //convert ResourceBundle to Map
            Enumeration<String> keys = resources.getKeys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                map.put(key, resources.getString(key));
            }
            //Now you can use the 'map' object as you wish.
            return map;

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new HashMap<String,String>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new HashMap<String,String>();

        }


    }
}
