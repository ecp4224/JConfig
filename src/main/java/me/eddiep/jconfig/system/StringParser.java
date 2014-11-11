package me.eddiep.jconfig.system;

public interface StringParser<T> {

    public T deserialize(String rawString);

    public String serialize(T obj);
}
