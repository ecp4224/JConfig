package me.eddiep.jconfig.parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Parser {

    public String getProperty(String property);

    public void setProperty(String property, String value);

    public void load(File file) throws IOException;

    public void save(File file) throws IOException;
}
