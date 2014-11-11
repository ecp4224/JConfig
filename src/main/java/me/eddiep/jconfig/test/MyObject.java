package me.eddiep.jconfig.test;

import me.eddiep.jconfig.system.annotations.Parseable;

@Parseable(parser = MyBasicParser.class)
public class MyObject {
    public int x, y, z;
}
