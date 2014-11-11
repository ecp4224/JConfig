package me.eddiep.jconfig.parsers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Hashtable;

public class JsonParser extends HashTableParser {
    private static final Gson GSON = new Gson();
    @Override
    public void load(File file) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));

        Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        super.properties = GSON.fromJson(json, type);
    }

    @Override
    public void save(File file) throws IOException {
        String data = GSON.toJson(super.properties);

        PrintWriter out = new PrintWriter(file);
        out.println(data);
        out.flush();
        out.close();
    }
}
