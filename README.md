JConfig
=======

A dynamic config class creator for those lazy devs!

#Creating a config class

Simply create an interface that extends the Config class with the
methods you would like in your config class.

Then annotate your methods with the correct annontations

ex:
```java
@DefaultLocation(location = "config.json") //Specifies the default location of the config when no file is specified
public interface MyConfig extends Config {

    @Getter(property = "port") //Specifies that this method is a getter for the property "port"
    public int getPort();

    @Operation(type = OperationType.SAVE) //Save every time this method is called
    @Setter(property = "port") //Specifies that this method is a setter for the property "port"
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

    @Operation(type = OperationTyp
e.SAVE)
    public void save();

    @Operation(type = OperationType.LOAD)
    public void load();
}
```

#Usage
Once you create your config interface, you can make a new instances of your config class by doing the following:


```java
MyConfig config = JConfig.newConfigObject(MyConfig.class);
```

Once you have an instance of it, you can use the methods normally:

```java
MyConfig config = JConfig.newConfigObject(MyConfig.class);

config.setPort(1137);
config.setServerIP("192.168.1.5");

config.save(new File("test.json"));

System.out.println("Port: " + config.getPort() + " IP: " + config.getServerIP());
```
