package me.eddiep.jconfig.test;

import me.eddiep.jconfig.system.Config;
import me.eddiep.jconfig.system.OperationType;
import me.eddiep.jconfig.system.annotations.*;

@DefaultLocation(location = "config.json")
public interface MyConfig extends Config {

    @Getter(property = "port")
    public int getPort();

    @Operation(type = OperationType.SAVE) //Save every time this method is called
    @Setter(property = "port")
    public void setPort(int i);

    @Getter(property = "serverIP")
    public String getServerIP();

    @Operation(type = OperationType.SAVE) //Save every time this method is called
    @Setter(property = "serverIP")
    @DefaultValue(value = "127.0.0.1")
    public void setServerIP(String s);

    @Setter(property = "customObj")
    public void setCustomObject(MyObject obj);

    @Getter(property = "customObj")
    public MyObject getCustomObject();

    @Operation(type = OperationType.SAVE)
    public void save();

    @Operation(type = OperationType.LOAD)
    public void load();
}
