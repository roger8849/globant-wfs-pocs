package com.globant.testing.framework.web.test.pageobject.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks POM classes to automatically navigate to a certain URL after instantiation of POM.
 * <p>
 * <b>NOTE</b> <i>SUT_URL</i> environment variable <b>overrides</b> value provided by this annotation.
 *
 * @author Juan Krzemien
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Url {
    String value() default "";
}
