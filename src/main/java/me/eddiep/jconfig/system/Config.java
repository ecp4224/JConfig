package me.eddiep.jconfig.system;

import java.io.File;

public interface Config {

    public void save(String file);

    public void save(File file);

    public void load(String file);

    public void load(File file);
}
