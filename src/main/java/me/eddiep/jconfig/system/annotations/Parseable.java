package me.eddiep.jconfig.system.annotations;

import me.eddiep.jconfig.system.StringParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify this class is parseable, and can be written to a config interface
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Parseable {
    /**
     * The {@link me.eddiep.jconfig.system.StringParser} class that will serialize and deserialize this class
     * @return The {@link me.eddiep.jconfig.system.StringParser} class that will serialize this class
     */
    Class<? extends StringParser<?>> parser();
}
