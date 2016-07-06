package client.view.gui.configurator;

import client.view.gui.LoaderResources;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.*;
import model.Region;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pietro on 05/07/16.
 */
public class Painter {

    private static final int  numCols=5;
    private static final int  numRows=9;

    private GridPane region1,region2,region3;

    private String css;

    private Pane linesPane;

    private ArrayList<Line> links;

    private CitiesListener citiesListener;

    private StackPane stackPane;




    public Painter(StackPane stackPane,GridPane region1, GridPane region2, GridPane region3, Pane linesPane, CitiesListener citiesListener) {
        this.stackPane=stackPane;
        this.citiesListener=citiesListener;
        links=new ArrayList<>();
        css= LoaderResources.loadPath("/configurator/style.css");
        this.linesPane=linesPane;
        this.region1=region1;
        this.region2=region2;
        this.region3=region3;
        addCells();

    }

    private void addCells() {
        for (int i = 0 ; i < numCols ; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            region1.getColumnConstraints().add(colConstraints);
            region2.getColumnConstraints().add(colConstraints);
            region3.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0 ; i < numRows ; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            region1.getRowConstraints().add(rowConstraints);
            region2.getRowConstraints().add(rowConstraints);
            region3.getRowConstraints().add(rowConstraints);
        }


    }

    public void repaint(Region[] regions) {
        List<City> regionOne=regions[0].getCities();
        List<City> regionTwo=regions[1].getCities();
        List<City> regionThree=regions[2].getCities();
        fillGrid(regionOne,region1);
        fillGrid(regionTwo,region2);
        fillGrid(regionThree,region3);


        region1.setPickOnBounds(false);
        region2.setPickOnBounds(false);
        region3.setPickOnBounds(false);
        addLinks();
    }

    private void fillGrid(List<City> region, GridPane regionGrid) {
        boolean choice=true;
        int k = 0;
        for (int i = 0 ; i < numCols ; i++) {
            for (int j = 0; j < numRows; j++) {
                if(choice && k<region.size()){
                    addCity(regionGrid,region.get(k),i, j);
                    k++;
                }
                choice=!choice;
            }
        }
    }

    private void addCity(GridPane region, City city, int colIndex, int rowIndex) {
        Pane pane=new Pane();
        pane.getStylesheets().add(css);
        if(!city.getKingIsHere())
            pane.getStyleClass().add(city.getColor()+"Castle");
        else
            pane.getStyleClass().add("PurpleCastle");
        pane.getStyleClass().add("city");
        pane.setOnMouseClicked(event -> {
            citiesListener.cityClicked(pane,city);
        });
        region.add(pane,colIndex,rowIndex);
    }


    private void addLinks() {

    }

    public void createLine(Pane firstLink, Pane secondLink) {
        double x1,y1,x2,y2;
        Parent parentFirstLink=firstLink.getParent();
        Parent parentSecondLink=secondLink.getParent();

        if (parentFirstLink.equals(region1)){
            x1=50;
            y1=30;
        }
        else if (parentFirstLink.equals(region2)){
            x1=460;
            y1=30;
        }else {
            x1=850;
            y1=20;
        }



        if (parentSecondLink.equals(region1)){
            x2=50;
            y2=30;
        }
        else if (parentSecondLink.equals(region2)){
            x2=460;
            y2=30;
        }else {
            x2=850;
            y2=20;
        }

        Line line=new Line(firstLink.getLayoutX()+x1,firstLink.getLayoutY()+y1,secondLink.getLayoutX()+x2,secondLink.getLayoutY()+y2);

        line.setStrokeWidth(10);
        links.add(line);
        line.setOnMouseClicked(event -> {
            citiesListener.removeLink(linesPane,line);
        });

        line.getStyleClass().add("line");

        linesPane.getChildren().add(line);
    }

}
