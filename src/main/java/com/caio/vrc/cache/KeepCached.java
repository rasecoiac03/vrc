package com.caio.vrc.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to inform that method in cacheable
 * 
 * @author caio.silva
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface KeepCached {

	/**
	 * 
	 * @return setted expiration time
	 */
	int expire() default -1;

}
