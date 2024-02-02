package com.example.server2.handler;


import com.example.server2.model.ErrorDefaultDTO;
import java.util.Arrays;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;

@Provider
public class NotReadableMapper implements ExceptionMapper<Exception> {
  @Override
  public Response toResponse(Exception exception) {
    System.out.println(exception.getClass().getCanonicalName() + " " + exception.getMessage());
    exception.printStackTrace();
    int code = 422;
    String message = "Невалидный формат данных";
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