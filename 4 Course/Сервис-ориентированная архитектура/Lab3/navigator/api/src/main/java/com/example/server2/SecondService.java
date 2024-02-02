package com.example.server2;

import com.example.server2.configuration.HttpsFilter;
import com.example.server2.controller.NavigatorController;
import com.example.server2.handler.*;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class SecondService extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(HttpsFilter.class);
        classes.add(NavigatorController.class);
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