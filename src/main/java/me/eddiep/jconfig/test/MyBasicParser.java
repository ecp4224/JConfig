package me.eddiep.jconfig.test;

import me.eddiep.jconfig.system.StringParser;

public class MyBasicParser implements StringParser<MyObject> {
    @Override
    public MyObject deserialize(String rawString) {
        MyObject obj = new MyObject();

        String[] s = rawString.split(":");
        obj.x = Integer.parseInt(s[0]);
        obj.y = Integer.parseInt(s[1]);
        obj.z = Integer.parseInt(s[2]);

        return obj;
    }

    @Override
    public String serialize(MyObject obj) {
        return obj.x + ":" + obj.y + ":" + obj.z;
    }
}
