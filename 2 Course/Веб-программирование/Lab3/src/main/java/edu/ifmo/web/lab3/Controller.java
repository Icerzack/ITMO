package edu.ifmo.web.lab3;

import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class Controller implements Serializable {
    @Inject
    DataBaseManager dataBaseManager;
    private List<PointEntity> pointEntityList;
    @Inject
    SVGHit SVGHit;
    @Inject FormHit formhit;

    @PostConstruct
    public void initializeAll() {
        pointEntityList = dataBaseManager.getHits();
    }

    public List<PointEntity> getResults() {
        return pointEntityList;
    }

    public void updateAll(){
        formhit.setDefaultValues();
        dataBaseManager.delBase();
        initializeAll();
        addStoredData();
        formhit.setDefaultValues();
    }

    public void formAdding() {
        if(formhit.validateValues()){
            double x = formhit.getX();
            double y = formhit.getY();
            double r = formhit.getR();
            addHits(calculate(x,y,r));
        }
    }

    public void svgAdding() {
        if(1.0001<=formhit.getR() && formhit.getR()<=3.9999){
            PointEntity pointEntity = calculate(SVGHit.getX(), SVGHit.getY(), SVGHit.getR());
            addHits(pointEntity);
        }
    }

    private PointEntity calculate(double x, double y, double radius) {
        return new PointEntity(x, y, radius, doesItHit(x, y, radius));
    }

    private boolean doesItHit(double x, double y, double radius) {
        if (x <= 0 && y <= 0 && y >= radius/2 - x - radius) {
            return true;
        }
        if (Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(radius/2, 2) && x >= 0 && y >= 0) {
            return true;
        }
        return -radius/2 <= x && x <= 0 && 0 <= y && y <= radius;
    }

    private void addHits(PointEntity hits) {
        if (dataBaseManager.addHits(hits)) {
            pointEntityList.add(hits);
            addHitsToCanvas(Collections.singletonList(hits));
        }
    }

    public void addStoredData() {
        addHitsToCanvas(pointEntityList);
    }

    private void addHitsToCanvas(List<PointEntity> hits) {
        String json = hits.stream()
            .map(hit -> "{" +
                " x: " + hit.getX() + "," +
                " y: " + hit.getY() + "," +
                " r: " + hit.getR() + "," +
                " doesHit: " + hit.isDoesHit() + " }"
            )
            .collect(Collectors.joining(", ", "[", "]"));
        PrimeFaces.current().executeScript("addHits(" + json + ")");
    }
}
