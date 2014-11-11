package me.eddiep.jconfig.system.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify that this method is a getter for a specific config property.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Getter {
    /**
     * The property this method retrieves
     * @return The property name
     */
    String property();
}
