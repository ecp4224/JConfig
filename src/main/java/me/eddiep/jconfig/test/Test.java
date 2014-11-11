package me.eddiep.jconfig.test;

import me.eddiep.jconfig.JConfig;

import java.io.File;

public class Test {

    public static void main(String[] args) {
        MyConfig config = JConfig.newConfigObject(MyConfig.class);

        config.setPort(1137);
        config.setServerIP("192.168.1.5");

        config.save(new File("test.json"));

        System.out.println("Port: " + config.getPort() + " IP: " + config.getServerIP());
    }
}
