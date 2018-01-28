package com.webservice;

import com.settings.db.*;
import com.webservice.handler.EnvironmentCheck;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

/**
 * Created by hagairevah on 11/5/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DBConnectionTest {

    @Test
    public void ConnectToKube() throws SQLException{
        DBController dbControllerKub = new KubeDB(90,"il");
        dbControllerKub.connect();
        dbControllerKub.executeQuery("Select * from system_settings");
        System.out.println();
        DBController dbControllerScrum = new ScrumDB(12,"il");
        dbControllerScrum.connect();
        dbControllerScrum.executeQuery("Select * from system_settings");
        System.out.println();
        DBController dbControllerDocker = new DockerDB(16,"il");
        dbControllerDocker.connect();
        dbControllerDocker.executeQuery("Select * from system_settings");
        System.out.println();
        DBController dbControllerProducstion = new ProductionDB(0,"il");
        dbControllerProducstion.connect();
        dbControllerProducstion.executeQuery("Select * from system_settings");



    }
}
