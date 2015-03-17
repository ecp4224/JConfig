package me.eddiep.jconfig.system;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import me.eddiep.jconfig.parsers.Parser;
import me.eddiep.jconfig.system.annotations.*;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class ConfigProxy implements InvocationHandler {
    private Parser parser;
    private String defaultLocation;
    private Class<? extends Config> configClass;
    private HashMap<Method, Getter> getters = new HashMap<>();
    private HashMap<Method, Setter> setters = new HashMap<>();
    private HashMap<Method, Operation> operations = new HashMap<>();
    private WeakHashMap<Class<? extends StringParser<?>>, StringParser<?>> stringParserCache = new WeakHashMap<>();

    private ConfigProxy(Parser parser, Class<? extends Config> configClass) {
        this.parser = parser;
        this.configClass = configClass;
        setupConfig(configClass);
    }

    private void setupConfig(Class<? extends Config> configClass) {
        DefaultLocation loc;
        if ((loc = configClass.getAnnotation(DefaultLocation.class)) != null) {
            defaultLocation = loc.location();
        }

        List<Method> methods = new ArrayList<Method>();
        methods.addAll(Arrays.asList(configClass.getMethods()));

        Class parent = configClass.getSuperclass();
        while (parent != null) {
            methods.addAll(Arrays.asList(parent.getDeclaredMethods()));
            parent = configClass.getSuperclass();
        }

        for (Method m : methods) {
            Annotation[] prop = m.getDeclaredAnnotations();
            for (Annotation annotation : prop) {
                if (annotation instanceof Getter) {
                    getters.put(m, (Getter)annotation);
                }

                if (annotation instanceof Setter) {
                    if (getters.containsKey(m))
                        throw new IllegalArgumentException("A method cannot be both a Setter and a Getter!");
                    setters.put(m, (Setter)annotation);
                }

                if (annotation instanceof Operation) {
                    operations.put(m, (Operation)annotation);
                }

                if (annotation instanceof DefaultValue) {
                    String property;
                    if (getters.containsKey(m))
                        property = getters.get(m).property();
                    else if (setters.containsKey(m))
                        property = setters.get(m).property();
                    else
                        throw new IllegalArgumentException("This method is not a Getter or a Setter, so it cannot have a default value!");

                    parser.setProperty(property, ((DefaultValue)annotation).value());
                }
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        if (getters.containsKey(method)) {
            if (returnType.equals(Void.TYPE))
                throw new IllegalArgumentException("This getter has a void return type!");

            String rawValue = parser.getProperty(getters.get(method).property());
            return convertRawValue(rawValue, returnType);
        }
        if (setters.containsKey(method)) {
            if (args.length == 0) throw new IllegalArgumentException("No value provided!");
            if (args.length > 1) System.err.println("[JConfig] This Setter method has more than 1 parameter, JConfig will only use the first one as the value!");

            String rawValue = convertToRawValue(args[0], args[0].getClass());

            parser.setProperty(setters.get(method).property(), rawValue);

            if (!returnType.equals(Void.TYPE)) {
                if (returnType.equals(args[0].getClass()))
                    return args[0];
                else if (returnType.equals(configClass)) {
                    return proxy;
                }
                else {
                    System.err.println("[JConfig] Unknown return type! Return null!");
                    return null;
                }
            }
        }

        if (operations.containsKey(method)) {
            OperationType type = operations.get(method).type();

            switch (type) {
                case SAVE:
                    if (defaultLocation != null)
                        parser.save(new File(defaultLocation));
                    else if (args[0] instanceof String)
                        parser.save(new File(args[0].toString()));
                    else if (args[0] instanceof File)
                        parser.save((File) args[0]);
                    break;
                case LOAD:
                    if (defaultLocation != null)
                        parser.load(new File(defaultLocation));
                    else if (args[0] instanceof String)
                        parser.load(new File(args[0].toString()));
                    else if (args[0] instanceof File)
                        parser.load((File) args[0]);
                    break;
            }
        }
        return null;
    }

    private String convertToRawValue(Object obj, Class<?> returnType) {
        for (Annotation annotation : returnType.getDeclaredAnnotations()) {
            if (annotation instanceof Parseable) {
                Class<? extends StringParser<?>> parseclass = ((Parseable)annotation).parser();

                StringParser parser;
                if (!stringParserCache.containsKey(parseclass)) {
                    ConstructorAccess<? extends StringParser> access = ConstructorAccess.get(parseclass);

                    parser = access.newInstance();

                    stringParserCache.put(parseclass, parser);
                } else parser = stringParserCache.get(parseclass);

                try {
                    return parser.serialize(obj);
                } catch(Throwable ignored) { }
            }
        }

        return obj.toString();
    }

    private Object convertRawValue(String rawValue, Class<?> returnType) {
        if (returnType.equals(String.class))
            return rawValue;
        if (returnType.equals(int.class)) {
            return Integer.parseInt(rawValue);
        }
        if (returnType.equals(boolean.class)) {
            return Boolean.parseBoolean(rawValue);
        }
        if (returnType.equals(float.class)) {
            return Float.parseFloat(rawValue);
        }
        if (returnType.equals(double.class)) {
            return Double.parseDouble(rawValue);
        }
        if (returnType.equals(long.class)) {
            return Long.parseLong(rawValue);
        }
        if (returnType.equals(byte.class)) {
            return Byte.parseByte(rawValue);
        }
        if (returnType.equals(short.class)) {
            return Short.parseShort(rawValue);
        }
        if (returnType.equals(char.class)) {
            return rawValue.toCharArray()[0];
        }
        for (Annotation annotation : returnType.getDeclaredAnnotations()) {
            if (annotation instanceof Parseable) {
                Class<? extends StringParser<?>> parseclass = ((Parseable)annotation).parser();

                StringParser<?> parser;
                if (!stringParserCache.containsKey(parseclass)) {
                    ConstructorAccess<? extends StringParser<?>> access = ConstructorAccess.get(parseclass);

                    parser = access.newInstance();

                    stringParserCache.put(parseclass, parser);
                } else parser = stringParserCache.get(parseclass);

                try {
                    return parser.deserialize(rawValue);
                } catch(Throwable ignored) { }
            }
        }

        return null;
    }

    public static <T extends Config> T createConfigProxy(Class<T> configClass, Parser parser) {
        ConfigProxy proxy = new ConfigProxy(parser, configClass);

        return (T) Proxy.newProxyInstance(configClass.getClassLoader(), new Class[] { configClass}, proxy);
    }
}
