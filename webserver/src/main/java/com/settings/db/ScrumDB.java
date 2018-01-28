package com.settings.db;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by hagairevah on 1/23/18.
 */
public class ScrumDB extends DBController{
    //Data members
    private String DBHost = "jdbc:mysql://scrum-rds.gett.io:3306";
    private String DBusername = "scrum";
    private String DBpassword = "scrum";


    public ScrumDB(int scrum, String country) {
        super(scrum, country);
    }

    public void connect(){
        try {
            System.out.println("Connecting to SCRUM DB = "+getURL(country)+".....");
            connection = DriverManager.getConnection(getURL(country), getUserPassword(country), getUserPassword(country));
            System.out.println("Connected!");
        } catch (SQLException s){
            System.out.println("Connection Failed!");
        }
    }

    public void disconnect(){
        try{
            connection.close();
        } catch (SQLException s){
            System.out.println("Connection closed!");
        }
    }
    private String getURL(String country){
        return DBHost+"/"+country+"scrum"+scrum;
    }
    private String getUserPassword(String country){
        return country+DBpassword+scrum;
    }
}
