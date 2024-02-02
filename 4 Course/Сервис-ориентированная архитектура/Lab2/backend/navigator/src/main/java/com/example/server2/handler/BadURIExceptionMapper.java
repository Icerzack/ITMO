package com.example.server2.handler;

import com.example.server2.model.ErrorDefaultDTO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Provider
public class BadURIExceptionMapper extends Throwable implements ExceptionMapper<NotFoundException> {

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(NotFoundException exception){
        System.out.println(exception.getClass().getCanonicalName() + "\n" + exception.getMessage());
        int code = 400;
        String message = "Несуществующий URI";
        ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
        String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        int pos = formattedCurrentDateTime.indexOf(".");
        formattedCurrentDateTime = formattedCurrentDateTime.replace('.', 'Z');
        formattedCurrentDateTime = formattedCurrentDateTime.substring(0, pos+1);
        ErrorDefaultDTO errorDTO = new ErrorDefaultDTO();
        errorDTO.setCode(code);
        errorDTO.setMessage(message);
        errorDTO.setTime(formattedCurrentDateTime);
        return Response.status(code)
                .entity(errorDTO)
                .type("application/json")
                .build();
    }
}
