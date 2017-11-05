package com.webservice;

import com.webservice.handler.EnvironmentCheck;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by hagairevah on 11/5/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class envCheckTests {
    EnvironmentCheck environmentCheck;

    @Test
    public void ciForgeAPITest(){
        environmentCheck = new EnvironmentCheck();
        environmentCheck.liveServerDetailsHashMap("scrum12");

    }
}
