package com.example.routes.controller;

import com.example.routes.dto.QueryDTO;
import com.example.routes.dto.RouteDTO;
import com.example.routes.dto.RoutesWithPagingDTO;
import com.example.routes.dto.SpecialOfferQueryDTO;
import com.example.routes.exception.NotValidParamsException;
import com.example.routes.exception.ResourceNotFoundException;
import com.example.routes.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import java.util.Map;
import java.util.Set;

@RestController
@Validated
public class RouteController {
    @Autowired
    private RouteService routeService;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @GetMapping("/")
    public String allWork() {
        return "All works";
    }

    @GetMapping("/api/v1/routes")
    public ResponseEntity<RoutesWithPagingDTO> getAllRoutes(@Valid QueryDTO dto) {
        return ResponseEntity.status(200).body(routeService.getAllRoutes(dto));
    }

    @PostMapping("/api/v1/routes")
    public ResponseEntity<RouteDTO> addNewRoute(@RequestBody RouteDTO dto) {
        Set<ConstraintViolation<RouteDTO>> constraintViolations = validator.validate(dto);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
        return ResponseEntity.status(200).body(routeService.addNewRoute(dto));
    }

    @GetMapping("/api/v1/routes/{id}")
    public ResponseEntity<RouteDTO> getRouteById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.status(200).body(routeService.getRouteById(Long.parseLong(String.valueOf(id))));
        } catch (NumberFormatException e) {
            System.out.println(e.getClass().getCanonicalName());
            throw new NotValidParamsException("id should be Long Number");
        }
    }

    @PutMapping("/api/v1/routes/{id}")
    public ResponseEntity<RouteDTO> updateRouteById(@PathVariable("id") String id, @RequestBody RouteDTO dto) {
        try {
            return ResponseEntity.status(200).body(routeService.updateRouteById(Long.parseLong(String.valueOf(id)), dto));
        } catch (NumberFormatException e) {
            System.out.println(e.getClass().getCanonicalName());
            throw new NotValidParamsException("id should be Long Number");
        }
    }

    @DeleteMapping("/api/v1/routes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRouteById(@PathVariable("id") String id) {
        try {
            routeService.deleteRouteById(Long.parseLong(String.valueOf(id)));
        } catch (NumberFormatException e) {
            System.out.println(e.getClass().getCanonicalName());
            throw new NotValidParamsException("id should be Long Number");
        }
    }

    @DeleteMapping("/api/v1/routes/distances/{distance}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoutesByDistance(@PathVariable("distance") String distance) {
        try {
            routeService.deleteRoutesByDistance(Float.parseFloat(String.valueOf(distance)));
        } catch (NumberFormatException e) {
            System.out.println(e.getClass().getCanonicalName());
            throw new NotValidParamsException("distance should be Float Number");
        }
    }

    @GetMapping("/api/v1/routes/distances/sum")
    public ResponseEntity<Float> getSumAllDistances() {
        return ResponseEntity.status(200).body(routeService.getSumAllDistances());
    }

    @GetMapping("/api/v1/routes/distances/{distance}/count/greater")
    public ResponseEntity<Integer> getCountRoutesWithGreaterDistance(@PathVariable("distance") String distance) {
        try {
            return ResponseEntity.status(200).body(routeService.getCountRoutesWithGreaterDistance(Float.parseFloat(String.valueOf(distance))));
        } catch (NumberFormatException e) {
            System.out.println(e.getClass().getCanonicalName());
            throw new NotValidParamsException("distance should be Float Number");
        }
    }

    @GetMapping("/api/v1/routes/special-offer")
    public ResponseEntity<Map<String, Object>> getSpecialOffers(@Valid SpecialOfferQueryDTO dto) {
        Map<String, Object> body = routeService.getSpecialOffers(dto);
        int statusCode = (int) body.get("responseCode");
        body.remove("responseCode");
        return ResponseEntity.status(statusCode).body(body);
    }

    @GetMapping("/error")
    public ResponseEntity<?> resourceNotFound() {
        throw new ResourceNotFoundException();
    }
}
