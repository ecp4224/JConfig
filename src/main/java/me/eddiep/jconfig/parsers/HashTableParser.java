package me.eddiep.jconfig.parsers;

import java.io.File;
import java.util.HashMap;

public abstract class HashTableParser implements Parser {
    protected HashMap<String, String> properties = new HashMap<>();

    @Override
    public String getProperty(String property) {
        return properties.get(property);
    }

    @Override
    public void setProperty(String property, String value) {
        properties.put(property, value);
    }
}
