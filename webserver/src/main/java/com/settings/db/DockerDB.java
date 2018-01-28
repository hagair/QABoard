package com.settings.db;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by hagairevah on 1/23/18.
 */
public class DockerDB extends DBController{
    //Data members
    private String DBHost;
    private String DBusername = "scrum";
    private String DBpassword = "scrum";

    private Session session;
    private JSch jsch;

    public DockerDB(int scrum, String country) {
        super(scrum, country);
    }

    public void connect(){
        try {
            System.out.println("Connecting to Docker DB = "+getURL(country)+".....");
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
        return "jdbc:mysql://Db-scrum"+scrum+".gett.io:3306/"+country+"scrum0";
    }
    private String getUserPassword(String country){
        return country+DBpassword+"0";
    }
}
