package com.example.server2.handler;


import com.example.server2.model.ErrorDefaultDTO;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import lombok.extern.slf4j.Slf4j;

@Provider
public class DateTimeParseMapper implements ExceptionMapper<DateTimeParseException> {
  @Override
  public Response toResponse(DateTimeParseException exception) {
    System.out.println(exception.getClass().getCanonicalName() + "\n" + exception.getMessage());
    int code = 422;
    String message = "Ошибка с парсингом даты, " + exception.getMessage();
    ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
    String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
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