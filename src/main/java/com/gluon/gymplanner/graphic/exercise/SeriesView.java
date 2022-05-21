package com.gluon.gymplanner.graphic.exercise;

import com.gluon.gymplanner.dtos.ExerciseSeries;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.List;

public class SeriesView extends GridPane {

    private int seriesCounter;

    List<TextField> weightFields = new LinkedList<>();
    List<TextField> repsPlanFields = new LinkedList<>();
    List<TextField> repsDoneFields = new LinkedList<>();

    Button addButton = initAddButton();

    public List<ExerciseSeries> getSeries() {
        List<ExerciseSeries> series = new LinkedList<>();
        for (int i=0; i<seriesCounter; i++){
            double weight = parseWeight(weightFields.get(i));
            int repsPlanned = parseReps(repsPlanFields.get(i));
            int repsDone = parseReps(repsDoneFields.get(i));
            series.add(new ExerciseSeries(weight, repsPlanned, repsDone));
        }
        return series;
    }

    public SeriesView(){
        seriesCounter = 0;
        init();
        addHeader();
        add(addButton,1,seriesCounter+1);
    }

    public SeriesView(List<ExerciseSeries> series){
        seriesCounter = 0;
        for (var s : series){
            this.newRow(s);
        }
        init();
        addHeader();
        add(addButton,1,seriesCounter+1);
    }

    private void addHeader() {
        add(new Text("#"),0,0);
        add(new Text("weight\n(kg)"),1,0);
        add(new Text("reps\n(plan)"),2,0);
        add(new Text("reps\n(done)"),3,0);
    }

    private void init(){
        setMaxWidth(225);
        //setHeight(300);
        setPadding(new Insets(5));
        setAlignment(Pos.CENTER);
        setVgap(10);
        setHgap(10);
    }

    public void newRow(){
        add(new Text(String.valueOf(seriesCounter+1)),0,seriesCounter+1);

        TextField weightField = new TextField();
        weightFields.add(weightField);
        add(weightField,1, seriesCounter+1);

        TextField repsPlanField = new TextField();
        repsPlanFields.add(repsPlanField);
        add(repsPlanField, 2, seriesCounter+1);

        TextField repsDoneField = new TextField();
        repsDoneFields.add(repsDoneField);
        add(repsDoneField, 3, seriesCounter+1);

        add(initDeleteButton(),4,seriesCounter+1);

        GridPane.setRowIndex(addButton,++seriesCounter+1);
    }

    public void newRow(ExerciseSeries series){
        add(new Text(String.valueOf(seriesCounter+1)),0,seriesCounter+1);

        TextField weightField = new TextField(String.valueOf(series.getWeight()));
        weightFields.add(weightField);
        add(weightField,1, seriesCounter+1);

        TextField repsPlanField = new TextField(String.valueOf(series.getRepsPlanned()));
        repsPlanFields.add(repsPlanField);
        add(repsPlanField, 2, seriesCounter+1);

        TextField repsDoneField = new TextField(String.valueOf(series.getRepsDone()));
        repsDoneFields.add(repsDoneField);
        add(repsDoneField, 3, seriesCounter+1);

        add(initDeleteButton(),4,seriesCounter+1);

        GridPane.setRowIndex(addButton,++seriesCounter+1);
    }

    private void setDeleteBtnAction(Button button) {
        seriesCounter--;
        this.getChildren().removeIf(node -> GridPane.getRowIndex(node) == GridPane.getRowIndex(button));
        this.getChildren().filtered(node -> (
                GridPane.getRowIndex(node) > GridPane.getRowIndex(button) &&
                        GridPane.getRowIndex(node) < GridPane.getRowIndex(addButton))
                ).forEach(node -> {
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

    private Button initAddButton(){
        Button button = new Button();
        button.setGraphic(MaterialDesignIcon.ADD.graphic());
        button.setStyle("-fx-background-radius: 30");
        button.setPrefWidth(100);
        button.setOnAction(e -> newRow());
        //TODO FIX: center add button
        GridPane.setColumnSpan(button, 4);

        return button;
    }

    private Button initDeleteButton(){
        Button button = new Button();
        button.setMaxWidth(30);
        button.setStyle("-fx-background-radius: 30");
        button.setId(String.valueOf(seriesCounter));
        button.setGraphic(MaterialDesignIcon.DELETE.graphic());
        button.setOnAction(del -> {
            weightFields.remove(Integer.parseInt(button.getId()));
            repsPlanFields.remove(Integer.parseInt(button.getId()));
            repsDoneFields.remove(Integer.parseInt(button.getId()));
            setDeleteBtnAction(button);
        });
        return button;
    }
}
