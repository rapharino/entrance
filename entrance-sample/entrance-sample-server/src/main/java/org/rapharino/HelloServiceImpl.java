package org.rapharino;

import org.rapharino.api.HelloWorldService;
import org.rapharino.entrance.core.annotation.API;

/**
 * Created By Rapharino on 12/10/2017 4:36 PM
 */
@API(name = "hello", value = HelloWorldService.class)
public class HelloServiceImpl implements HelloWorldService {

    @Override
    public String hello(String name) {
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello " + name +" from 127.0.0.1:"+System.getProperty("port");
    }
}
