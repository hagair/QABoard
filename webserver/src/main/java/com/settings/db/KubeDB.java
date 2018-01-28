package com.settings.db;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by hagairevah on 1/23/18.
 */
public class KubeDB extends DBController{
    //Data members
    private String DBHost = "mysql.scrum";
    private String DBusername = "root";
    private String DBpassword = "Auto!@2016";

    private String sshDBHost = "dev-ssh.gett.io";
    private final int sshPort = 22;
    private final String sshDBUsername = "gtdeploy";
    private final String sshDBPassword = "gtdeploy!@*";
    private final int lportForPortForwarding=13306;
    private final int rportToConnectDB=3306;

    private Session session;
    private JSch jsch;
    int assinged_port;

    public KubeDB(int scrum, String country) {
        super(scrum, country);

    }

    public void openSSHTunnel() throws JSchException {
        jsch = new JSch();
        try {
            session = jsch.getSession(sshDBUsername, sshDBHost, sshPort);
        } catch (JSchException e) {
            e.printStackTrace();
        }

        System.out.println("SSH Parameters: " + sshDBUsername + ", " + sshDBHost + ", " + sshPort);
        session.setPassword(sshDBPassword);
        session.setConfig("StrictHostKeyChecking", "no");
        System.out.println("Establishing Connection...");
        if (!session.isConnected()) {
            session.connect();
            System.out.println("Connection Established...");
            assinged_port = session.setPortForwardingL(lportForPortForwarding, DBHost + scrum, rportToConnectDB);
        }


    }
    public void openDBConnection(){
        System.out.println("Connecting to Kube DB = "+getURL(country)+".....");
        try {
            connection = DriverManager.getConnection(getURL(country), DBusername, DBpassword);
            System.out.println("Connected!");
        } catch (SQLException e) {
            System.out.println("Failed to Connect to DB!");
            e.printStackTrace();
        }

    }

    public void connect(){
        try {
            openSSHTunnel();
            openDBConnection();
        } catch (Exception js){
            session.disconnect();
        }
    }


    public void disconnect(){
        try {
            connection.close();
        } catch (Exception er){
            er.printStackTrace();
        }
        try {
            session.delPortForwardingL(lportForPortForwarding);

        } catch (JSchException e) {
            System.out.println("Failed to close a session!");
            e.printStackTrace();
        }
        session.disconnect();
        jsch = null;
    }
    private String getURL(String country){
        return "jdbc:mysql://127.0.0.1:"+lportForPortForwarding+"/"+country+"scrum0";
    }

}
