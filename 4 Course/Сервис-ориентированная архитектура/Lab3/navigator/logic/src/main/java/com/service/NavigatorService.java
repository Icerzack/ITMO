package com.service;


import com.dto.RouteDTO;
import javax.ejb.Remote;
import javax.ws.rs.core.Response;


@Remote
public interface NavigatorService {

  float findShortest(String idFrom, String idTo);
  RouteDTO addRoute(String idFrom, String idTo);
}
