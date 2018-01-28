package com.settings.db;

import com.jcraft.jsch.Session;

import java.sql.*;

/**
 * Created by hagairevah on 1/25/18.
 */
public abstract class DBController {
    Connection connection;
    int scrum;
    String country;

    public DBController(int scrum, String country) {
        this.scrum = scrum;
        this.country = country;
    }

    public abstract void connect();

    public abstract void disconnect();

    public ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }

    public int updateQuery(String query) throws SQLException {
        // create the java mysql update preparedstatement
        PreparedStatement preparedStmt = this.connection.prepareStatement(query);
        int rs = preparedStmt.executeUpdate(query);
        return rs;
    }
}
