package me.eddiep.jconfig.system.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify a default file location for this config interface to be saved when one
 * is not provided in the {@link me.eddiep.jconfig.system.Config#save(java.io.File)} method.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultLocation {
    /**
     * The default file location to save this config interface
     * @return The default file location
     */
    String location();
}
