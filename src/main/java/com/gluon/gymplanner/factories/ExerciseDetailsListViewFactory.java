package com.gluon.gymplanner.factories;

import com.gluon.gymplanner.dtos.ExerciseDetails;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.Arrays;

public class ExerciseDetailsListViewFactory implements Callback<ListView<ExerciseDetails>, ListCell<ExerciseDetails>> {

    @Override
    public ListCell<ExerciseDetails> call(ListView<ExerciseDetails> exerciseDetailsListView) {
        ListCell<ExerciseDetails> cell = new ListCell<>(){
            @Override
            public void updateItem(ExerciseDetails item, boolean empty){
                super.updateItem(item,empty);
                if (!empty){
                    Text name = new Text(item.getName());
                    name.setFont(Font.font("System", FontWeight.BOLD,22));
                    name.setWrappingWidth(320);

                    ImageView imageView = new ImageView();
                    imageView.setFitWidth(126);
                    imageView.setFitHeight(84);
                    imageView.setImage((new Image(item.getPicture(),126,84,true,true,true)));

                    HBox bodyPartsHBox = new HBox(Arrays.stream(item.getBodyParts()).map(Text::new).toArray(Text[]::new));
                    bodyPartsHBox.setSpacing(5);
                    Text force = new Text(item.getForce());
                    Text mechanics = new Text(item.getMechanics());

                    VBox description = new VBox(bodyPartsHBox, force, mechanics);
                    description.setSpacing(7);
                    description.setPadding(new Insets(5,0,5,5));

                    setGraphic(new VBox(name, new HBox(imageView, description)));
                }
            }
        };

        cell.addEventHandler(MouseEvent.MOUSE_RELEASED, e ->{
            System.out.println("\tClicked on:" + cell.getItem());
        });

        return cell;
    }
}
