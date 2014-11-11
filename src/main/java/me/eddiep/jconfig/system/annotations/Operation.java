package me.eddiep.jconfig.system.annotations;

import me.eddiep.jconfig.system.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify that this method does a config operation when it's invoked
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Operation {
    /**
     * The operation this method should perform once invoked.
     * @return The {@link me.eddiep.jconfig.system.OperationType} to perform.
     */
    OperationType type();
}
