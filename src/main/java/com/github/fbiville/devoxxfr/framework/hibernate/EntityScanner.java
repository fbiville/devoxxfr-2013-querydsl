package com.github.fbiville.devoxxfr.framework.hibernate;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import javax.persistence.Entity;
import java.util.Collection;

public class EntityScanner {

    public static Collection<Class<?>> scanEntities(String basePackage) {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage(basePackage))
        );
        return reflections.getTypesAnnotatedWith(Entity.class);
    }
}
