package com.settings.db;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by hagairevah on 1/23/18.
 */
public class ProductionDB extends DBController{
    //Data members
    private String DBHost;
    private String DBusername = "automation";
    private String DBpassword = "Auto!@2016";

    private Session session;
    private JSch jsch;

    public ProductionDB(int scrum, String country) {
        super(scrum, country);
    }

    public void connect(){
        try {
            System.out.println("Connecting to Production DB = "+getURL(country)+".....");
            connection = DriverManager.getConnection(getURL(country), DBusername, DBpassword);
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

        return "jdbc:mysql://gt-"+country+"-cloned-db.gtforge.com/gettaxi_"+country+"_production";
    }
}
