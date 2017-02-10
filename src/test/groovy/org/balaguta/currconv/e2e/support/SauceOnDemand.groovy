package org.balaguta.currconv.e2e.support;

import org.spockframework.runtime.extension.ExtensionAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE])
@ExtensionAnnotation(SauceOnDemandExtension.class)
public @interface SauceOnDemand {
}
