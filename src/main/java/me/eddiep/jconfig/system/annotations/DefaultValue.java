package me.eddiep.jconfig.system.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify a default <b>string</b> value for this property. <b>The {@link me.eddiep.jconfig.system.annotations.Setter}
 * annotation must be declared above this annotation.</b>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultValue {
    /**
     * The default <b>string</b> value of this property
     * @return The default <b>string</b> value
     */
    String value();
}
