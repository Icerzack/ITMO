package com.example.server2;

import com.example.server2.configuration.HttpsFilter;
import com.example.server2.controller.Navigator;
import com.example.server2.handler.*;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class SecondService extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(HttpsFilter.class);
        classes.add(Navigator.class);
        classes.add(NotFoundMapper.class);
        classes.add(DateTimeParseMapper.class);
        classes.add(InvalidFormatMapper.class);
        classes.add(NotReadableMapper.class);
        classes.add(ResourceNotFoundMapper.class);
        classes.add(BadURIExceptionMapper.class);
        classes.add(ConstraintViolationExceptionMapper.class);
        return classes;
    }

}