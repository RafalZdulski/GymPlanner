package com.gluon.gymplanner.factories;

import com.gluon.gymplanner.dtos.ExerciseTraining;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.Arrays;

public class ExerciseTrainingListViewFactory implements Callback<ListView<ExerciseTraining>, ListCell<ExerciseTraining>> {
    @Override
    public ListCell<ExerciseTraining> call(ListView<ExerciseTraining> exerciseTrainingListView) {
        ListCell<ExerciseTraining> cell = new ListCell<>() {
            @Override
            public void updateItem(ExerciseTraining item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty){
                    Text name = new Text(item.getName());
                    name.setFont(Font.font("System", FontWeight.BOLD,22));
                    name.setWrappingWidth(320);

                    HBox bodyPartsHBox = new HBox(Arrays.stream(item.getBodyParts()).map(Text::new).toArray(Text[]::new));
                    bodyPartsHBox.setSpacing(5);
                    Text force = new Text(item.getForce());
                    Text mechanics = new Text(item.getMechanics());

                    HBox description = new HBox(bodyPartsHBox, force, mechanics);
                    description.setSpacing(7);
                    description.setPadding(new Insets(5,0,5,5));

                    GridPane gridPane = new GridPane();
                    gridPane.add(new Text("Weight:"),0,0);
                    gridPane.add(new Text(String.valueOf(item.getWeight())),1,0);
                    gridPane.add(new Text("reps planned"),0,1);
                    gridPane.add(new Text(String.valueOf(item.getWeight())),0,1);
                    gridPane.add(new Text("reps done"),2,0);
                    gridPane.add(new Text(String.valueOf(item.getWeight())),2,1);


                    setGraphic(new VBox(name, new HBox(description, gridPane)));
                }
            }
        };

        cell.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            System.out.println("\tClicked on: (edit)" + cell.getItem().getName());
        });

        return cell;
    }
}
