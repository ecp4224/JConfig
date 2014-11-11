package me.eddiep.jconfig;

import me.eddiep.jconfig.parsers.JsonParser;
import me.eddiep.jconfig.parsers.Parser;
import me.eddiep.jconfig.system.Config;
import me.eddiep.jconfig.system.ConfigProxy;

public class JConfig {

    public static <T extends Config> T newConfigObject(Class<T> configClass) {
        return newConfigObject(configClass, new JsonParser());
    }

    public static <T extends Config> T newConfigObject(Class<T> configClass, Parser parser) {
        return ConfigProxy.createConfigProxy(configClass, parser);
    }
}
