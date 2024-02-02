package com.example.server2.controller;

import com.dto.ErrorDefaultDTO;
import com.example.server2.util.Jndi;
import com.exception.FirstServiceUnavailableException;
import com.exception.NotValidParamsException;
import com.service.NavigatorService;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/v1")
public class NavigatorController {
    private NavigatorService getService() {
        return Jndi.getFromContext(NavigatorService.class,
            "ejb:/logic-1-jar-with-dependencies/NavigatorServiceImpl!com.service.NavigatorService");
    }

    @GET
    @Path("/health")
    public Response checkHealth() {
        return Response.ok().build();
    }

    @GET
    @Path("/route/{id-from}/{id-to}/shortest")
    public Response findShortest(@PathParam("id-from") String idFrom, @PathParam("id-to") String idTo) {
        try {
            return Response.status(Response.Status.OK).entity(getService().findShortest(idFrom, idTo)).build();
        } catch (NumberFormatException e) {
            System.out.println(e.getClass().getCanonicalName());
            ErrorDefaultDTO errorDefaultDTO = new ErrorDefaultDTO();
            errorDefaultDTO.setCode(404);
            errorDefaultDTO.setMessage("id should be Long Number");
            ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
            String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
            int pos = formattedCurrentDateTime.indexOf(".");
            formattedCurrentDateTime = formattedCurrentDateTime.replace('.', 'Z');
            formattedCurrentDateTime = formattedCurrentDateTime.substring(0, pos+1);
            errorDefaultDTO.setTime(formattedCurrentDateTime);
            return Response.status(errorDefaultDTO.getCode()).entity(errorDefaultDTO).build();
        } catch (NotValidParamsException e) {
            System.out.println(e.getClass().getCanonicalName());
            ErrorDefaultDTO errorDefaultDTO = new ErrorDefaultDTO();
            errorDefaultDTO.setCode(404);
            errorDefaultDTO.setMessage(e.getMessage());
            ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
            String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
            int pos = formattedCurrentDateTime.indexOf(".");
            formattedCurrentDateTime = formattedCurrentDateTime.replace('.', 'Z');
            formattedCurrentDateTime = formattedCurrentDateTime.substring(0, pos+1);
            errorDefaultDTO.setTime(formattedCurrentDateTime);
            return Response.status(errorDefaultDTO.getCode()).entity(errorDefaultDTO).build();
        } catch (FirstServiceUnavailableException e) {
            System.out.println(e.getClass().getCanonicalName());
            ErrorDefaultDTO errorDefaultDTO = new ErrorDefaultDTO();
            errorDefaultDTO.setCode(500);
            errorDefaultDTO.setMessage("проверьте корректную работоспособность первого сервиса");
            ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
            String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
            int pos = formattedCurrentDateTime.indexOf(".");
            formattedCurrentDateTime = formattedCurrentDateTime.replace('.', 'Z');
            formattedCurrentDateTime = formattedCurrentDateTime.substring(0, pos+1);
            errorDefaultDTO.setTime(formattedCurrentDateTime);
            return Response.status(errorDefaultDTO.getCode()).entity(errorDefaultDTO).build();
        } catch (Exception e) {
            System.out.println(e.getClass().getCanonicalName());
            e.printStackTrace();
            ErrorDefaultDTO errorDefaultDTO = new ErrorDefaultDTO();
            errorDefaultDTO.setCode(500);
            errorDefaultDTO.setMessage("Непредвиденная ошибка");
            ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
            String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
            int pos = formattedCurrentDateTime.indexOf(".");
            formattedCurrentDateTime = formattedCurrentDateTime.replace('.', 'Z');
            formattedCurrentDateTime = formattedCurrentDateTime.substring(0, pos+1);
            errorDefaultDTO.setTime(formattedCurrentDateTime);
            return Response.status(errorDefaultDTO.getCode()).entity(errorDefaultDTO).build();
        }
    }

    @POST
    @Path("/route/add/{id-from}/{id-to}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRoute(@PathParam("id-from") String idFrom, @PathParam("id-to") String idTo) {
        try {
            return Response.status(Response.Status.OK).entity(getService().addRoute(idFrom, idTo)).build();
        } catch (NumberFormatException e) {
            System.out.println(e.getClass().getCanonicalName());
            ErrorDefaultDTO errorDefaultDTO = new ErrorDefaultDTO();
            errorDefaultDTO.setCode(404);
            errorDefaultDTO.setMessage("id should be Long Number");
            ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
            String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
            int pos = formattedCurrentDateTime.indexOf(".");
            formattedCurrentDateTime = formattedCurrentDateTime.replace('.', 'Z');
            formattedCurrentDateTime = formattedCurrentDateTime.substring(0, pos+1);
            errorDefaultDTO.setTime(formattedCurrentDateTime);
            return Response.status(errorDefaultDTO.getCode()).entity(errorDefaultDTO).build();
        } catch (NotValidParamsException e) {
            System.out.println(e.getClass().getCanonicalName());
            ErrorDefaultDTO errorDefaultDTO = new ErrorDefaultDTO();
            errorDefaultDTO.setCode(404);
            errorDefaultDTO.setMessage(e.getMessage());
            ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
            String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
            int pos = formattedCurrentDateTime.indexOf(".");
            formattedCurrentDateTime = formattedCurrentDateTime.replace('.', 'Z');
            formattedCurrentDateTime = formattedCurrentDateTime.substring(0, pos+1);
            errorDefaultDTO.setTime(formattedCurrentDateTime);
            return Response.status(errorDefaultDTO.getCode()).entity(errorDefaultDTO).build();
        } catch (FirstServiceUnavailableException e) {
            System.out.println(e.getClass().getCanonicalName());
            ErrorDefaultDTO errorDefaultDTO = new ErrorDefaultDTO();
            errorDefaultDTO.setCode(500);
            errorDefaultDTO.setMessage("проверьте корректную работоспособность первого сервиса");
            ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
            String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
            int pos = formattedCurrentDateTime.indexOf(".");
            formattedCurrentDateTime = formattedCurrentDateTime.replace('.', 'Z');
            formattedCurrentDateTime = formattedCurrentDateTime.substring(0, pos+1);
            errorDefaultDTO.setTime(formattedCurrentDateTime);
            return Response.status(errorDefaultDTO.getCode()).entity(errorDefaultDTO).build();
        } catch (Exception e) {
            System.out.println(e.getClass().getCanonicalName());
            ErrorDefaultDTO errorDefaultDTO = new ErrorDefaultDTO();
            errorDefaultDTO.setCode(500);
            errorDefaultDTO.setMessage("Непредвиденная ошибка");
            ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of("UTC+3"));
            String formattedCurrentDateTime = currentDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
            int pos = formattedCurrentDateTime.indexOf(".");
            formattedCurrentDateTime = formattedCurrentDateTime.replace('.', 'Z');
            formattedCurrentDateTime = formattedCurrentDateTime.substring(0, pos+1);
            errorDefaultDTO.setTime(formattedCurrentDateTime);
            return Response.status(errorDefaultDTO.getCode()).entity(errorDefaultDTO).build();
        }
    }
}
