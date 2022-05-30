package com.gluon.gymplanner.presenters;

import com.gluon.gymplanner.dtos.ExerciseDetails;
import com.gluon.gymplanner.jdbc.h2JDBC;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.util.Arrays;
import java.util.List;

public class ExercisePresenter implements Presenter{

    private ExerciseDetails exercise;

    @FXML
    private GridPane desc;

    @FXML
    private ListView<String> executionList;

    @FXML
    private ImageView image;

    @FXML
    private Text name;

    @FXML
    private View secondary;

    @FXML
    private ListView<String> tipsList;

    public void initialize() {
        secondary.setShowTransitionFactory(BounceInRightTransition::new);
    }

    public void setExercise(ExerciseDetails exercise){
        this.exercise = exercise;
        showExercise();
    }

    private void showExercise(){
        image.setImage(new Image(exercise.getPicture(),350,233,true,true,true));
        name.setText(exercise.getName());
        desc.add(new Text("Body Parts:"),0,0);
        desc.add(new VBox(Arrays.stream(exercise.getBodyParts()).map(Text::new).toArray(Text[]::new)),1,0);
        desc.add(new Text("Mechanics::"),0,1);
        desc.add(new Text(exercise.getMechanics()),1,1);
        desc.add(new Text("Force"),0,2);
        desc.add(new Text(exercise.getForce()),1,2);

        setExecution();
        setTips();
    }


    void setExecution() {
        List<String> execution = new h2JDBC().getExerciseExecution(exercise.getId());
        executionList.setItems(FXCollections.observableList(execution));
        executionList.setCellFactory(this.getTipsOrExecutionFactory());
    }

    void setTips() {
        List<String> tips = new h2JDBC().getExerciseTips(exercise.getId());
        tipsList.setItems(FXCollections.observableList(tips));
        tipsList.setCellFactory(this.getTipsOrExecutionFactory());
    }

    private Callback<ListView<String>, ListCell<String>> getTipsOrExecutionFactory(){
        return factory -> new ListCell<>(){
            @Override
            public void updateItem(String item, boolean empty){
                super.updateItem(item, empty);
                if (!empty) {
                    Text iterator = new Text(String.valueOf(this.getIndex()+1+". "));
                    iterator.setFont(Font.font("system", FontWeight.BOLD, 22));

                    Text text = new Text(item);
                    text.setFont(Font.font(14));
                    text.setTextAlignment(TextAlignment.JUSTIFY);
                    text.setWrappingWidth(220);

                    HBox hBox = new HBox(iterator,text);
//                        hBox.setAlignment(Pos.CENTER);
                    hBox.setSpacing(5);

                    setGraphic(hBox);
                }
            }
        };
    }


}
