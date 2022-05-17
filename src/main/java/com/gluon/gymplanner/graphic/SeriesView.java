package com.gluon.gymplanner.graphic;

import com.gluon.gymplanner.dtos.ExerciseSeries;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.List;

public class SeriesView extends GridPane {

    private int seriesCounter;
    private List<ExerciseSeries> series;

    TextField lastWeightField;
    TextField lastRepsPlanField;
    TextField lastRepsDoneField;

    public List<ExerciseSeries> getSeries() {
        double weight = parseWeight(lastWeightField);
        int repsPlan = parseReps(lastRepsPlanField);
        int repsDone = parseReps(lastRepsDoneField);
        if (weight + repsDone + repsPlan > 0)
            series.add(new ExerciseSeries(weight,repsPlan,repsDone));
        return series;
    }

    public SeriesView(){
        series = new LinkedList<>();
        seriesCounter = 0;
        init();
        addHeader();
        newSeriesRow();
    }

    private void addHeader() {
        add(new Text("#"),0,0);
        add(new Text("weight\n(kg)"),1,0);
        add(new Text("reps\n(plan)"),2,0);
        add(new Text("reps\n(done)"),3,0);
    }

    private void init(){
        setMaxWidth(250);
        setHeight(300);
        setPadding(new Insets(5));
        setAlignment(Pos.CENTER);
        setVgap(10);
        setHgap(10);
    }

    public void newSeriesRow(){
        add(new Text(String.valueOf(seriesCounter+1)),0,seriesCounter+1);

        lastWeightField = new TextField();
        add(lastWeightField,1, seriesCounter+1);

        lastRepsPlanField = new TextField();
        add(lastRepsPlanField, 2, seriesCounter+1);

        lastRepsDoneField = new TextField();
        add(lastRepsDoneField, 3, seriesCounter+1);

        Button button = new Button();
        button.setMaxWidth(30);
        button.setId(String.valueOf(seriesCounter));
        button.setGraphic(MaterialDesignIcon.ADD.graphic());
        button.setOnAction(add -> {
            double weight = parseWeight(lastWeightField);
            int repsPlan = parseReps(lastRepsPlanField);
            int repsDone = parseReps(lastRepsDoneField);
            series.add(new ExerciseSeries(weight,repsPlan,repsDone));

            newSeriesRow();

            button.setGraphic(MaterialDesignIcon.DELETE.graphic());
            button.setOnAction(del -> {
                series.remove(Integer.parseInt(button.getId()));
                seriesCounter--;
                this.getChildren().removeIf(node -> GridPane.getRowIndex(node) == GridPane.getRowIndex(button));
                this.getChildren().filtered(node -> GridPane.getRowIndex(node) > GridPane.getRowIndex(button))
                        .forEach(node -> {
                            if(GridPane.getColumnIndex(node) == 4) {
                                //decrementation of all buttons id
                                int id = Integer.parseInt(node.getId()) - 1;
                                node.setId(String.valueOf(id));
                            }else if(GridPane.getColumnIndex(node) == 0){
                                //decrementation of rows id '#' below deleted row
                                Text txt = (Text) node;
                                int val = Integer.parseInt(txt.getText()) - 1;
                                txt.setText(String.valueOf(val));
                            }
                            int rowIndex = GridPane.getRowIndex(node);
                            GridPane.setRowIndex(node,rowIndex - 1);
                        });
            });
        });
        add(button,4,seriesCounter+1);

        seriesCounter++;
    }

    private int parseReps(TextField repsField) {
        if (repsField.getText().isEmpty())
            return 0;
        try{
            return Integer.parseInt(repsField.getText());
        }catch (NumberFormatException e){
            //TODO ADD: make it illegal to insert non-number values
        }
        return 0;
    }

    private double parseWeight(TextField weightField) {
        if (weightField.getText().isEmpty())
            return .0;
        try {
            return Double.parseDouble(weightField.getText());
        } catch (NumberFormatException e) {
            //TODO ADD: make it illegal to insert non-number values
        }
        return .0;
    }
}
